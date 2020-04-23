/*
 * CurvePointTest.java
 *
 * Created on 19 January 2005, 22:23
 */

package tg.random;

import junit.framework.TestCase;

/**
 *
 * @author  root
 */
public class CurvePointTest extends TestCase {
  public void testBadPoint() throws Exception {
    
    new Curve.Point(0,0);
    new Curve.Point(1,1);
    
    try {
      new Curve.Point(-1, 0);
      fail("Was able to set 'x' to less than 0");
    }
    catch (IllegalArgumentException e) {
      // expected
    }
    
    
    try {
      new Curve.Point(0, -1);
      fail("Was able to set 'y' to less than 0");
    }
    catch (IllegalArgumentException e) {
      // expected
    }
    
    try {
      new Curve.Point(-1, -1);
      fail("Was able to set 'x' AND 'y' to less than 0");
    }
    catch (IllegalArgumentException e) {
      // expected
    }
  }
}
