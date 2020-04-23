/*
 * KeyStrokeProducer.java
 *
 * Created on 30 December 2004, 18:23
 */

package tg.produce;

import tg.KeyStrokeBuffer;
import tg.util.Util;

/**
 * A Class that has a companion daemon thread that keeps the given buffer full.
 * NOTE: Only the companion thread should ever 'wait' on this object.
 *
 * @author  root
 */
public class KeyStrokeProducer {
  
  /**
   * The buffer that this producer must keep full.
   */
  private KeyStrokeBuffer buffer;
  private Listener listener; // listens for changes to buffer, and is the lock for the companion thread
  private boolean shutdown = false; // flag for when the producer should shutdown
  private KeyStrokeSource source;
  
  /** Creates a new instance of KeyStrokeProducer */
  public KeyStrokeProducer(KeyStrokeBuffer buffer, KeyStrokeSource source) {
    Util.assertNotNull(buffer, "' buffer' cannot be null");
    
    Util.assertNotNull(source, "The source for key strokes cannot be null");
    
    // save buffer
    this.buffer = buffer;
    this.source = source;
    this.listener = new Listener();
    buffer.addListener(this.listener);
    
    // launch the thread
    Thread t = new Thread(new ProducerLoop());
    t.setDaemon(true); // will not prevent shutdown
    t.start();
  }
  
  /**
   * Shuts down this producer and returns 'null' for convenience.
   */
  public KeyStrokeProducer shutdown() {
    // remove listening
    this.buffer.removeListener(this.listener);
    
    // kill off companion thread
    this.shutdown = true; //?? Is there some caching that needs to be synchonised here?
    unpauseThread();

    return null;
  }
  
  /**
   * Brings the thread out of a 'wait' state
   */
  private void unpauseThread() {
    synchronized (this.listener) {
      this.listener.notifyAll();
    }
  }
  
  /**
   * Puts the thread in a 'wait' state.  This should only be called by the ProducerLoop.
   */
  private void pauseThread() {
    synchronized (this.listener) {
      try {
        this.listener.wait();
      }
      catch (InterruptedException ie) {
        // do nothing
      }
    }
  }
  
  /**
   * Getter for property source.
   * @return Value of property source.
   */
  private tg.produce.KeyStrokeSource getSource() {
    return source;
  }
  
  /**
   * The entry point for the Producer thread
   */
  private class ProducerLoop implements Runnable {
    public void run() {
      while (!shutdown) {
        while (!buffer.isFull()) {
          if (getSource() == null) {
            throw new IllegalStateException("No source from which to draw Keystrokes");
          }
          RatedKeyStroke ks = getSource().getKeyStroke();
          buffer.add(ks);
        }
        
        // buffer is now full
        // await notification
        pauseThread();
      }
    }
  }
  
  /**
   * Listens to the buffer to determine when to wake thread.
   */
  private class Listener implements KeyStrokeBuffer.Listener {
    
    public void add(tg.KeyStrokeBuffer.Event evt) {
      // don't care
    }
    
    public void consume(tg.KeyStrokeBuffer.Event evt) {
      // buffer is now less than empty
      // notify producer thread
      unpauseThread();
    }
    
    public void flush() {
      // buffer is now empty
      unpauseThread();
    }
  }
}
