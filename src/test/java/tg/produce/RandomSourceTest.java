/*
 * RandomSourceTest.java
 *
 * Created on 20 January 2005, 22:43
 */
package tg.produce;

import junit.framework.TestCase;
import tg.random.Curve;
import tg.random.StepCurve;

/**
 * @author root
 */
public class RandomSourceTest extends TestCase {
    // FIX TESTING This requires visual confirmation.
    public void testSimple() throws Exception {
        RandomSource source = source();
        System.out.println("Result:");
        for (int i = 1; i < 200; i++) {
            System.out.print(source.getKeyStroke().getKeyChar());
        }
        System.out.println();
    }

    private RandomSource source() {
        Curve difficulty = step(40, 10, 100, 2);
        Curve recent = step(4, 0, 20, 8);
        RatedKeyStroke[] keys = genKeys();
        return new RandomSource(keys, difficulty, recent);
    }

    private RatedKeyStroke[] genKeys() {
        RatedKeyStroke[] keys = new RatedKeyStroke[26];
        for (int i = 0; i < keys.length; i++) {
            char c = (char) ('a' + i);
            int rating = 1 + (i * 5);
            keys[i] = new RatedKeyStroke(c, rating);
        }
        return keys;
    }

    private StepCurve step(int x1, int y1, int x2, int y2) {
        Curve.Point p1 = new Curve.Point(x1, y1);
        Curve.Point p2 = new Curve.Point(x2, y2);
        return new StepCurve(p1, p2);
    }
}
