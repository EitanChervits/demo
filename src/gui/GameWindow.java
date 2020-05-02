package gui;

import engine.Checker;
import engine.Color;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;

public class GameWindow extends JFrame implements MouseListener,
        ActionListener {
private JFrame frame;
private Image img;
private ImagePanel background;
private CheckerButton[][] checkerButtons;
private JPanel top;
private JPanel menu;
private JPanel left;

public GameWindow() throws IOException {
    frame = new JFrame("Lines Of Action");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setContentPane(background);
    frame.setLayout(new BorderLayout());
    frame.setResizable(false);

    checkerButtons = new CheckerButton[8][8];
    iniCheckerButtons();

    img = ImageIO.read(new File("resources/chessboard.jpg"));//added exception
    background = new ImagePanel(img);

    top = new JPanel();
    top.setOpaque(false);
    top.setPreferredSize(new Dimension(1200, 150));

    menu = new JPanel();
    menu.setOpaque(false);
    menu.setPreferredSize(new Dimension(320, 1200));

    left = new JPanel();
    left.setOpaque(false);
    left.setPreferredSize(new Dimension(661,512));

    GridBagConstraints gbc = new GridBagConstraints();
    gbc.gridwidth = GridBagConstraints.REMAINDER;
    menu.add(getButton("PlayBTNpressed.png", "PlayBTNnotpressed.png", 202, 91), gbc);
    menu.add(getButton("RestartBTNpressed.png", "RestartBTNnotpressed.png", 202, 91), gbc);
    menu.add(getButton("PauseBTNpressed.png", "PauseBTNnotpressed.png", 202, 91), gbc);
    menu.add(getButton("MainMenuBTNpressed.png", "MainMenuBTNnotpressed.png", 202, 91), gbc);

    // left.add();

    frame.add(top, BorderLayout.PAGE_START);
    frame.add(menu, BorderLayout.LINE_START);
    frame.add(left, BorderLayout.EAST);

    frame.setSize(1066,854);
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
    public void iniCheckerButtons(){
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if ((i == 0 || i == 7) && (j != 0 && j != 7))
                    checkerButtons[i][j] = new CheckerButton(engine.Color.BLACK,i,j);//sets all the black cells
                else if ((i != 0 && i != 7) && (j == 0 || j == 7))
                    checkerButtons[i][j] = new CheckerButton(Color.WHITE,i,j);
                else checkerButtons[i][j] = new CheckerButton(null,i,j);
            }
        }
    }
    public void actionPreformed (Action action){

    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
