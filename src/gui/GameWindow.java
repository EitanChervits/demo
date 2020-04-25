package gui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class GameWindow {

    public static void main(String[] args) throws IOException {
        Image img = ImageIO.read(new File("resources/chessboard.jpg"));
        ImagePanel background = new ImagePanel(img);

        JFrame frame = new JFrame("Lines Of Action");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(background);
        frame.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        frame.add(new JLabel("Hello world"), gbc);
        frame.add(new JLabel("I'm on top"), gbc);
        frame.add(new JButton("Clickity-clackity"), gbc);
        frame.setSize(1200,1200);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
