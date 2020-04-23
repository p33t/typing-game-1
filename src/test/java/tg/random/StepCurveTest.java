/*
 * StepCurveTest.java
 *
 * Created on 19 January 2005, 22:26
 */

package tg.random;

import junit.framework.TestCase;

/**
 *
 * @author  root
 */
public class StepCurveTest extends TestCase {

  public void testBadCurve() throws Exception {
    
    new StepCurve(new Curve.Point(1,1), new Curve.Point(1, 2)); // same x
    new StepCurve(new Curve.Point(1,1), new Curve.Point(1,1)); // same point
    
    try {
      new StepCurve(new Curve.Point(1,0), new Curve.Point(0,0));
      fail("Was able to set p1.x > p2.x");
    }
    catch (IllegalArgumentException e) {
      // expected
    }
  }
  
  public void testFlat() {
    StepCurve sc = new StepCurve(new Curve.Point(10, 10), new Curve.Point(20,10));
    assertEquals("Failed 12", 10, sc.getY(12));
  }
  
  public void testPositive() {
    StepCurve sc = new StepCurve(new Curve.Point(0, 0), new Curve.Point(10, 10));
    assertEquals("Failed 0", 0, sc.getY(0));
    assertEquals("Failed 5", 5, sc.getY(5));
    assertEquals("Failed 2", 2, sc.getY(2));
    assertEquals("Failed 9", 9, sc.getY(9));
    assertEquals("Failed 11", 10, sc.getY(11));
  }

  public void testPositive2() {
    StepCurve sc = new StepCurve(new Curve.Point(10, 10), new Curve.Point(20, 20));
    
    assertEquals("Failed 0", 10, sc.getY(10));
    assertEquals("Failed 15", 15, sc.getY(15));
    assertEquals("Failed 12", 12, sc.getY(12));
    assertEquals("Failed 19", 19, sc.getY(19));
    assertEquals("Failed 21", 20, sc.getY(21));
  }

  public void testPositive3() {
    StepCurve sc = new StepCurve(new Curve.Point(10, 10), new Curve.Point(20, 20));
    sc.setP1X(5); // slide curve down to '5'
    
    assertEquals("Failed 0", 10, sc.getY(0));
    assertEquals("Failed 15", 20, sc.getY(15));
    assertEquals("Failed 12", 17, sc.getY(12));
    assertEquals("Failed 19", 20, sc.getY(19));
    assertEquals("Failed 21", 20, sc.getY(21));
  }
  
  public void testNegative() {
    StepCurve sc = new StepCurve(new Curve.Point(0, 10), new Curve.Point(10, 0));
    assertEquals("Failed 5", 5, sc.getY(5));
    assertEquals("Failed 2", 8, sc.getY(2));
    assertEquals("Failed 9", 1, sc.getY(9));
  }
}
