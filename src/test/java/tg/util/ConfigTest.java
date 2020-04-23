/*
 * ConfigTest.java
 *
 * Created on 24 January 2005, 21:49
 */

package tg.util;

import junit.framework.TestCase;

/**
 *
 * @author  root
 */
public class ConfigTest extends TestCase {

  public void testRatingWeight() throws Exception {
    // use other tests
    Config.createRatingWeight(Util.getKeys(null));
  }

  public void testRecentWeight() throws Exception {
    // use other tests
    Config.createRecentUseWeight(Util.getKeys(null));
  }
}
