/*
 * RandomUtilTest.java
 *
 * Created on 19 January 2005, 19:46
 */

package tg.random;

/**
 *
 * @author  root
 */
public class RandomUtilTest extends junit.framework.TestCase {
  public void testChoose() throws Exception {
    
    int[] weights = new int[5];
    weights[0] = 0;
    weights[1] = 3;
    weights[2] = 0;
    weights[3] = 2; 
    weights[4] = 0; 
    
    int[] occurences = new int[weights.length];
    
    for (int i = 0; i < 3000; i++) {
      int index = RandomUtil.choose(weights);
      occurences[index] += 1;
    }
    
    System.out.println("Weights: " + stringRep(weights));
    System.out.println("Results: " + stringRep(occurences));
    
  }
  
  /**
   * Output the given array as [2,8,2]
   */
  private String stringRep(int[] vals)  {
    StringBuffer sb = new StringBuffer();
    sb.append('[');
    for (int i = 0; i < vals.length; i++) {
      if (i > 0) {
        // notthe first
        // need separator
        sb.append(',');
      }
      sb.append(vals[i]);
    }
    sb.append(']');
    return sb.toString();
  }
}
