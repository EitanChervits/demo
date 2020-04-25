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
        frame.add(getButton("PlayBTNpressed.png", "PlayBTNnotpressed.png", 202, 91), gbc);
        frame.add(getButton("RestartBTNpressed.png", "RestartBTNnotpressed.png", 202, 91), gbc);
        frame.add(getButton("PauseBTNpressed.png", "PauseBTNnotpressed.png", 202, 91), gbc);
        frame.add(getButton("MainMenuBTNpressed.png", "MainMenuBTNnotpressed.png", 202, 91), gbc);
        frame.setSize(1200,1200);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private static JButton getButton(String pressedFile, String notPressedFile, int width, int height) {
        JButton btn = new JButton();
        ImageIcon pressedIcon = getResizedIcon(pressedFile, width, height);
        ImageIcon playNotPressed = getResizedIcon(notPressedFile, width, height);
        btn.setIcon(playNotPressed);
        btn.setRolloverIcon(pressedIcon);
        btn.setPressedIcon(pressedIcon);
        btn.setPreferredSize(new Dimension(width, height));
        btn.setOpaque(false);
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        return btn;
    }

    private static ImageIcon getResizedIcon(String filename, int width, int height) {
        Image toResize = new ImageIcon("resources/" + filename).getImage();
        Image resized = toResize.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(resized);
    }
}
