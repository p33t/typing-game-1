/*
 * TestKeyStrokeSource.java
 *
 * Created on 19 January 2005, 19:15
 */

package tg.produce;

/**
 *
 * @author  root
 */
public class TestKeyStrokeSource implements KeyStrokeSource {
  public RatedKeyStroke getKeyStroke() {
    char c = (Math.random() > 0.5)? 'f': 'j';
    RatedKeyStroke result = new RatedKeyStroke(c, 1);
    return result;
  }
}
