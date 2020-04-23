/*
 * RandomSource.java
 *
 * Created on 20 January 2005, 22:10
 */
package tg.produce;

import tg.random.Curve;
import tg.random.RandomUtil;
import tg.util.Util;

/**
 * Produces a random series of keys.
 * The probability of a key being produced is proportional to:
 * Difficulty and Difficulty probability factor (EG: hard keys are penalised)
 * How recently was the key output (EG: Avoid repeated keys)
 */
public class RandomSource implements KeyStrokeSource {
    private Curve ratingWeight;
    private Curve recentWeight;
    private RatedKeyStroke[] keys;
    private int[] keyWeight; // kept in memory to avoid memory churn.
    private int[] lastUseTick;
    private int currentTick = 50; // by default everything was used 50 ticks ago

    public RandomSource(RatedKeyStroke[] keys, Curve ratingWeight, Curve recentWeight) {
        validate(keys, ratingWeight, recentWeight);
        this.ratingWeight = ratingWeight;
        this.recentWeight = recentWeight;
        this.keys = keys;
        this.keyWeight = new int[keys.length];
        this.lastUseTick = new int[keys.length];
    }

    public synchronized RatedKeyStroke getKeyStroke() {
        // TODO: Make this cyclic
        currentTick++;
        int ix = chooseKey();
        return keys[ix];
    }

    private void validate(RatedKeyStroke[] keys, Curve ratingWeight, Curve recentWeight) {
        Util.assertNotNull(keys, "'keys' are needed");
        Util.assertNotNull(ratingWeight, "'ratingWeight' is needed");
        Util.assertNotNull(recentWeight, "'recentWeight' is needed");
        if (keys.length == 0) throw new IllegalArgumentException("At least 1 key is required");
    }

    private int chooseKey() {
        for (int i = 0; i < keys.length; i++) keyWeight[i] = calcWeight(i);
        int ix = RandomUtil.choose(keyWeight);
        lastUseTick[ix] = currentTick;
        return ix;
    }

    private int calcWeight(int i) {
        int difficultyWeight = difficultyWeight(i);
        int recentWeight = recentWeight(i);
        // FIX CORRECTNESS This should be square rooted(?)
        return difficultyWeight * recentWeight;
    }

    private int recentWeight(int i) {
        int duration = duration(i);
        return this.recentWeight.getY(duration);
    }

    private int duration(int i) {
        return currentTick - lastUseTick[i];
    }

    private int difficultyWeight(int i) {
        int difficulty = keys[i].getRating();
        return ratingWeight.getY(difficulty);
    }
}
