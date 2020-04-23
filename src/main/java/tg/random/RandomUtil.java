/*
 * Util.java
 *
 * Created on 19 January 2005, 19:21
 */
package tg.random;

import tg.util.Util;

public class RandomUtil {
    private RandomUtil() {
    }

    /**
     * Returns an index into the given array randomly, using the weights for each element in the array
     */
    public static int choose(int[] weights) {
        checkWeights(weights);
        if (isAllZero(weights)) return weights.length - 1;
        return choosePriv(weights);
    }

    private static int choosePriv(int[] weights) {
        long[] accum = accumWeights(weights);
        int maxIndex = weights.length - 1;
        long random = rand(accum[maxIndex]);
        for (int i = 0; i < maxIndex; i++) {
            if ((accum[i] > random)) return i;
        }
        return maxIndex;
    }

    private static long rand(long totalWeight) {
        return (long) (Math.random() * totalWeight);
    }

    private static long[] accumWeights(int[] weights) {
        long[] accum = new long[weights.length];
        long lastAccum = 0;
        for (int i = 0; i < weights.length; i++) {
            lastAccum += weights[i];
            accum[i] = lastAccum;
        }
        return accum;
    }

    private static void checkWeights(int[] weights) {
        Util.assertNotNull(weights, "An array of weightings is required");
        if (weights.length == 0) throw new IllegalArgumentException("There must be at least 1 weighting");
    }

    private static boolean isAllZero(int[] arr) {
        for (int elem : arr) {
            if (elem != 0) return false;
        }
        return true;
    }
}
