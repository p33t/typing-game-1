/*
 * RunningAssessor.java
 *
 * Created on 24 January 2005, 22:40
 */
package tg.assess;

import tg.KeyStroke;
import tg.produce.RatedKeyStroke;
import tg.util.UserPref;
import tg.util.Util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static tg.util.Config.*;

/**
 * Manages a worker thread that maintains a running average assessment.
 */
public class RunningAssessor {
    // Normal state vars
    private final List<UserResponse> history = new ArrayList<UserResponse>(HISTORY_SIZE);
    private int keyCount = 0;
    private List<AssessmentListener> listeners = new ArrayList<AssessmentListener>();
    boolean shutdown = false;
    // user controllable settings
    public final UserPref userPref;
    // statistics
    private final int averageRating;
    private final int minRating;

    public RunningAssessor(int averageRating, int minRating, UserPref userPref) {
        this.averageRating = averageRating;
        this.minRating = minRating;
        this.userPref = userPref;
        validate();
        launchThread();
    }

    /**
     * Resets the assessor and triggers an assessment update with all 0 values
     */
    public void reset() {
        AssessmentListener[] listenerArray;
        synchronized (history) {
            history.clear();
            keyCount = 0;
            listenerArray = listenerArray();
        }
        notifyListeners(listenerArray, new Assessment());
    }

    public void addListener(AssessmentListener listener) {
        // ensure it has not already been added
        removeListener(listener);
        synchronized (history) {
            if (listener != null) listeners.add(listener);
        }
    }

    public void removeListener(AssessmentListener listener) {
        synchronized (history) {
            for (Iterator it = listeners.iterator(); it.hasNext();) {
                Object obj = it.next();
                if (obj == listener) it.remove();
            }
        }
    }

    /**
     * Accept the given response and update the ongoing totals
     */
    public void assess(UserResponse response) {
        synchronized (history) {
            truncateHistory();
            history.add(response);
            keyCount++;
        }
    }

    /**
     * Causes worker thread to shutdown.  Returns 'null' for convenience.
     */
    public RunningAssessor shutdown() {
        shutdown = true;
        return null;
    }

    private void validate() {
        if (averageRating <= 0) throw new IllegalArgumentException("Average Rating must be > 0.  Was given " + averageRating);
        if (minRating < 0) throw new IllegalArgumentException("Min Rating must be >= 0.  Was given " + minRating);
        Util.assertNotNull(userPref, "UserPref is required.");
    }

    private AssessmentListener[] listenerArray() {
        int size = listeners.size();
        AssessmentListener[] result = new AssessmentListener[size];
        listeners.toArray(result);
        return result;
    }

    private void launchThread() {
        Loop loop = new Loop();
        Thread t = new Thread(loop);
        t.setDaemon(true);
        t.start();
    }

    private void truncateHistory() {
        while (history.size() >= HISTORY_SIZE) history.remove(0);
    }

    /**
     * Notify all of the listeners of the given assessment
     */
    private void notifyListeners(AssessmentListener[] listeners, Assessment assessment) {
        for (AssessmentListener listener : listeners) {
            listener.update(assessment);
        }
    }

    /**
     * The duration between keystrokes indicating 100% for speed.
     */
    private int getPerfectDuration() {
        int targetRate = userPref.getTargetKeyStrokeRate();
        return 1000 / targetRate;
    }

    /**
     * The worker thread that publishes assessments regularly
     */
    private class Loop implements Runnable {
        private int lastKeyCount = 0;

        public void run() {
            do {
                attemptAssess();
                pause();
            } while (!shutdown);
        }

        private void attemptAssess() {
            if (!keyPressed()) return;
            AssessmentListener[] listeners;
            UserResponse[] responses;
            synchronized (history) {
                // copy necessary vars and then release lock
                lastKeyCount = keyCount;
                listeners = listenerArray();
                responses = historyArray();
            }
            assess(listeners, responses);
        }

        private void assess(AssessmentListener[] listeners, UserResponse[] responses) {
            if (isEmpty(responses)) return;
            if (isEmpty(listeners)) return;
            Assessment a = createAssessment(responses);
            notifyListeners(listeners, a);
        }

        private boolean keyPressed() {
            return lastKeyCount != keyCount;
        }

        private boolean isEmpty(Object[] arr) {
            return arr.length == 0;
        }

        private Assessment createAssessment(UserResponse[] responses) {
            int accuracy = calcAccuracy(responses);
            int rating = calcRating(responses);
            int speed = calcSpeed(responses);

            // ensure at least a value of '1'
            speed = Math.max(speed, 1);
            rating = Math.max(rating, 1);
            accuracy = Math.max(accuracy, 1);

            // publish assessment
            int overAll = calcOverall(accuracy, rating, speed);
            return new Assessment(rating, accuracy, speed, lastKeyCount, 0, overAll);
        }

        private void pause() {
            try {
                Thread.sleep(ASSESSMENT_INTERVAL);
            }
            catch (InterruptedException e) {
                // interrupted
                // do nothing
            }
        }

        private int calcOverall(int accuracy, int rating, int speed) {
            double cube = speed * accuracy * rating;
            return (int) Math.round(Math.pow(cube, 1.0 / 3.0));
        }

        private int calcSpeed(UserResponse[] responses) {
            if (isEmpty(responses)) return 0;
            // a little expensive to calculate
            int durationMax = userPref.getDurationMax();
            int duration = avgDuration(responses, durationMax);
            int speed = durationToSpeed(durationMax, duration);
            // need to truncate to a perfect score (which is configurable)
            return Math.min(speed, PERFECT);
        }

        private int durationToSpeed(int durationMax, int duration) {
            int normalisedDuration = (durationMax - duration) * PERFECT;
            int normalisedMax = durationMax - getPerfectDuration();
            return normalisedDuration / normalisedMax;
        }

        private int avgDuration(UserResponse[] responses, int durationMax) {
            int total = sumDuration(responses, durationMax);
            return total / (responses.length - 1);
        }

        private int calcRating(UserResponse[] responses) {
            int rating = sumRating(responses);
            int zeroRating = responses.length * minRating;
            rating -= zeroRating;
            rating *= PERFECT;
            rating /= responses.length;
            rating /= averageRating - minRating;
            return rating;
        }

        private int calcAccuracy(UserResponse[] responses) {
            int hits = sumAccuracy(responses);
            hits *= PERFECT;
            hits /= responses.length;
            return hits;
        }

        private int sumDuration(UserResponse[] responses, int durationMax) {
            int duration = 0;
            for (int i = 0; i < responses.length; i++) duration += elapsed(responses, durationMax, i);
            return duration;
        }

        private int sumRating(UserResponse[] responses) {
            int rating = 0;
            for (UserResponse response : responses) rating += rating(response);
            return rating;
        }

        private int sumAccuracy(UserResponse[] responses) {
            int accuracy = 0;
            for (UserResponse response : responses) accuracy += accuracy(response);
            return accuracy;
        }

        private int accuracy(UserResponse response) {
            if (charMatch(response)) return 1;
            return 0;
        }

        private int rating(UserResponse response) {
            RatedKeyStroke desired = response.getDesiredKeyStroke();
            return desired.getRating();
        }

        private long elapsed(UserResponse[] responses, int durationMax, int i) {
            if (i == 0) return 0;
            UserResponse prevResponse = responses[i - 1];
            UserResponse response = responses[i];
            long elapsed = response.getTime() - prevResponse.getTime();
            return Math.min(elapsed, durationMax);
        }

        private boolean charMatch(UserResponse response) {
            RatedKeyStroke desired = response.getDesiredKeyStroke();
            KeyStroke actual = response.getResultingKeyStroke();
            return desired.getKeyChar() == actual.getKeyChar();
        }

        private UserResponse[] historyArray() {
            int size = history.size();
            UserResponse[] result = new UserResponse[size];
            history.toArray(result);
            return result;
        }
    } // END of Loop
}// END of main class
