package gui;

import engine.Board;
import engine.Color;
import engine.Game;

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
private JPanel right;
private Game Gameboard;
public static final int WIDTH = 77;
public static final int HEIGHT = 59;

public GameWindow(Board board)  {
    frame = new JFrame("Lines Of Action");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setContentPane(background);
    frame.setLayout(new BorderLayout());
    frame.setResizable(false);

    checkerButtons = new CheckerButton[8][8];

    img = ImageIO.read(new File("resources/chessboard.jpg"));//added exception
    background = new ImagePanel(img);

    top = new JPanel();
    top.setOpaque(false);
    top.setPreferredSize(new Dimension(1200, 150));

    menu = new JPanel();
    menu.setOpaque(false);
    menu.setPreferredSize(new Dimension(320, 1200));

    right = new JPanel();
    right.setOpaque(false);
    right.setPreferredSize(new Dimension(661,512));
   // right.add(checkerButtons);

    GridBagConstraints gbc = new GridBagConstraints();
    gbc.gridwidth = GridBagConstraints.REMAINDER;
    menu.add(getButton("PlayBTNpressed.png", "PlayBTNnotpressed.png", 202, 91), gbc);
    menu.add(getButton("RestartBTNpressed.png", "RestartBTNnotpressed.png", 202, 91), gbc);
    menu.add(getButton("PauseBTNpressed.png", "PauseBTNnotpressed.png", 202, 91), gbc);
    menu.add(getButton("MainMenuBTNpressed.png", "MainMenuBTNnotpressed.png", 202, 91), gbc);

    frame.add(top, BorderLayout.PAGE_START);
    frame.add(menu, BorderLayout.LINE_START);
    frame.add(right, BorderLayout.CENTER);

    frame.setSize(1066,854);
    frame.pack();
    frame.setLocationRelativeTo(null);
    frame.setVisible(true);
    Gameboard = new Game(board,Color.WHITE);

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
    public void updateBoard(){
        int x=0,y=0;
         for (int i = 0; i <8 ; i++) {
             for (int j = 0; j <8 ; j++) {
                checkerButtons[i][j] = new CheckerButton(Gameboard.getCheckers()[i][j].getColor(),x,y,i,j);
                x+=WIDTH;
                right.add(checkerButtons[i][j]);
            }
             y+=HEIGHT;
        }
    }

    public void actionPreformed (Action action){

    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        Object temp = e.getSource();

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
    public static void main(String[]args){
    Board board = new Board();
    GameWindow gameWindow = new GameWindow(board);
    }
}
