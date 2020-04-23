/*
 * Assessment.java
 *
 * Created on 25 January 2005, 20:21
 */

package tg.assess;

/**
 * Holds assessment information for conveying to the user interface
 * @author  root
 */
public class Assessment {
  int rating;
  int accuracy;
  int speed;
  int keyCount;
  int correctCount;
  int overAll;
  
  public Assessment() {
  }
  
  /** Creates a new instance of Assessment */
  public Assessment(int rating, int accuracy, int speed, int keyCount, int correctCount, int overAll) {
    this.rating = rating;
    this.accuracy = accuracy;
    this.speed = speed;
    this.keyCount = keyCount;
    this.correctCount = correctCount;
    this.overAll = overAll;
  }
  
  /**
   * Indicates whether the assessment is all zero's
   */
  public boolean isBlank() {
    return (this.rating == 0) 
        && (this.accuracy == 0) 
        && (this.speed == 0) 
        && (this.overAll == 0) 
        && (this.correctCount == 0) 
        && (this.keyCount == 0);
  }
  
  public int getRating() {return this.rating;}
  public int getAccuracy() {return this.accuracy;}
  public int getSpeed() {return this.speed;}
  public int getKeyCount() {return this.keyCount;}
  public int getCorrectCount() {return this.correctCount;}
  public int getOverall() {return this.overAll;}
}
