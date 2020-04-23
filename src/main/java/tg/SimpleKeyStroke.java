/*
 * SimpleKeyStroke.java
 *
 * Created on 30 December 2004, 15:36
 */

package tg;

/**
 * A default implementation of KeyStroke
 *
 * @author  root
 */
public class SimpleKeyStroke implements KeyStroke {
  private char keyChar;

  public char getKeyChar() {
    return this.keyChar;
  }
  
  public void setKeyChar(char _keyChar) {
    this.keyChar = _keyChar;
  }
}
