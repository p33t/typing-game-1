/*
 * Curve.java
 *
 * Created on 19 January 2005, 21:36
 */
package tg.random;

public interface Curve {
    public int getY(int x);

    public static class Point {
        public final int x;
        public final int y;

        public Point(int x, int y) {
            checkVal("x", x);
            checkVal("y", y);
            this.x = x;
            this.y = y;
        }

        private void checkVal(String s, int i) {
            if (i < 0) throw new IllegalArgumentException("'" + s + "' must be >= 0");
        }

        public String toString() {
            return "[" + x + "," + y + "]";
        }
    }
}
