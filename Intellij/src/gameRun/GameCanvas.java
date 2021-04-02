package gameRun;

import javax.swing.*;
import java.awt.*;

public class GameCanvas extends JPanel {

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

    public void paintComponent(Graphics g) {

        super.paintComponent(g);

        Image image = new ImageIcon("res/Logo.png").getImage();
        g.drawImage(image, 0, 0, this);
        image = new ImageIcon("C:/Users/samue/OneDrive/School/VAVA/Zadanie semestralne/Intellij/res/alpha.png").getImage();
        g.drawImage(image, 100, 100, this);
        image = new ImageIcon("C:/Users/samue/OneDrive/School/VAVA/Zadanie semestralne/Intellij/res/alpha.png").getImage();
        g.drawImage(image, 200, 100, this);
        image = new ImageIcon("C:/Users/samue/OneDrive/School/VAVA/Zadanie semestralne/Intellij/res/alpha.png").getImage();
        g.drawImage(image, 100, 200, this);
        image = new ImageIcon("C:/Users/samue/OneDrive/School/VAVA/Zadanie semestralne/Intellij/res/alpha.png").getImage();
        g.drawImage(image, 200, 200, this);
        image = new ImageIcon("C:/Users/samue/OneDrive/School/VAVA/Zadanie semestralne/Intellij/res/alpha.png").getImage();
        g.drawImage(image, 300, 100, this);
        image = new ImageIcon("C:/Users/samue/OneDrive/School/VAVA/Zadanie semestralne/Intellij/res/alpha.png").getImage();
        g.drawImage(image, 100, 300, this);
        image = new ImageIcon("C:/Users/samue/OneDrive/School/VAVA/Zadanie semestralne/Intellij/res/alpha.png").getImage();
        g.drawImage(image, 300, 300, this);
        image = new ImageIcon("C:/Users/samue/OneDrive/School/VAVA/Zadanie semestralne/Intellij/res/alpha.png").getImage();
        g.drawImage(image, 400, 100, this);
        image = new ImageIcon("C:/Users/samue/OneDrive/School/VAVA/Zadanie semestralne/Intellij/res/alpha.png").getImage();
        g.drawImage(image, 100, 400, this);
        image = new ImageIcon("C:/Users/samue/OneDrive/School/VAVA/Zadanie semestralne/Intellij/res/alpha.png").getImage();
        g.drawImage(image, 400, 400, this);
    }

}
