/*
 * Util.java
 *
 * Created on 30 December 2004, 18:27
 */

package tg.util;

import tg.produce.RatedKeyStroke;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author  root
 */
public class Util {
  // The different possible configurations
  public static final String CONFIG_QWERTY = "qwerty";
  public static final String CONFIG_LETTERS_ONLY = "qwerty-letters-only";
  public static final String CONFIG_LOWERCASE_ONLY = "qwerty-lowercase-only";
  public static final String CONFIG_ALPHAGRIP_BASIC = "alphagrip-basic";
  public static final String CONFIG_ALPHAGRIP_SHIFT = "alphagrip-shift";
  public static final String CONFIG_ALPHAGRIP_SHIFT_X2 = "alphagrip-shiftx2";
  public static final String CONFIG_ALPHAGRIP_SHIFT_X3 = "alphagrip";
  public static final String CONFIG_DVORAK = "dvorak";
  
  // not bothered yet...
//  public static final String CONFIG_DVORAK_LETTERS_ONLY = "dvorak-letters-only";
//  public static final String CONFIG_DVORAK_LOWERCASE_ONLY = "dvorak-lowercase-only";
  
  public static final String DEFAULT_CONFIG = CONFIG_QWERTY;
  
  // A map of dvorak to qwerty key strokes (common layout, not strict ANSI)
  // absent keys mean they are the same
  private static final char[][] DVORAK_MAP = new char[][]{
    {'{', '_'},
    {'[', '-'},
    {'}', '+'},
    {']', '='},
    {'\'', 'q'},
    {'"', 'Q'},
    {',', 'w'},
    {'<', 'W'},
    {'.', 'e'},
    {'>', 'E'},
    {'p', 'r'},
    {'P', 'R'},
    {'y', 't'},
    {'Y', 'T'},
    {'f', 'y'},
    {'F', 'Y'},
    {'g', 'u'},
    {'G', 'U'},
    {'c', 'i'},
    {'C', 'I'},
    {'r', 'o'},
    {'R', 'O'},
    {'l', 'p'},
    {'L', 'P'},
    {'?', '{'},
    {'/', '['},
    {'+', '}'},
    {'=', ']'},
    // a is same
    {'o', 's'},
    {'O', 'S'},
    {'e', 'd'},
    {'E', 'D'},
    {'u', 'f'},
    {'U', 'F'},
    {'i', 'g'},
    {'I', 'G'},
    {'d', 'h'},
    {'D', 'H'},
    {'h', 'j'},
    {'H', 'J'},
    {'t', 'k'},
    {'T', 'K'},
    {'n', 'l'},
    {'N', 'L'},
    {'s', ';'},
    {'S', ':'},
    {'-', '\''},
    {'_', '"'},
    {';', 'z'},
    {':', 'Z'},
    {'q', 'x'},
    {'Q', 'X'},
    {'j', 'c'},
    {'J', 'C'},
    {'k', 'v'},
    {'K', 'V'},
    {'x', 'b'},
    {'X', 'B'},
    {'b', 'n'},
    {'B', 'N'},
    // m is same
    {'w', ','},
    {'W', '<'},
    {'v', '.'},
    {'V', '>'},
    {'z', '/'},
    {'Z', '?'},
  };
  
  /**
   * Extra information about an alphagrip.  Specifically, the chars on each key.
   */
  private static char NO_KEY = 0;
  private static final char[][] ALPHAGRIP_MAP = new char[][] {
    {'j', 'J', '@', NO_KEY},
    {'v', 'V', NO_KEY, NO_KEY},
    {'x', 'X', NO_KEY, NO_KEY},
    {'z', 'Z', NO_KEY, NO_KEY},
    {'c', 'C', NO_KEY, NO_KEY},
    {'y', 'Y', NO_KEY, NO_KEY},
    {'k', 'K', NO_KEY, NO_KEY},
    {'l', 'L', NO_KEY, NO_KEY},
    {'g', 'G', '#', '~'},
    {'t', 'T', '1', '\''},
    {'r', 'R', NO_KEY, '%'},
    {'f', 'F', '3', '"'},
    {'d', 'D', '+', '('},
    {'w', 'W', '-', '{'},
    {'q', 'Q', '=', '['},
    {'e', 'E', '5', ')'},
    {'s', 'S', '7', '}'},
    {'a', 'A', '9', ']'},
    {'m', 'M', '$', '^'},
    {'n', 'N', '2', '?'},
    {'h', 'H', NO_KEY, '_'},
    {'u', 'U', '4', '!'},
    {'i', 'I', '6', '&'},
    {'o', 'O', '8', '<'},
    {'p', 'P', '0', '`'},
    {',', ';', NO_KEY, '|'},
    {'.', ':', '*', '>'},
    {'b', 'B', '/', '\\'},
    {' ', NO_KEY, NO_KEY, NO_KEY},
  };
  
  /** Creates a new instance of Util */
  private Util() {
  }
  
  /**
   * Throws and IllegalArgumentException with the given message if the given object is null.
   */
  public static void assertNotNull(Object obj, String message) {
    if (obj == null) {
      throw new IllegalArgumentException(message);
    }
  }
  
//  public static Curve getUS101RecentWeight() {
//    StepCurve result = new StepCurve(new Curve.Point(1, 10), new Curve.Point(100, 10));
//    return result;
//  }
//  
//  /**
//   * A starting point for US101 key's rating weights
//   */
//  public static Curve getUS101RatingWeight() {
//    StepCurve result = new StepCurve(new Curve.Point(10, 30), new Curve.Point(50, 1));
//    return result;
//  }
//  
  /**
   * Return the keystrokes for the designated configuration
   */
  public static RatedKeyStroke[] getKeys(String config) {
    RatedKeyStroke[] result = null;
    UniqueKeystrokeBuilder b = new UniqueKeystrokeBuilder();
    
    if (CONFIG_DVORAK.equalsIgnoreCase(config)) {
      result = getKeys(CONFIG_QWERTY);
      convert(result, new KeyMap(DVORAK_MAP));
    }
    else if (CONFIG_ALPHAGRIP_BASIC.equalsIgnoreCase(config)) {
      // ag lowercase only
      b.add(" lteni", 15);
      b.add("safdu,op", 23);
      b.add("cywq.b", 25);
      b.add("rgkhm", 26);
      b.add("jvxz", 35);
      b.assertSize(29);
    }
    else if (CONFIG_ALPHAGRIP_SHIFT.equalsIgnoreCase(config)) {
      // ag shift chars
      AlphagripKeyInfo.populate(b, AlphagripKeyInfo.SHIFT);
      b.assertSize(57);
    }
    else if (CONFIG_ALPHAGRIP_SHIFT_X2.equalsIgnoreCase(config)) {
      // ag shift chars
      AlphagripKeyInfo.populate(b, AlphagripKeyInfo.RED_SHIFT);
      b.assertSize(57 + 18);
    }
    else if (CONFIG_ALPHAGRIP_SHIFT_X3.equalsIgnoreCase(config)) {
      // ag shift chars
      AlphagripKeyInfo.populate(b, AlphagripKeyInfo.GREEN_SHIFT);
      b.assertSize(57 + 18 + 20);
    }
    else if (CONFIG_LOWERCASE_ONLY.equalsIgnoreCase(config)) {
      // lowercase only
      //b.add(" ", 10);
      b.add(" asdfjkl", 15);
      b.add("weruio", 23);
      b.add("ghmcxnv", 25);
      b.add("zt", 26);
      b.add("bqpy", 35);
      b.assertSize(27);
    }
    else if (CONFIG_LETTERS_ONLY.equalsIgnoreCase(config)) {
      // letters only
      //b.add(" ", 10);
      b.add(" asdfjkl", 15);
      b.add("ASDFJKL", 20);
      b.add("weruio", 23);
      b.add("ghmcxnv", 25);
      b.add("zt", 26);
      b.add("WERUIO", 28);
      b.add("GHMCNVXT", 30);
      b.add("Z", 32);
      b.add("bqpy", 35);
      b.add("BQPY", 40);
      b.assertSize(53);
    }
    else {
      // default to qwerty
      b.add(" ", 10);
      b.add("asdfjkl;", 15);
      b.add("ASDFJKL:", 20);
      b.add("weruio", 23);
      b.add("ghmc,xnv", 25);
      b.add(".z/t", 26);
      b.add("WERUIO", 28);
      b.add("GHMCNV<X>T", 30);
      b.add("Z?", 32);
      b.add("b'qpy[", 35);
      b.add("]B\"QPY{", 40);
      b.add("}\\", 45);
      b.add("|", 50);
      b.add("2390", 53);
      b.add("4578-", 55);
      b.add("@#()", 58);
      b.add("$%&*_`=16", 60);
      b.add("~+!^", 65);
      b.assertSize(95);
    }
    
    if (result == null) {
      // builder was used
      result = b.getKeys();
    }
    return result;
  }
  
  /**
   * Convert the given RatedKeyStrokes using the KeyMap.
   */
  private static void convert(RatedKeyStroke[] keyStrokes, KeyMap keyMap) {
    for (int i = 0; i < keyStrokes.length; i++) {
      // for each keystroke
      RatedKeyStroke ks = keyStrokes[i];
      ks.setKeyChar(keyMap.getDest(ks.getKeyChar()));
    }
  }
  
  /**
   * Helper class for building up large keystroke arrays
   */
  private static class UniqueKeystrokeBuilder {
    private Collection keys = new ArrayList();
    private Set chars = new HashSet();
    
    /**
     * Add the specified keystroke, throw an exception if the character is already present
     */
    public void add(char c, int rating) {
      if (!chars.add(new Character(c))) {
        // character already added
        throw new IllegalArgumentException("Character '" + c + "' has already been added");
      }
      keys.add(new RatedKeyStroke(c, rating));
    }
    
    /**
     * Add each of the characters in the given string
     */
    public void add(String chars, int rating) {
      for (int i = 0; i < chars.length(); i++) {
        add(chars.charAt(i), rating);
      }
    }
    
    /**
     * Return the accumulation of keys so far in an array
     */
    public RatedKeyStroke[] getKeys() {
      RatedKeyStroke[] result = new RatedKeyStroke[keys.size()];
      keys.toArray(result);
      return result;
    }
    
    /**
     * Throw and exception if the number of keys does not equal that given
     */
    public void assertSize(int size) {
      if (keys.size() != size) {
        throw new RuntimeException("Size is incorrect.  Expected " + size + " but is actually " + keys.size());
      }
    }
  } // END of UniqueKeystrokeBuilder
  
  /**
   * Maintains a mapping of chars for similar keyboard types
   */
  private static class KeyMap {
    private static int SRC = 1; // yeah..it was easier to code the array this way.
    private static int DEST = 0;
    private char[][] charMap;
    
    public KeyMap(char[][] charMap) {
      // check keys
      assertUniqueChars(charMap, SRC);
      assertUniqueChars(charMap, DEST);
      this.charMap = charMap;
    }
    
//    /**
//     * Convert the given series of source chars to a series of dest chars
//     */
//    public String convertToDest(String str) {
//      char[] result = new char[str.length()];
//      for (int i = 0; i < result.length; i++) {
//        // for each source char
//        result[i] = getDest(str.charAt(i));
//      }
//      return new String(result);
//    }
    
//    /**
//     * Returns the source equiv char for the given dest char
//     */
//    public char getSource(char destChar) {
//      char result = destChar; // default to same key
//      int row = find(destChar, DEST);
//      if (row >= 0) {
//        // dest char found
//        result = this.charMap[row][SRC];
//      }
//      return result;
//    }

    /**
     * Returns the dest equiv char for the given source char
     */
    public char getDest(char srcChar) {
      char result = srcChar; // default to same key
      int row = find(srcChar, SRC);
      if (row >= 0) {
        // src char found
        result = this.charMap[row][DEST];
      }
      Config.output("Converted " + srcChar + "->" + result);
      return result;
    }
    
    /**
     * Return the row index of the given char in the specified col or -1 if it is not present.
     */
    private int find(char ch, int col) {
      int result = -1; 
      for (int i = this.charMap.length - 1; i >= 0; i--) {
        // for each char (starting from end)
        char chr = this.charMap[i][col];
        if (chr == ch) {
          // char found
          result = i;
          break;
        }
      }
      return result;
    }
    
    /**
     * Throw an exception if the chars in the specified column of the 2d char array
     * are not unique.
     */
    private void assertUniqueChars(char[][] charArr, int col) {
      Set charSet = new HashSet();
      for (int i = 0; i < charArr.length; i++) {
        // for each char
        char ch = charArr[i][col];
        if (!charSet.add(new Character(ch))) { // .valueOf is JDK 1.5
          // character was already present
          throw new RuntimeException("Character " + ch + " is already present in (0-based) column " + col);
        }
      }
    }
  } // END of KeyMap
  
  private static class AlphagripKeyInfo {
    private static final int BASE = 0;
    public static final int SHIFT = 1;
    public static final int RED_SHIFT = 2;
    public static final int GREEN_SHIFT = 3;
    
    /**
     * Populate the builder with the difficulty ratings, up to the given 
     * shiftLevel of difficulty.
     */
    public static void populate(UniqueKeystrokeBuilder builder, int shiftLevel) {
      RatedKeyStroke[] ratings = getKeys(CONFIG_ALPHAGRIP_BASIC); // use base difficulties.
      
      for (int i = 0; i < ratings.length; i++) {
        // for each base char
        char baseChar = ratings[i].getKeyChar();
        int baseRating = ratings[i].getRating();
        
        builder.add(baseChar, baseRating);
        
        
        int ix = indexOf(baseChar, BASE);
        if (ix < 0) {
          throw new RuntimeException("Unable to locate base char: " + baseChar);
        }

        if (SHIFT <= shiftLevel) {
          add(builder, ix, SHIFT, baseRating);
        }
        if (RED_SHIFT <= shiftLevel) {
          add(builder, ix, RED_SHIFT, baseRating);
        }
        if (GREEN_SHIFT <= shiftLevel) {
          add(builder, ix, GREEN_SHIFT, baseRating);
        }
      }
    }
    
    /**
     * Add the specified char rating if applicable.
     */
    private static void add(UniqueKeystrokeBuilder builder, int ix, int modifier, int baseRating) {
      if (hasChar(ix, modifier)) {
        // char avail
        builder.add(getChar(ix, modifier), getRating(baseRating, modifier));
      }
    }
    
    /**
     * Return the new rating derived from base rating and modifier.
     */
    private static int getRating(int baseRating, int modifer) {
      int result = baseRating;
      switch (modifer) {
        case SHIFT:
          result += 10; // significantly more difficult
          break;
        case RED_SHIFT:
          result += 15; // little more difficult that normal shift
          break;
        case GREEN_SHIFT:
          result += 25; // significantly more difficult than red shift
          break;
        default:
          // nothing
      }
      return result;
    }
    
    /**
     * Returns the index of the given character stored under the specified modifer
     * or '-1' if the char is not found.
     */
    private static int indexOf(char ch, int modifer) {
      int result = -1;
      for (int i = 0; i < ALPHAGRIP_MAP.length; i++) {
        // for each row
        if (getChar(i, modifer) == ch) {
          // found
          result = i;
          break;
        }
      }
      return result;
    }
    
    /**
     * Returns 'true' if there is key at the given array location.
     */
    private static boolean hasChar(int ix, int modifier) {
      return getChar(ix, modifier) != NO_KEY;
    }
    
    /**
     * Returns the char at the given co-orders.
     */
    private static char getChar(int ix, int modifier) {
      return ALPHAGRIP_MAP[ix][modifier];
    }
  }
} // END Main Class
