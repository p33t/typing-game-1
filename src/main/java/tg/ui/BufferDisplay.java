/*
 * BufferDisplay.java
 *
 * Created on 30 December 2004, 15:57
 */

package tg.ui;

import tg.KeyStroke;
import tg.KeyStrokeBuffer;

import javax.swing.*;

/**
 * Display's what is going on inside a buffer.
 * @TODO: Make this more efficient (?)
 * @author  root
 */
public class BufferDisplay implements KeyStrokeBuffer.Listener{

  private KeyStrokeBuffer keyStrokeBuffer;
  private JLabel displayLabel;
  private String originalString;
  
  /** Creates a new instance of BufferDisplay */
  public BufferDisplay(KeyStrokeBuffer buffer, JLabel label) {
    if (buffer == null) {
      throw new IllegalArgumentException("'buffer' cannot be null");
    }
    if (label == null) {
      throw new IllegalArgumentException("'label' cannot be null");
    }
    
    // save vars
    this.keyStrokeBuffer = buffer;
    this.displayLabel = label;
    this.originalString = label.getText();
    
    // initialize
    buffer.addListener(this);
    refreshDisplay();
  }
  
  /**
   * Refreshes the display from the buffer
   */
  private void refreshDisplay() {
    KeyStroke[] contents = this.keyStrokeBuffer.getContents();
    StringBuffer sb = new StringBuffer(contents.length);
    for (int i = 0; i < contents.length; i++) {
      sb.append(contents[i].getKeyChar());
    }
    if (sb.length() > 0) {
      // there are keys in the buffer
      // write to screen
      this.displayLabel.setText(sb.toString());
    }
    else {
      //empty buffer
      // restore original string
      // this will enable help messages to remain displayed
      this.displayLabel.setText(this.originalString);
    }
  }
  
  public void add(KeyStrokeBuffer.Event evt) {
    refreshDisplay();
  }
  
  public void consume(KeyStrokeBuffer.Event evt) {
    refreshDisplay();
  }
  
  public void flush() {
    refreshDisplay();
  }
}