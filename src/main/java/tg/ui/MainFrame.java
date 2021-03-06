/*
 * MainApplet.java
 *
 * Created on 30 December 2004, 15:55
 */

package tg.ui;

import tg.KeyStroke;
import tg.KeyStrokeBuffer;
import tg.SimpleKeyStroke;
import tg.assess.Assessment;
import tg.assess.AssessmentListener;
import tg.assess.RunningAssessor;
import tg.assess.UserResponse;
import tg.produce.KeyStrokeProducer;
import tg.produce.KeyStrokeSource;
import tg.produce.RandomSource;
import tg.produce.RatedKeyStroke;
import tg.random.Curve;
import tg.random.StepCurve;
import tg.util.Config;
import tg.util.UserPref;
import tg.util.Util;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.KeyEvent;

/**
 *
 * @author  root
 */
public class MainFrame extends javax.swing.JFrame implements AssessmentListener {

  private KeyStrokeBuffer inputFeedbackBuffer = null;
  private KeyStrokeBuffer promptBuffer = null;
  private KeyStrokeBuffer promptHistoryBuffer = null;
  private KeyStrokeProducer producer = null;
  private RunningAssessor assessor = null;
  
  private StepCurve ratingWeight = null; // for adjusting difficulty
  
  private String scorePrefix = "Score ";
  private String keysPerSecPrefix = "Best Speed: ";
  private String keysPerSecSuffix = "";
  
  /** Initializes the applet MainApplet */
  public void init() {
    initComponents();
    this.scorePrefix = ((TitledBorder) this.pnlEast.getBorder()).getTitle();
    this.sldrKeys.setMaximum(Config.HISTORY_SIZE);
    this.sldrKeys.setValue(Config.HISTORY_SIZE);
    this.sldrKeysPerSec.setMinimum(Config.EASIEST_PERFECT_KEY_RATE);
    this.sldrKeysPerSec.setMaximum(Config.HARDEST_PERFECT_KEY_RATE);
    this.sldrKeysPerSec.setValue(Config.DEFAULT_PERFECT_KEY_RATE);
    syncDetailedDisplay();
  }
  
  /** This method is called from within the init() method to
   * initialize the form.
   * WARNING: Do NOT modify this code. The content of this method is
   * always regenerated by the Form Editor.
   */
  // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
  private void initComponents() {
    btngrpErrorStyle = new javax.swing.ButtonGroup();
    jLabel4 = new javax.swing.JLabel();
    pnlCentre = new javax.swing.JPanel();
    pnlBuffers = new javax.swing.JPanel();
    lblPromptHist = new javax.swing.JLabel();
    lblPrompt = new javax.swing.JLabel();
    lblInputHist = new javax.swing.JLabel();
    txtInput = new javax.swing.JTextField();
    pnlSettings = new javax.swing.JPanel();
    pnlSettingsLeft = new javax.swing.JPanel();
    btnAbout = new javax.swing.JButton();
    pnlDifficulty = new javax.swing.JPanel();
    chkAdapt = new javax.swing.JCheckBox();
    chkSmallChanges = new javax.swing.JCheckBox();
    pnlErrorStyle = new javax.swing.JPanel();
    radIgnore = new javax.swing.JRadioButton();
    radAccept = new javax.swing.JRadioButton();
    radBuffer = new javax.swing.JRadioButton();
    pnlSettingsRight = new javax.swing.JPanel();
    jPanel4 = new javax.swing.JPanel();
    lblKeysPerSec = new javax.swing.JLabel();
    sldrKeysPerSec = new javax.swing.JSlider();
    chkDetailedScore = new javax.swing.JCheckBox();
    pnlNorth = new javax.swing.JPanel();
    sldrKeys = new javax.swing.JSlider();
    btnReset = new javax.swing.JButton();
    btnCountDown = new javax.swing.JButton();
    pnlEast = new javax.swing.JPanel();
    progOverall = new javax.swing.JProgressBar();
    pnlDetailedScore = new javax.swing.JPanel();
    jPanel2 = new javax.swing.JPanel();
    progAccurracy = new javax.swing.JProgressBar();
    lblAccuracy = new javax.swing.JLabel();
    jLabel2 = new javax.swing.JLabel();
    jPanel1 = new javax.swing.JPanel();
    progSpeed = new javax.swing.JProgressBar();
    lblSpeed = new javax.swing.JLabel();
    jLabel1 = new javax.swing.JLabel();
    jPanel3 = new javax.swing.JPanel();
    progRating = new javax.swing.JProgressBar();
    lblRating = new javax.swing.JLabel();
    jLabel3 = new javax.swing.JLabel();
    pnlWest = new javax.swing.JPanel();
    sldrRating = new javax.swing.JSlider();
    lblDesiredRating = new javax.swing.JLabel();

    jLabel4.setText("jLabel4");

    pnlCentre.setLayout(new java.awt.GridLayout(2, 1));

    pnlBuffers.setLayout(new java.awt.GridLayout(2, 2));

    lblPromptHist.setFont(new java.awt.Font("Courier New", 0, 18));
    lblPromptHist.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
    lblPromptHist.setText("Type this >>>");
    lblPromptHist.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
    pnlBuffers.add(lblPromptHist);

    lblPrompt.setFont(new java.awt.Font("Courier New", 1, 20));
    lblPrompt.setBorder(javax.swing.BorderFactory.createLineBorder(javax.swing.UIManager.getDefaults().getColor("TextField.shadow")));
    pnlBuffers.add(lblPrompt);

    lblInputHist.setFont(new java.awt.Font("Courier New", 0, 18));
    lblInputHist.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
    lblInputHist.setText("In here >>>");
    pnlBuffers.add(lblInputHist);

    txtInput.setFont(new java.awt.Font("Courier New", 1, 18));
    txtInput.setHorizontalAlignment(javax.swing.JTextField.LEFT);
    txtInput.addKeyListener(new java.awt.event.KeyAdapter() {
      public void keyTyped(KeyEvent evt) {
        txtInputKeyTyped(evt);
      }
    });

    pnlBuffers.add(txtInput);

    pnlCentre.add(pnlBuffers);

    pnlSettings.setLayout(new java.awt.GridLayout(1, 3));

    pnlSettingsLeft.setLayout(new java.awt.BorderLayout());

    pnlSettingsLeft.setBorder(javax.swing.BorderFactory.createTitledBorder("Difficulty"));
    btnAbout.setText("About");
    btnAbout.setMaximumSize(new java.awt.Dimension(80, 20));
    btnAbout.setPreferredSize(new java.awt.Dimension(80, 23));
    btnAbout.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        btnAboutActionPerformed(evt);
      }
    });

    pnlSettingsLeft.add(btnAbout, java.awt.BorderLayout.SOUTH);

    pnlDifficulty.setLayout(new java.awt.GridLayout(2, 1));

    chkAdapt.setSelected(true);
    chkAdapt.setText("Auto Adjust");
    chkAdapt.setToolTipText("Automatically Adjusts Difficulty");
    chkAdapt.addItemListener(new java.awt.event.ItemListener() {
      public void itemStateChanged(java.awt.event.ItemEvent evt) {
        chkAdaptItemStateChanged(evt);
      }
    });

    pnlDifficulty.add(chkAdapt);

    chkSmallChanges.setText("Small Changes");
    chkSmallChanges.setToolTipText("Make only small changes to difficulty");
    chkSmallChanges.addItemListener(new java.awt.event.ItemListener() {
      public void itemStateChanged(java.awt.event.ItemEvent evt) {
        chkSmallChangesItemStateChanged(evt);
      }
    });

    pnlDifficulty.add(chkSmallChanges);

    pnlSettingsLeft.add(pnlDifficulty, java.awt.BorderLayout.CENTER);

    pnlSettings.add(pnlSettingsLeft);

    pnlErrorStyle.setLayout(new java.awt.GridLayout(3, 1));

    pnlErrorStyle.setBorder(javax.swing.BorderFactory.createTitledBorder("Mistakes"));
    pnlErrorStyle.setMaximumSize(new java.awt.Dimension(32767, 30));
    btngrpErrorStyle.add(radIgnore);
    radIgnore.setSelected(true);
    radIgnore.setText("Ignore");
    pnlErrorStyle.add(radIgnore);

    btngrpErrorStyle.add(radAccept);
    radAccept.setText("Accept");
    pnlErrorStyle.add(radAccept);

    btngrpErrorStyle.add(radBuffer);
    radBuffer.setText("Buffer (Realistic)");
    pnlErrorStyle.add(radBuffer);

    pnlSettings.add(pnlErrorStyle);

    pnlSettingsRight.setLayout(new java.awt.GridLayout(2, 1));

    pnlSettingsRight.setBorder(javax.swing.BorderFactory.createTitledBorder("Scoring"));
    jPanel4.setLayout(new java.awt.GridLayout(2, 1));

    lblKeysPerSec.setText("Best Speed:");
    jPanel4.add(lblKeysPerSec);

    sldrKeysPerSec.setMajorTickSpacing(2);
    sldrKeysPerSec.setMaximum(10);
    sldrKeysPerSec.setMinimum(1);
    sldrKeysPerSec.setMinorTickSpacing(1);
    sldrKeysPerSec.setToolTipText("Indicates 100% speed in keys per second");
    sldrKeysPerSec.setValue(7);
    sldrKeysPerSec.addChangeListener(new javax.swing.event.ChangeListener() {
      public void stateChanged(javax.swing.event.ChangeEvent evt) {
        sldrKeysPerSecStateChanged(evt);
      }
    });

    jPanel4.add(sldrKeysPerSec);
    sldrKeysPerSec.getAccessibleContext().setAccessibleDescription("Indicates 100% speed in keys per second");

    pnlSettingsRight.add(jPanel4);

    chkDetailedScore.setText("Detailed Score");
    chkDetailedScore.setToolTipText("Display Detailed Score");
    chkDetailedScore.addItemListener(new java.awt.event.ItemListener() {
      public void itemStateChanged(java.awt.event.ItemEvent evt) {
        chkDetailedScoreItemStateChanged(evt);
      }
    });

    pnlSettingsRight.add(chkDetailedScore);

    pnlSettings.add(pnlSettingsRight);

    pnlCentre.add(pnlSettings);

    getContentPane().add(pnlCentre, java.awt.BorderLayout.CENTER);

    pnlNorth.setLayout(new java.awt.BorderLayout());

    pnlNorth.setBorder(javax.swing.BorderFactory.createTitledBorder("Count Down"));
    sldrKeys.setPaintLabels(true);
    sldrKeys.setSnapToTicks(true);
    sldrKeys.addChangeListener(new javax.swing.event.ChangeListener() {
      public void stateChanged(javax.swing.event.ChangeEvent evt) {
        sldrKeysStateChanged(evt);
      }
    });

    pnlNorth.add(sldrKeys, java.awt.BorderLayout.CENTER);

    btnReset.setText("Reset");
    btnReset.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        btnResetActionPerformed(evt);
      }
    });

    pnlNorth.add(btnReset, java.awt.BorderLayout.EAST);

    btnCountDown.setText("0");
    btnCountDown.setToolTipText("Click to set Count Down");
    btnCountDown.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        btnCountDownActionPerformed(evt);
      }
    });

    pnlNorth.add(btnCountDown, java.awt.BorderLayout.WEST);

    getContentPane().add(pnlNorth, java.awt.BorderLayout.NORTH);

    pnlEast.setLayout(new java.awt.BorderLayout());

    pnlEast.setBorder(javax.swing.BorderFactory.createTitledBorder("Score "));
    progOverall.setBackground(javax.swing.UIManager.getDefaults().getColor("Button.light"));
    progOverall.setOrientation(1);
    progOverall.setToolTipText("Overall");
    progOverall.setMaximumSize(new java.awt.Dimension(25, 32767));
    progOverall.setMinimumSize(new java.awt.Dimension(25, 10));
    pnlEast.add(progOverall, java.awt.BorderLayout.WEST);

    pnlDetailedScore.setLayout(new java.awt.GridLayout(1, 3));

    jPanel2.setLayout(new java.awt.BorderLayout());

    progAccurracy.setOrientation(1);
    progAccurracy.setToolTipText("Accuracy");
    jPanel2.add(progAccurracy, java.awt.BorderLayout.CENTER);

    lblAccuracy.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
    lblAccuracy.setText("0");
    jPanel2.add(lblAccuracy, java.awt.BorderLayout.NORTH);

    jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
    jLabel2.setText("A");
    jPanel2.add(jLabel2, java.awt.BorderLayout.SOUTH);

    pnlDetailedScore.add(jPanel2);

    jPanel1.setLayout(new java.awt.BorderLayout());

    progSpeed.setOrientation(1);
    progSpeed.setToolTipText("Speed");
    jPanel1.add(progSpeed, java.awt.BorderLayout.CENTER);

    lblSpeed.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
    lblSpeed.setText("0");
    jPanel1.add(lblSpeed, java.awt.BorderLayout.NORTH);

    jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
    jLabel1.setText("S");
    jPanel1.add(jLabel1, java.awt.BorderLayout.SOUTH);

    pnlDetailedScore.add(jPanel1);

    jPanel3.setLayout(new java.awt.BorderLayout());

    progRating.setOrientation(1);
    progRating.setToolTipText("Difficulty");
    jPanel3.add(progRating, java.awt.BorderLayout.CENTER);

    lblRating.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
    lblRating.setText("0");
    jPanel3.add(lblRating, java.awt.BorderLayout.NORTH);

    jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
    jLabel3.setText("D");
    jPanel3.add(jLabel3, java.awt.BorderLayout.SOUTH);

    pnlDetailedScore.add(jPanel3);

    pnlEast.add(pnlDetailedScore, java.awt.BorderLayout.EAST);

    getContentPane().add(pnlEast, java.awt.BorderLayout.EAST);

    pnlWest.setLayout(new java.awt.BorderLayout());

    sldrRating.setOrientation(javax.swing.JSlider.VERTICAL);
    sldrRating.setSnapToTicks(true);
    sldrRating.setToolTipText("Difficulty");
    sldrRating.setValue(0);
    sldrRating.addChangeListener(new javax.swing.event.ChangeListener() {
      public void stateChanged(javax.swing.event.ChangeEvent evt) {
        sldrRatingStateChanged(evt);
      }
    });

    pnlWest.add(sldrRating, java.awt.BorderLayout.WEST);

    lblDesiredRating.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
    lblDesiredRating.setText("0");
    lblDesiredRating.setToolTipText("Difficulty");
    pnlWest.add(lblDesiredRating, java.awt.BorderLayout.NORTH);

    getContentPane().add(pnlWest, java.awt.BorderLayout.WEST);

  }// </editor-fold>//GEN-END:initComponents

  private void chkSmallChangesItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_chkSmallChangesItemStateChanged
    int quot = Config.DEFAULT_DIFFICULTY_GAIN_QUOTIENT;
    if (this.chkSmallChanges.isSelected()) {
      quot = Config.SLOW_DIFFICULTY_GAIN_QUOTIENT;
    }
    getUserPref().setDifficultyGainQuotient(quot);
  }//GEN-LAST:event_chkSmallChangesItemStateChanged

  private void sldrKeysPerSecStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_sldrKeysPerSecStateChanged
    // set desired rate
    if (this.isActive()) {
      getUserPref().setTargetKeyStrokeRate(this.sldrKeysPerSec.getValue());
    }
    this.lblKeysPerSec.setText(this.keysPerSecPrefix + this.sldrKeysPerSec.getValue() + this.keysPerSecSuffix);    
  }//GEN-LAST:event_sldrKeysPerSecStateChanged

  private void chkAdaptItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_chkAdaptItemStateChanged
    // enable / disable 'small change'
    this.chkSmallChanges.setEnabled(this.chkAdapt.isSelected());
  }//GEN-LAST:event_chkAdaptItemStateChanged

  private void chkDetailedScoreItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_chkDetailedScoreItemStateChanged
    syncDetailedDisplay();
  }//GEN-LAST:event_chkDetailedScoreItemStateChanged

  private boolean getDetailVisible() {
    return this.pnlDetailedScore.isVisible();
  }
  
  private void setDetailVisible(boolean visible) {
    this.pnlDetailedScore.setVisible(visible);
  }
  
  private void btnCountDownActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCountDownActionPerformed
    this.sldrKeys.setValue(this.sldrKeys.getMinimum());
    resetGame();

  }//GEN-LAST:event_btnCountDownActionPerformed
 
  private void btnAboutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAboutActionPerformed
    JOptionPane.showMessageDialog(this,  Config.ABOUT_MESSAGE);
  }//GEN-LAST:event_btnAboutActionPerformed

  private void sldrKeysStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_sldrKeysStateChanged
    // display count
    String s = String.valueOf(this.sldrKeys.getMaximum() - this.sldrKeys.getValue());
    //this.sldrKeys.setToolTipText(s);
    this.btnCountDown.setText(s);
  }//GEN-LAST:event_sldrKeysStateChanged

  /**
   * Resets the display and history etc.
   */
  private void resetGame() {
    this.promptBuffer.flush();
    this.promptHistoryBuffer.flush();
    this.inputFeedbackBuffer.flush();
    this.assessor.reset();
    this.txtInput.setText("");
    this.txtInput.requestFocus();
  }
  
  private void btnResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResetActionPerformed
    resetGame();
  }//GEN-LAST:event_btnResetActionPerformed
 
  private void sldrRatingStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_sldrRatingStateChanged
    if ((this.ratingWeight != null) && (this.ratingWeight.getP1X() != this.sldrRating.getValue())) {
      // can change and is necessary
      this.ratingWeight.setP1X(this.sldrRating.getValue());
      this.lblDesiredRating.setText(twoChars(getNormalisedRating()));
      Config.output("Changed difficulty to: " + this.ratingWeight.getP1X());
      Config.output("Changed normalised difficulty to: " + getNormalisedRating());
    }
  }//GEN-LAST:event_sldrRatingStateChanged

  private void processText(long time) {
    boolean cycle = false;
    int charsConsumed = 0;
    String text = this.txtInput.getText();
    
    if (text == null) {
      text = "";
    }    

    if (text.length() == 0) {
      return;
    }
    
    do {
      cycle = false;
      
      // determine key stroke
      char ch = 0;
      if (text.length() > 0) {
        // use the first character instead
        ch = text.charAt(0);
      }
      else {
        // length is 0
        // use the typed character
        // do nothing
      }
      SimpleKeyStroke ksEntered = new SimpleKeyStroke();
      ksEntered.setKeyChar(ch);

      RatedKeyStroke ksDesired = (RatedKeyStroke) this.promptBuffer.peek();

      if (isUseful(ch)) {
        // a processable char
        // continue processing
        if (keyStrokeMatch(ksDesired, ksEntered)) { 
          // key stroke matches
          cycle = true;
        }
        else {
          // don't match
          if (this.radBuffer.isSelected()) {
            // buffering input
            // place resulting text
            if (charsConsumed == 0) {
              // first cycle
              //leave input field untouched
            }
            else {
              // have processed some of the text
              int pos = this.txtInput.getCaretPosition();
              pos = pos - charsConsumed;
              pos = Math.max(pos, 0);
              this.txtInput.setText(text);
              this.txtInput.setCaretPosition(Math.min(pos, text.length()));
            }
          }
          else if (this.radIgnore.isSelected()) {
            // ignoring mistakes
            if (charsConsumed == 0) {
              // first cycle
              // leave text untouched
            }
            else {
              // have processed some of the text
              // select all text
              this.txtInput.setText(text);
            }
            this.txtInput.select(0, this.txtInput.getText().length());
          }
          else {
            // must be accepting mistakes
            cycle = true;
          }
        }
      }
      else {
        // not interested
        // do nothing
      }


      if (cycle) {
        // will cycle
        this.promptBuffer.consume();
        forceAdd(this.promptHistoryBuffer, ksDesired);
        forceAdd(this.inputFeedbackBuffer, ksEntered);
        UserResponse resp = new UserResponse();
        resp.setTime(time);
        resp.setDesiredKeyStroke(ksDesired);
        resp.setResultingKeyStroke(ksEntered);
        this.assessor.assess(resp);

        
        text = text.substring(1);
        
        if (cycleCounter() || (text.length() == 0)) {
          // counter has run down or text has run out
          // no more input
          this.txtInput.setText(text);
          cycle = false;
        }
      }
      
      charsConsumed++;
    } while (cycle && (text.length() > 0));
  }
  
  
  private void txtInputKeyTyped(KeyEvent evt) {//GEN-FIRST:event_txtInputKeyTyped
    EventQueue.invokeLater(new ProcessTextCommand(evt.getWhen()));
  }//GEN-LAST:event_txtInputKeyTyped

  /**
   * Processes a tick for the cycle counter.  Return whether or not the counter has finished
   */
  private boolean cycleCounter() {
    boolean result = false;
    if (this.sldrKeys.getValue() < this.sldrKeys.getMaximum()) {
      // still counting
      // inc counter
      this.sldrKeys.setValue(this.sldrKeys.getValue() + 1);
      
      if (this.sldrKeys.getValue() >= this.sldrKeys.getMaximum()) {
        // end of counter cycle
        this.sldrKeys.requestFocus();
        this.inputFeedbackBuffer.flush();
        this.promptHistoryBuffer.flush();
        Toolkit.getDefaultToolkit().beep();
        Toolkit.getDefaultToolkit().beep();
        result = true;
      }
    }
    return result;
  }
  
  private boolean isUseful(char ch) {
    return (ch != KeyEvent.VK_BACK_SPACE)
        && (ch != KeyEvent.VK_DELETE)
        && (ch != KeyEvent.VK_ENTER);
  }
  
  /**
   * Add the given keystroke to the given buffer, consume a keystroke if necessary
   */
  private void forceAdd(KeyStrokeBuffer buffer, KeyStroke ks) {
    if (buffer.isFull()) {
      buffer.consume();
    }
    buffer.add(ks);
  }
  
  /**
   * Compare the keystrokes to see if they match
   */
  private boolean keyStrokeMatch(KeyStroke ks1, KeyStroke ks2) {
    return ks1.getKeyChar() == ks2.getKeyChar();
  }
  
  public void stop() {
    this.producer = this.producer.shutdown();
    this.assessor = this.assessor.shutdown();
    this.inputFeedbackBuffer = this.inputFeedbackBuffer.shutdown();
    this.promptBuffer = this.promptBuffer.shutdown();
    this.promptHistoryBuffer = this.promptHistoryBuffer.shutdown();
    System.gc();

  }  
  
  /**
   * Synchronize display with data
   */
  private void syncDisplay() {
    this.sldrRating.setValue(this.ratingWeight.getP1X());
    this.lblDesiredRating.setText(twoChars(getNormalisedRating()));
  }

  /**
   * Synchronise the detailed score visibility
   */
  private void syncDetailedDisplay() {
    setDetailVisible(this.chkDetailedScore.isSelected());
    syncScoreTitle();
  }
  
  /**
   * Return the prefix to use in front of the score
   */
  private String getScorePrefix() {
    String result = "";
    if (getDetailVisible()) {
      result = this.scorePrefix;
    }
    return result;
  }
  
  public void start(String keyboardLayout) {

    if (this.promptBuffer == null) {
      // not yet started
      // set up prompt buffers
      this.promptBuffer = new KeyStrokeBuffer(Config.DISPLAY_BUFFER_SIZE);
      this.promptHistoryBuffer = new KeyStrokeBuffer(Config.DISPLAY_BUFFER_SIZE);
      this.inputFeedbackBuffer = new KeyStrokeBuffer(Config.DISPLAY_BUFFER_SIZE);

      //config = Util.CONFIG_LETTERS_ONLY;
      RatedKeyStroke[] keys = Util.getKeys(keyboardLayout);
      
      setMaxRating(Config.getMaxRating(keys));
      setMinRating(Config.getMinRating(keys));
      this.producer = createProducer(this.promptBuffer, keys);
      this.assessor = new RunningAssessor(Config.calcAverageRating(keys), getMinRating(), new UserPref());
      this.assessor.addListener(this);
 
      // TODO: Sort out weak reference stuff..this may be enough
      new BufferDisplay(this.inputFeedbackBuffer, this.lblInputHist);
      new BufferDisplay(this.promptBuffer, this.lblPrompt);
      new BufferDisplay(this.promptHistoryBuffer, this.lblPromptHist);
      
      this.progAccurracy.setMaximum(Config.PERFECT);
      this.progOverall.setMaximum(Config.PERFECT);
      this.progRating.setMaximum(Config.PERFECT);
      this.progSpeed.setMaximum(Config.PERFECT);
      
      syncDisplay();
    }
//    else {
      // already started
      // just reset
      //let them do it manually...this.assessor.reset();
//    }
  }  
  
  /**
   * Initialise and return the keystroke producer for this app
   */
  private KeyStrokeProducer createProducer(KeyStrokeBuffer buffer, RatedKeyStroke[] keys) {
    ratingWeight = Config.createRatingWeight(keys);
    Curve recentWeight = Config.createRecentUseWeight(keys);
    KeyStrokeSource kss = new RandomSource(keys, ratingWeight, recentWeight);
    
    return new KeyStrokeProducer(buffer, kss); //new TestKeyStrokeSource());
  }

  /**
   * The maximum difficulty rating (any more will not have any effect due to the step curve.
   */
  private int getMaxRating() {
    return this.sldrRating.getMaximum();
  }
  private void setMaxRating(int rating) {
    this.sldrRating.setMaximum(rating);
  }
  
  private int getMinRating() {
    return this.sldrRating.getMinimum();
  }
  private void setMinRating(int rating) {
    this.sldrRating.setMinimum(rating);
  }
  
  /**
   * The current difficulty rating
   */
  private void setRating(int rating) {
    this.sldrRating.setValue(rating);
  }
  
  private int getRating() {
    return this.sldrRating.getValue();
  }

  /**
   * Accessors for a rating between 0 and 'PERFECT'
   */
  private int getNormalisedRating() {
    int zeroBased = getRating() - getMinRating();
    int range = getMaxRating() - getMinRating();
    return Math.round((zeroBased * Config.PERFECT * 1.0f) / range);
  }
  private void setNormalisedRating(int rating) {
    int range = getMaxRating() - getMinRating();
    int zeroBased = Math.round((rating * range * 1.0f) / Config.PERFECT);
    setRating(zeroBased + getMinRating());
  }

  /**
   * Return the last 2 characters of the given number
   */
  private String twoChars(int value) {
    String s = String.valueOf(value);
    return s.substring(Math.max(0, s.length() - 2));
  }
  
  /** 
   * Adjust the difficulty level if necessary
   */
  private void autoAdjust(Assessment assessment) {
    if (this.chkAdapt.isSelected() && !assessment.isBlank()) {
      Config.output("Assessment at " + assessment.getKeyCount() + " keystrokes");
      // auto adjusting and not a 'reset' command
      int desired = (int) Math.sqrt(Config.STEADY_ACCURACY * Config.STEADY_SPEED);
      int current = (int) Math.sqrt(assessment.getAccuracy() * assessment.getSpeed());
      Config.output("Current speed - accuracy: " + current);
      int delta = current - desired; // want rating to go down if current is < desired and VV.
      Config.output("Raw Delta: " + delta);
      delta = Math.round(((float)delta) / getUserPref().getDifficultyGainQuotient());
      Config.output("Partial Delta: " + delta);
      int newRating = getNormalisedRating() + delta;
      
      Config.output("Old rating: " + getNormalisedRating());
      
      // check limits
      newRating = Math.min(Config.PERFECT, newRating);
      newRating = Math.max(newRating, 0);
      
      Config.output("New Rating: " + newRating);
      
      // adapt
      setNormalisedRating(newRating);
      
      Config.output("Resulting Rating: " + getNormalisedRating());
      Config.output("");
    }
  }
  
  private void syncScoreTitle() {
    ((TitledBorder) this.pnlEast.getBorder()).setTitle(getScorePrefix() + twoChars(this.progOverall.getValue()));
  }
  
  /**
   * Returns the user pref object for this applet.
   */
  private UserPref getUserPref() {
      return this.assessor.userPref;
  }
  
  /**
   * Notification of another assessment
   */
  public void update(Assessment assessment) {
    this.lblAccuracy.setText(twoChars(assessment.getAccuracy()));
    this.progAccurracy.setValue(assessment.getAccuracy());
    
    this.lblRating.setText(twoChars(assessment.getRating()));
    this.progRating.setValue(assessment.getRating());
    
    this.progOverall.setValue(assessment.getOverall());
    syncScoreTitle();
    
    this.lblSpeed.setText(twoChars(assessment.getSpeed()));
    this.progSpeed.setValue(assessment.getSpeed());
     
    this.pnlEast.repaint();
    
    autoAdjust(assessment);    
  }
  
  // Variables declaration - do not modify//GEN-BEGIN:variables
  private javax.swing.JButton btnAbout;
  private javax.swing.JButton btnCountDown;
  private javax.swing.JButton btnReset;
  private javax.swing.ButtonGroup btngrpErrorStyle;
  private javax.swing.JCheckBox chkAdapt;
  private javax.swing.JCheckBox chkDetailedScore;
  private javax.swing.JCheckBox chkSmallChanges;
  private javax.swing.JLabel jLabel1;
  private javax.swing.JLabel jLabel2;
  private javax.swing.JLabel jLabel3;
  private javax.swing.JLabel jLabel4;
  private javax.swing.JPanel jPanel1;
  private javax.swing.JPanel jPanel2;
  private javax.swing.JPanel jPanel3;
  private javax.swing.JPanel jPanel4;
  private javax.swing.JLabel lblAccuracy;
  private javax.swing.JLabel lblDesiredRating;
  private javax.swing.JLabel lblInputHist;
  private javax.swing.JLabel lblKeysPerSec;
  private javax.swing.JLabel lblPrompt;
  private javax.swing.JLabel lblPromptHist;
  private javax.swing.JLabel lblRating;
  private javax.swing.JLabel lblSpeed;
  private javax.swing.JPanel pnlBuffers;
  private javax.swing.JPanel pnlCentre;
  private javax.swing.JPanel pnlDetailedScore;
  private javax.swing.JPanel pnlDifficulty;
  private javax.swing.JPanel pnlEast;
  private javax.swing.JPanel pnlErrorStyle;
  private javax.swing.JPanel pnlNorth;
  private javax.swing.JPanel pnlSettings;
  private javax.swing.JPanel pnlSettingsLeft;
  private javax.swing.JPanel pnlSettingsRight;
  private javax.swing.JPanel pnlWest;
  private javax.swing.JProgressBar progAccurracy;
  private javax.swing.JProgressBar progOverall;
  private javax.swing.JProgressBar progRating;
  private javax.swing.JProgressBar progSpeed;
  private javax.swing.JRadioButton radAccept;
  private javax.swing.JRadioButton radBuffer;
  private javax.swing.JRadioButton radIgnore;
  private javax.swing.JSlider sldrKeys;
  private javax.swing.JSlider sldrKeysPerSec;
  private javax.swing.JSlider sldrRating;
  private javax.swing.JTextField txtInput;
  // End of variables declaration//GEN-END:variables
  
  /**
   * Invokes the 'processText' method
   */
  private class ProcessTextCommand implements Runnable {
    long time;
    ProcessTextCommand(long time) {
      this.time = time;
    }
    
    public void run() {
      processText(this.time);
    }
  }
}
