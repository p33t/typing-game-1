/*
 * Config.java
 *
 * Created on 24 January 2005, 20:55
 */

package tg.util;

import tg.produce.RatedKeyStroke;
import tg.random.Curve;
import tg.random.StepCurve;

import java.util.Comparator;

public class Config {
  
  // Assessment
  public static final int PERFECT = 100; // 100 %
  public static final int ASSESSMENT_INTERVAL = 3000; // time between assessments
  public static final int DEFAULT_PERFECT_KEY_RATE = 7; // equals a score of 100% for keys per sec (default)
  public static final int EASIEST_PERFECT_KEY_RATE = 1; // need at least one key per second as the goal
  public static final int HARDEST_PERFECT_KEY_RATE = 8; // at most 8 key per second as the goal
  public static final int HISTORY_SIZE = 50; // size of sample for assessment
  public static final int DURATION_MAX_THRESHOLD = 2000; // won't get penalized above this == 0%
  public static final int DURATION_MAX_FACTOR = 10; // 10 times slower than perfect is 0% (unless threshold is activated)
  
  public static final int DEFAULT_DIFFICULTY_GAIN_QUOTIENT = 4; // 1/x proportion of the difference will be compensated (default)
  public static final int SLOW_DIFFICULTY_GAIN_QUOTIENT = 8; // 1/x proportion of the difference will be compensated (default)
  public static final int STEADY_ACCURACY = (int) (PERFECT * .9); // the accuracy that is deemed desirable
  public static final int STEADY_SPEED = (int) (PERFECT * .7); // the speed that is deemed desirable

  public static final int DISPLAY_BUFFER_SIZE = 10; // the number of chars shown to the user
  
  private static final int EASY_KEY_TRANSITION_QUOTIENT = 8; // 1/x proportion of the range for the transition (more is steeper)
  private static final int NON_REPEAT_QUOTIENT = 10; // 1/x proportion of the range for the beginning of recent use gradient
                                                     // 10 = a 10th of the recent keys will be seriously improbable
  private static final int NON_REPEAT_TRANSITION_QUOTIENT = 5; // 1/x proportion of the curve in the transition to fully probably recent use

  private static final int HIGH_PROBABILITY = 20;
  
  public static final String ABOUT_MESSAGE = "The Typing Tutor\n\nInfo:  www.geocities.com/thetypingtutor\nEmail: TheTypingTutor@yahoo.com"
                                             + "\n\nCopyright: Peter W Leong";

  private Config() {
  }
  
  /**
   * Create a normalised difficulty curve
   */
  public static StepCurve createRatingWeight(RatedKeyStroke[] keys) {
//    final int EASY_KEY_THRESHOLD = keys.length / EASY_KEY_THRESHOLD_QUOTIENT; // bottom portion that are easy
//    final int EASY_TRANSITION = keys.length / EASY_KEY_TRANSITION_QUOTIENT; // the number of keys above the threshold that 
                                                  // are possible although decreasingly probable
    // NOTE: Assume spread from 0 - max
    
    // determine characteristics
    
    
    //NO good....susceptable to too course-grained point spread
//    RatedKeyStroke[] sortedKeys = new RatedKeyStroke[keys.length];
//    
//    for (int i = 0; i < keys.length; i++) {
//      // for each key
//      // copy
//      sortedKeys[i] = keys[i];
//    }
//    
//    // sort
//    Arrays.sort(sortedKeys, new Config.RateComparator());
//    
//    int easyRating = sortedKeys[EASY_KEY_THRESHOLD].getRating();
//    int lessEasyRating = sortedKeys[EASY_KEY_THRESHOLD + EASY_TRANSITION].getRating();
    
    int minRating = getMinRating(keys);
    int maxRating = getMaxRating(keys);
    int easyRating = minRating; //maxRating / EASY_KEY_THRESHOLD_QUOTIENT;
    //easyRating += minRating;
    int lessEasyRating = easyRating + (maxRating / EASY_KEY_TRANSITION_QUOTIENT);
    
    StepCurve ratingWeight = new StepCurve(new Curve.Point(easyRating, HIGH_PROBABILITY),
                                           new Curve.Point(lessEasyRating, 0));
    outputCurve("Rating Weight", ratingWeight);
    return ratingWeight;
  }

  /**
   * Create a general recent use weight curve for the given keys
   */
  public static StepCurve createRecentUseWeight(RatedKeyStroke[] keys) {
    final int NON_REPEAT_THRESHOLD = keys.length / NON_REPEAT_QUOTIENT;
    final int NON_REPEAT_TRANSITION = keys.length / NON_REPEAT_TRANSITION_QUOTIENT;
    
    StepCurve result = new StepCurve(new Curve.Point(NON_REPEAT_THRESHOLD, 1),
                                     new Curve.Point(NON_REPEAT_THRESHOLD + NON_REPEAT_TRANSITION, HIGH_PROBABILITY));
    outputCurve("Recent Weight", result);
    return result;
  }
  
  /**
   * Return the average rating of the given keys
   */
  public static int calcAverageRating(RatedKeyStroke[] keys) {
    int total = 0;
    for (int i = 0; i < keys.length; i++) {
      total += keys[i].getRating();
    }
    int result = (total / keys.length);
    output("Avg.Rating: " + result);
    return result;
  }
  
  /**
   * Indicate the maximum rating in the given array of keystrokes
   */
  public static int getMaxRating(RatedKeyStroke[] keys) {
    int max = 0;
    for (int i = 0; i < keys.length; i++) {
      max = Math.max(max, keys[i].getRating());
    }
    output("Max.Rating: " + max);
    return max;
  }
  
  /**
   * Indicate the minimum rating in the given array of keystrokes
   */
  public static int getMinRating(RatedKeyStroke[] keys) {
    int min = Integer.MAX_VALUE;
    for (int i = 0; i < keys.length; i++) {
      min = Math.min(min, keys[i].getRating());
    }
    output("Min.Rating: " + min);
    return min;
  }
  
  public static void outputCurve(String name, StepCurve curve) {
    output(name + ": " + curve);
  }
  
  /**
   * Simply output the given string.
   * ...used for turning output on / off
   */
  public static void output(String s) {
    //System.out.println(s);
  }
  
  /**
   * Compares the rating of two rated keys
   */
  private static class RateComparator implements Comparator {
    public int compare(Object o1, Object o2) {
      return ((RatedKeyStroke) o1).getRating() - ((RatedKeyStroke) o2).getRating();
    }
  } // END of RateComparator
} // END of main class
