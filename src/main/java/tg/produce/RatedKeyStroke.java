/*
 * RatedKeyStroke.java
 *
 * Created on 30 December 2004, 18:17
 */
package tg.produce;

import tg.KeyStroke;

/**
 * A Keystroke with a difficulty rating.
 */
public class RatedKeyStroke implements KeyStroke {
    private char keyChar;
    private final int rating;

    public RatedKeyStroke(char keyChar, int rating) {
        this.keyChar = keyChar;
        this.rating = rating;
    }

    public char getKeyChar() {
        return this.keyChar;
    }

    /**
     * Getter for the difficulty of this keystroke
     */
    public int getRating() {
        return this.rating;
    }

    public void setKeyChar(char _keyChar) {
        this.keyChar = _keyChar;
    }
}
