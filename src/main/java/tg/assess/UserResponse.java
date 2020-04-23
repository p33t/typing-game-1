/*
 * UserResponse.java
 *
 * Created on 30 December 2004, 18:57
 */

package tg.assess;

import tg.KeyStroke;
import tg.produce.RatedKeyStroke;
import tg.util.Util;

/**
 * A response from a user
 *
 * @author  root
 */
public class UserResponse {
  private RatedKeyStroke desiredKeyStroke;
  private KeyStroke resultingKeyStroke;
  private long time;
  
  /**
   * Getter for property desiredKeyStroke.
   * @return Value of property desiredKeyStroke.
   */
  public RatedKeyStroke getDesiredKeyStroke() {
    return desiredKeyStroke;
  }
  
  /**
   * Setter for property desiredKeyStroke.
   * @param desiredKeyStroke New value of property desiredKeyStroke.
   */
  public void setDesiredKeyStroke(RatedKeyStroke desiredKeyStroke) {
    this.desiredKeyStroke = desiredKeyStroke;
  }
  
  /**
   * Getter for property resultingKeyStroke.
   * @return Value of property resultingKeyStroke.
   */
  public tg.KeyStroke getResultingKeyStroke() {
    return resultingKeyStroke;
  }
  
  /**
   * Setter for property resultingKeyStroke.
   * @param resultingKeyStroke New value of property resultingKeyStroke.
   */
  public void setResultingKeyStroke(tg.KeyStroke resultingKeyStroke) {
    this.resultingKeyStroke = resultingKeyStroke;
  }
  
  /**
   * Getter for property time.
   * @return Value of property time.
   */
  public long getTime() {
    return time;
  }
  
  /**
   * Setter for property time.
   * @param time New value of property time.
   */
  public void setTime(long time) {
    this.time = time;
  }
  
  /**
   * Indicates whether or not this User Response is acceptable, and the relevant buffer element can be consumed
   */
  public boolean isAcceptable() {
    Util.assertNotNull(getResultingKeyStroke(), "There is no 'resultingKeyStroke'");
    Util.assertNotNull(getDesiredKeyStroke(), "There is no 'desiredKeyStroke'");
    
    boolean result = true;
    
    result = result && (getResultingKeyStroke().getKeyChar() == getDesiredKeyStroke().getKeyChar());
    
    return result;
  }
}
