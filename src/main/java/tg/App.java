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
            String config = Util.CONFIG_QWERTY;
            if (args.length >= 1) config = args[0];
            MainFrame frame = new MainFrame();
            frame.init();
            frame.setTitle("Typing Game '" + config + "' (by freshcode.biz)");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.pack();
            frame.setVisible(true);
            frame.start(config);
        });
    }
}
