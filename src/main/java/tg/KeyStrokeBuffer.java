/*
 * KeyStrokeBuffer.java
 *
 * Created on 30 December 2004, 15:38
 */

package tg;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Represents a buffer that contains keystrokes.
 * This is a multi-thread safe class.
 * There is a companion thread to process events, and an event queue that ensures events are fired in 
 * chronological order.
 * @author  root
 */
public class KeyStrokeBuffer {
  
  private static final Object EVENT_ADD = "add";
  private static final Object EVENT_CONSUME = "consume";
  private static final Object EVENT_FLUSH = "flush";
    
  // event listeners
  private List listeners = new ArrayList();
  
  // event queue
  private List eventQueue = new LinkedList();
  private boolean shutdown = false; // indicates that buffer has been shut down
  
  // the buffer of KeyStrokes
  private List contents = null;
  private int capacity = 0;
  
  public KeyStrokeBuffer(int capacity) {
    if (capacity < 0) {
      throw new IllegalArgumentException("Cannot have capacity less that zero.  Desired capacity was " + capacity);
    }
    this.capacity = capacity;
    contents = new ArrayList(capacity);
    
    // launch event thread
    Thread t = new Thread(new EventProcessor());
    t.setDaemon(true); // will not prevent shutdown
    t.start();
  }
  
  /**
   * Causes the buffer to flush and raise the corresponding event
   */
  public void flush() {
    synchronized (eventQueue) {
      this.contents.clear();
      queueAndProcessEvent(new Event(null, EVENT_FLUSH));
    }
  }
  
  /**
   * Shuts down this buffer's events and returns 'null' for convenience
   */
  public KeyStrokeBuffer shutdown() {
    // remove listening
    this.listeners.clear();
    
    // kill off companion thread
    this.shutdown = true; //?? Is there some caching that needs to be synchonised here?
    synchronized (eventQueue) {
      eventQueue.notifyAll(); // will cause the waiting thread to see the shutdown flag
    }

    return null;
  }

  
  /**
   * Adds the given keystroke to the buffer.  If the buffer was full and the keystroke could not be added then
   * return false.
   */
  public boolean add(KeyStroke ks) {
    if (ks == null) {
      throw new IllegalArgumentException("'ks' cannot be null");
    }
    synchronized (contents) {
      boolean result = getSize() < getCapacity();
      if (result) {
        // there is room for another
        contents.add(ks);
        queueAndProcessEvent(new Event(ks, EVENT_ADD));
      }
      else {
        // no room
        // false will be returned accordingly
      }
      return result;
    }
  }
  
  /**
   * Consumes the next Keystroke in the buffer, and return it.  If the buffer is empty, return null.
   */
  public KeyStroke consume() {
    KeyStroke result = null;
    synchronized (contents) {
      if (contents.size() > 0) {
        // there is a keystroke available
        result = (KeyStroke) contents.remove(0);
        queueAndProcessEvent(new Event(result, EVENT_CONSUME));
      }
      else {
       // no more keystrokes
       // let null get returned
      }
      return result;
    }
  }

  /**
   * Processes the given event , observing any events that are being processed FIFO
   */
  public void queueAndProcessEvent(Event evt) {
    synchronized (eventQueue) {
      eventQueue.add(evt);
      eventQueue.notifyAll();
    }
  }
  
// Not sure how to do comparison...there are 'RatedKeyStrokes' in the system.
//  /**
//   * Consume the next Keystroke in the budder IFF it equals that given.  This is needed
//   * for multi-threading where a client must be able to peek and then consume the peeked keystroke.
//   */
//  public boolean consume(KeyStroke ks) {
//    synchronized (contents) {
//      if (ks.equals(peek())) {
//        contents.remove(0);
//        return true;
//      }
//      else {
//        return false;
//      }
//    }
//  }
  
  /**
   * Returns the next keystroke that would be returned from 'consume()'
   *
   * @see #consume()
   */
  public KeyStroke peek() {
    synchronized (contents) {
      if (isEmpty()) {
        return null;
      }
      else {
        return (KeyStroke) contents.get(0);
      }
    }
  }
  
  /**
   * Indicates how many KeyStrokes are in the buffer
   */
  public int getSize() {
    return this.contents.size();
  }
  
  /**
   * Indicates the maximum size that this buffer can be
   */
  public int getCapacity() {
    return this.capacity;
  }
  
  /**
   * Indicates that the buffer is full IE: size == capacity.
   */
  public boolean isFull() {
    return getSize() >= getCapacity();
  }
  
  public boolean isEmpty() {
    return getSize() == 0;
  }
  
  /**
   * Returns a copy of the contents of the buffer
   */
  public KeyStroke[] getContents() {
    synchronized (contents) {
      KeyStroke[] result = new KeyStroke[getSize()];
      contents.toArray(result);
      return result;
    }
  }
  
  /**
   * Adds the given listener to the list of objects that will recieve event notification.
   */
  public void addListener(Listener l){
    if (l != null) {
      this.listeners.add(l);
    }
  }
  
  /**
   * Removes the given listener from the list of objects that will recieve event notification.
   */
  public void removeListener(Listener l) {
    if (l != null) {
      this.listeners.remove(l);
    }
  }
  
  /**
   * Interfaces required of listeners
   */
  public interface Listener {
    public void add(Event evt);
    public void consume(Event evt);
    public void flush();
  } // END OF Listener
  
  /**
   * A rich parameter for events
   */
  public class Event {
    KeyStroke keyStroke = null;
    Object type = null;
    
    private Event(KeyStroke ks, Object type) {
      this.keyStroke = keyStroke;
      this.type = type;
    }
    
    public KeyStroke getKeyStroke(){
      return this.keyStroke;
    }
    
    /**
     * Notifies all listeners of this event
     */
    private void fire() {
      for (Iterator it = KeyStrokeBuffer.this.listeners.iterator(); it.hasNext(); ) {
        Listener l = (Listener) it.next();
        if (EVENT_ADD.equals(this.type)) {
          l.add(this);
        }
        else if (EVENT_CONSUME.equals(this.type)) {
          l.consume(this);
        }
        else if (EVENT_FLUSH.equals(this.type)) {
          l.flush();
        }
        else {
          // unknown type
          throw new RuntimeException("Unable to fire event '" + this.type + "'");
        }
      }
    }
  } // END OF Event

  /**
   * Processes events for this keystroke buffer
   */
  private class EventProcessor implements Runnable {
    
    public void run() {
      while (!KeyStrokeBuffer.this.shutdown) {
        // still running
        Event nextEvent = null;
        synchronized (eventQueue) {
          
          // pause if necessary
          if (eventQueue.isEmpty()) {
            // no more events
            try {
              eventQueue.wait();
            }
            catch (InterruptedException ie) {
              // do nothing
            }
          }
          
          // process the next event that may have appeared
          if (!eventQueue.isEmpty()) {
            // there is another event
            // dequeue it
            nextEvent = (Event) eventQueue.remove(0);
          }
        }

        if (nextEvent != null) {
          // the queue was empty
          nextEvent.fire();
        }
      }
    }
  } // END OF EventProcessor
} // END OF Main class
