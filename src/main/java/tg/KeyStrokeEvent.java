/*
 * KeyStrokeEvent.java
 *
 * Created on 30 December 2004, 18:08
 */

package tg;

/**
 * A Keystroke at a particular point in time.
 * @author  root
 */
public class KeyStrokeEvent {
  
  private KeyStroke keyStroke;
  private long time;
  
  /**
   * Complete constructor
   */
  public KeyStrokeEvent(KeyStroke ks, long time) {
    if (ks == null) {
      throw new IllegalArgumentException("'keyStroke' cannot be null");
    }
    this.keyStroke = ks;
    this.time = time;
  }
  
  /**
   * Getter
   */
  public KeyStroke getKeyStroke() {
    return this.keyStroke;
  }
  
  /**
   * Getter
   */
  public long getTime() {
    return this.time;
  }
}
