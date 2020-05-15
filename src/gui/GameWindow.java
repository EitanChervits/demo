package gui;

import engine.*;
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
import java.util.ArrayList;

public class GameWindow extends JFrame implements MouseListener,
        ActionListener {
    private CheckerButton[][] checkerButtons;
    private JPanel right;
    private Board gameBoard;
    private JButton play;

    private ImageIcon whitePieceImg;
    private ImageIcon blackPieceImg;

    private Checker clickedChecker;

    //אומר שהאקספטיון משחרר אותו והתוכנית לא תסיים לורץ אבל אם שים את הץומנה כמו שצריך התוכנית לא תעשה את האקספטיון
    public GameWindow() throws IOException {
        int wid = 1066;
        int hei = 854;
        int topHei = 120;
        int menuWid = 325;

        whitePieceImg = getResizedIcon("WhitePlayer.png",65,65);
        blackPieceImg = getResizedIcon("BlackPlayer.png",65,65);


        gameBoard = new Board();
        JFrame frame = new JFrame("Lines Of Action");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.setResizable(false);

        checkerButtons = new CheckerButton[8][8];

        Image img = ImageIO.read(new File("resources/chessboard.jpg"));//added exception
        ImagePanel background = new ImagePanel(img);

        frame.setContentPane(background);

        JPanel top = new JPanel();
        top.setOpaque(false);
        top.setPreferredSize(new Dimension(wid, topHei));

        JPanel menu = new JPanel();
        menu.setOpaque(false);
        menu.setPreferredSize(new Dimension(260, hei - topHei - 35));

        JPanel menu2 = new JPanel();
        menu2.setOpaque(false);
        menu2.setPreferredSize(new Dimension(menuWid, hei - topHei - 35));
        JPanel menuRight = new JPanel();
        menuRight.setOpaque(false);
        menuRight.setPreferredSize(new Dimension(0, hei - topHei - 35));
        menu2.setLayout(new BorderLayout());
        menu2.add(menu, BorderLayout.LINE_START);
        menu2.add(menuRight, BorderLayout.LINE_END);

        JPanel right2 = new JPanel();
        right2.setOpaque(false);
        right2.setLayout(new BorderLayout());

        JPanel rightBtm = new JPanel();
        rightBtm.setOpaque(false);
        rightBtm.setPreferredSize(new Dimension(626, 135));
        right2.add(rightBtm, BorderLayout.PAGE_END);

        right = new JPanel();
        right.setOpaque(false);
        right.setPreferredSize(new Dimension(wid - menuWid - 110,600));
        right.setLayout(new GridLayout(8,8));
        updateBoard();
        setImageIcons();

        right2.add(right, BorderLayout.PAGE_START);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        play = getButton("PlayBTNpressed.png", "PlayBTNnotpressed.png", 202, 91);
        play.addActionListener(this);
        menu.add(play, gbc);
        menu.add(getButton("RestartBTNpressed.png", "RestartBTNnotpressed.png", 202, 91), gbc);
        menu.add(getButton("PauseBTNpressed.png", "PauseBTNnotpressed.png", 202, 91), gbc);
        menu.add(getButton("MainMenuBTNpressed.png", "MainMenuBTNnotpressed.png", 202, 91), gbc);

        frame.add(top, BorderLayout.PAGE_START);
        frame.add(menu2, BorderLayout.LINE_START);
        frame.add(right2, BorderLayout.CENTER);

        JPanel rightEmpty = new JPanel();
        rightEmpty.setOpaque(false);
        rightEmpty.setPreferredSize(new Dimension(60,hei - topHei));
        frame.add(rightEmpty, BorderLayout.LINE_END);

        frame.setPreferredSize(new Dimension(wid, hei));
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
                if(gameBoard.getColorOfCheckerAt(i, j)==Color.WHITE)
                    checkerButtons[i][j].setIcon(whitePieceImg);
                else if(gameBoard.getColorOfCheckerAt(i, j)==Color.BLACK)
                    checkerButtons[i][j].setIcon(blackPieceImg);
                else
                    checkerButtons[i][j].setIcon(null);
            }
        }
    }

    protected static ImageIcon getResizedIcon(String filename, int width, int height) {
        Image toResize = new ImageIcon("resources/" + filename).getImage();
        Image resized = toResize.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(resized);
    }

    public void updateBoard(){
         for (int i = 0; i <8 ; i++) {
             for (int j = 0; j <8 ; j++) {
                checkerButtons[i][j] = new CheckerButton(gameBoard.getColorOfCheckerAt(i,j),i,j);
                right.add(checkerButtons[i][j]);
            }
        }
    }

    public CheckerButton findByPosition(Position position){
        return checkerButtons[position.getX()][position.getY()];
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == play) {
            makeBoardActive();
        }
    }

    public void makeBoardActive(){
        for (int i = 0; i <8 ; i++) {
            for (int j = 0; j <8 ; j++) {
                checkerButtons[i][j].addMouseListener(this);
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        Object temp = e.getSource();
        if(temp instanceof CheckerButton) {
            CheckerButton clicked = (CheckerButton) temp;
            Position p = clicked.getPosition();
            if (gameBoard.getTurn() == gameBoard.getColorOfCheckerAt(p)) {
                System.out.println("Clicked on [" + p.getX() + "," + p.getY() +"]");
                Checker c = gameBoard.getCheckerAt(p);
                if (clickedChecker == null) {
                    clickedChecker = c;
                } else if (clickedChecker == c) {
                    clickedChecker = null;
                }
            } else if (clickedChecker != null) {
                gameBoard.makeAMove(clickedChecker, p);
                setImageIcons();
                clickedChecker = null;
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
        Object temp = e.getSource();
        if(temp instanceof CheckerButton) {
            CheckerButton hovered = (CheckerButton) temp;
            Position p = hovered.getPosition();
            if (gameBoard.getTurn() == gameBoard.getColorOfCheckerAt(p)) {
                System.out.println("Hovered on [" + p.getX() + "," + p.getY() +"]");
                Checker c = gameBoard.getCheckerAt(p);
                ArrayList<Position> positions = gameBoard.canGo(c);
                for (Position pos : positions) {
                    CheckerButton cb = findByPosition(pos);
                    cb.setIcon(CheckerButton.imageIcon);
                }
            }
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        Object temp = e.getSource();
        if(temp instanceof CheckerButton) {
            CheckerButton hovered = (CheckerButton) temp;
            Position p = hovered.getPosition();
            if (gameBoard.getTurn() == gameBoard.getColorOfCheckerAt(p)) {
                System.out.println("Hovered on [" + p.getX() + "," + p.getY() +"]");
                Checker c = gameBoard.getCheckerAt(p);
                ArrayList<Position> positions = gameBoard.canGo(c);
                for (Position pos : positions) {
                    CheckerButton cb = findByPosition(pos);
                    cb.setIcon(getImgOfPieceAt(pos));
                }
            }
        }
    }

    private ImageIcon getImgOfPieceAt(Position p) {
        Color color = gameBoard.getColorOfCheckerAt(p);
        return color == Color.WHITE ? whitePieceImg :
                color == Color.BLACK ? blackPieceImg :
                null;
    }

    public static void main(String[]args) throws IOException{
      GameWindow gameWindow = new GameWindow();
    }
}
