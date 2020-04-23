/*
 * UserPref.java
 *
 * Created on 2 April 2006, 22:07
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package tg.util;

/**
 * Describes the user preferences in the system.
 *
 * @author root
 */
public class UserPref {
  private int targetKeyStrokeRate = Config.DEFAULT_PERFECT_KEY_RATE;
   private int difficultyGainQuotient = Config.DEFAULT_DIFFICULTY_GAIN_QUOTIENT;

  /**
   * The keys per second that is considered perfect / 100% speed.
   */
  public int getTargetKeyStrokeRate() {
    return targetKeyStrokeRate;
  }

  public void setTargetKeyStrokeRate(int targetKeyStrokeRate) {
    this.targetKeyStrokeRate = targetKeyStrokeRate;
  }

  /**
   * The inverse of the responsiveness of the auto difficulty adjustment.
   */
  public int getDifficultyGainQuotient() {
    return difficultyGainQuotient;
  }

  public void setDifficultyGainQuotient(int difficultyGainQuotient) {
    this.difficultyGainQuotient = difficultyGainQuotient;
  }
  
  /**
   * The max duration considered to be 0%.
   */
  public int getDurationMax() {
    // a factor of the target key stroke rate, but at least a given threshold.
    // EG: 2sec or 10 * the perfect duration, which ever is largest.
    return Math.max(Config.DURATION_MAX_THRESHOLD, 
                   (Config.DURATION_MAX_FACTOR * 1000) / getTargetKeyStrokeRate());
  }
}
