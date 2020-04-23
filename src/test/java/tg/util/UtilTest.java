/*
 * UtilTest.java
 *
 * Created on 21 January 2005, 21:12
 */

package tg.util;

import junit.framework.TestCase;

/**
 * Test cases for 'Util'
 *
 * @author  root
 */
public class UtilTest extends TestCase {
  
  public void testKeys() throws Exception {
    Object[] keys = Util.getKeys(null);
  }
  
  public void testLettersOnly() throws Exception {
    Object[] keys = Util.getKeys(Util.CONFIG_LETTERS_ONLY);
  }
  
  public void testLowercaseOnly() throws Exception {
    Object[] keys = Util.getKeys(Util.CONFIG_LOWERCASE_ONLY);
  }
  
  public void testAlphagripLowercaseOnly() throws Exception {
    Object[] keys = Util.getKeys(Util.CONFIG_ALPHAGRIP_BASIC);
  }

  public void testAlphagripShift() throws Exception {
    Object[] keys = Util.getKeys(Util.CONFIG_ALPHAGRIP_SHIFT);
  }

  public void testAlphagripRedShift() throws Exception {
    Object[] keys = Util.getKeys(Util.CONFIG_ALPHAGRIP_SHIFT_X2);
  }

  public void testAlphagripGreenShift() throws Exception {
    Object[] keys = Util.getKeys(Util.CONFIG_ALPHAGRIP_SHIFT_X3);
  }
  
  public void testDvorak() throws Exception {
    Object[] keys = Util.getKeys(Util.CONFIG_DVORAK);
  }
}
