package gameRun;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyListener;

public class GameCanvas extends Canvas {

    private final JFrame frame;

    public GameCanvas() {

        setMinimumSize(new Dimension(GameController.Width(), GameController.Height()));
        setMaximumSize(new Dimension(GameController.Width(), GameController.Height()));
        setPreferredSize(new Dimension(GameController.Width(), GameController.Height()));

        frame = new JFrame(GameController.WindowName());

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.add(this, BorderLayout.CENTER);
        frame.pack();

        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public void closeFrame() {

        frame.dispose();
    }

}
