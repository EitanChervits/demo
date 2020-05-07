package gui;

import engine.Board;
import engine.Color;
import engine.Game;
import engine.Position;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class GameWindow extends JFrame implements MouseListener,
        ActionListener {
private JFrame frame;
private Image img;
private ImagePanel background;
private CheckerButton[][] checkerButtons;
private JPanel top;
private JPanel menu;
private JPanel right;
private Board gameBoard;
public static final int WIDTH = 77;
public static final int HEIGHT = 59;
    //אומר שהאקספטיון משחרר אותו והתוכנית לא תסיים לורץ אבל אם שים את הץומנה כמו שצריך התוכנית לא תעשה את האקספטיון
public GameWindow() throws IOException {
    gameBoard = new Board();
    frame = new JFrame("Lines Of Action");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setLayout(new BorderLayout());
    frame.setResizable(false);

    checkerButtons = new CheckerButton[8][8];

    img = ImageIO.read(new File("resources/chessboard.jpg"));//added exception
    background = new ImagePanel(img);

    frame.setContentPane(background);

    top = new JPanel();
    top.setOpaque(false);
    top.setPreferredSize(new Dimension(1200, 150));

    menu = new JPanel();
    menu.setOpaque(false);
    menu.setPreferredSize(new Dimension(320, 1200));

    right = new JPanel();
    right.setOpaque(false);
    right.setPreferredSize(new Dimension(661,512));
    updateBoard();
    setImageIcons();

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
    public void setImageIcons(){
        for (int i = 0; i <8 ; i++) {
            for (int j = 0; j <8 ; j++) {
                if(checkerButtons[i][j].getCheckerColor()==Color.WHITE)
                    checkerButtons[i][j].setIcon(getResizedIcon("WhitePlayer.png",40,40));
                else checkerButtons[i][j].setIcon(getResizedIcon("BlackPlayer.png",40,40));
            }
        }

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
                checkerButtons[i][j] = new CheckerButton(gameBoard.getColorOfCheckerAt(i,j),x,y,i,j);
                x+=WIDTH;
                right.add(checkerButtons[i][j]);
            }
             y+=HEIGHT;
             x=0;
        }
    }
    public CheckerButton findByPosition(Position position){
    for (CheckerButton[] array:checkerButtons){
        for(CheckerButton c:array){
            if(c.getPosition()==position)
                return c;
        }
    }
    return null;
    }

    public void actionPreformed (Action action){

    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        Object temp = e.getSource();
        if(temp.getClass()==CheckerButton.class){
          if(!((CheckerButton) temp).isHighlighted()) {
              if (((CheckerButton) temp).getCheckerColor() == gameBoard.getTurn()) {
                  ArrayList<Position> positions = gameBoard.canGo(gameBoard.getCheckerAt(((CheckerButton) temp).getPosition()));
                  while (!positions.isEmpty()) {
                      int index = 0;
                      CheckerButton checkerButton = findByPosition(positions.get(index));
                      checkerButton.Highlighted();
                      checkerButton.setHighlighted(true);
                  }

              }
          }

        }

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
    public static void main(String[]args) throws IOException{
      GameWindow gameWindow = new GameWindow();
    }
}
