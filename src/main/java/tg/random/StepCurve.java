/*
 * StepCurve.java
 *
 * Created on 19 January 2005, 21:40
 */
package tg.random;

import tg.util.Util;

/**
 * A simple curve that can be dynamically adjusted
 * p2 _______
 * __/
 * p1
 */
public class StepCurve implements Curve {
    private Point p1;
    private Point p2;

    public StepCurve(Point p1, Point p2) {
        this.p1 = p1;
        this.p2 = p2;
        validate();
    }

    public Point getP1() {
        return p1;
    }

    public Point getP2() {
        return p2;
    }

    public int getY(int x) {
        if (x >= p2.x) return p2.y;
        else if (x <= p1.x) return p1.y;
        int partX = x - p1.x;
        int partY = (yDelta() * partX) / xDelta(); // divide by 0 is not possible here
        return p1.y + partY;
    }

    /**
     * Slides the entire curve so that p1.x is the given value
     */
    public void setP1X(int x) {
        int delta = x - p1.x;
        this.p1 = slide(delta, p1);
        this.p2 = slide(delta, p2);
    }

    public String toString() {
        return getP1() + " " + getP2();
    }

    public int getP1X() {
        return getP1().x;
    }

    private int yDelta() {
        return p2.y - p1.y;
    }

    private int xDelta() {
        return p2.x - p1.x;
    }

    private Point slide(int delta, Point p) {
        return new Point(p.x + delta, p.y);
    }

    private void validate() {
        Util.assertNotNull(p2, "P2 cannot be null");
        Util.assertNotNull(p1, "P1 cannot be bull");
        if (p1.x > p2.x) throw new IllegalArgumentException("P1.x cannot be more than P2.x");
    }
}
