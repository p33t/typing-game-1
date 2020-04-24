package tg;

import tg.ui.MainFrame;
import tg.util.Util;

import javax.swing.*;

/**
 * Launches the application
 */
public class App {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainFrame frame = new MainFrame();
            frame.init();
            frame.setTitle("Typing Game (by freshcode.biz)");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.pack();
            frame.setVisible(true);
            frame.start(Util.CONFIG_QWERTY);
        });
    }
}
