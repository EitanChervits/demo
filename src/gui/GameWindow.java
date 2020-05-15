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
    private JButton play;
    private JButton restart;
    private Board gameBoard;
    private static Position clickedPosition;

    private static ImageIcon whitePlayerImg;
    private static ImageIcon blackPlayerImg;

    //to dedicate the first checker clicked
    private static boolean firstHit = true;

    //אומר שהאקספטיון משחרר אותו והתוכנית לא תסיים לורץ אבל אם שים את הץומנה כמו שצריך התוכנית לא תעשה את האקספטיון
    public GameWindow() throws IOException {
        int wid = 1066;
        int hei = 854;
        int topHei = 120;
        int menuWid = 325;

        whitePlayerImg = getResizedIcon("WhitePlayer.png",65,65);
        blackPlayerImg = getResizedIcon("BlackPlayer.png",65,65);


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
        restart = getButton("RestartBTNpressed.png", "RestartBTNnotpressed.png", 202, 91);
        restart.addActionListener(this);
        menu.add(restart, gbc);
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
                if(gameBoard.getColorOfCheckerAt(i, j) == Color.WHITE)
                    checkerButtons[i][j].setIcon(whitePlayerImg);
                else if(gameBoard.getColorOfCheckerAt(i, j) == Color.BLACK)
                    checkerButtons[i][j].setIcon(blackPlayerImg);
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
                //checkerButtons[i][j].addMouseListener(this);
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
            System.out.println("play");
            for (int i = 0; i < 8 ; i++) {
                for (int j = 0; j < 8 ; j++) {
                    checkerButtons[i][j].addMouseListener(this);
                }
            }
        } else if (e.getSource() == restart) {
            System.out.println("restart");
            gameBoard = new Board();
            updateBoard();
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        Object temp = e.getSource();
        if(temp instanceof CheckerButton){
            CheckerButton cb = (CheckerButton) temp;
            Position p = cb.getPosition();
            if (gameBoard.getTurn() == cb.getCheckerColor()) {
                System.out.println("Clicked on [" + p.getX() + "," + p.getY() + "]");
                Checker checker = gameBoard.getCheckerAt(p);
                clickedPosition = firstHit ? p : null;
                CheckerButton.switchOneButtonClicked();
                firstHit = !firstHit;
                return;
            } else {
                if (!firstHit) {
                    System.out.println("Clicked on [" + p.getX() + "," + p.getY() + "]");
                    gameBoard.makeAMove(gameBoard.getCheckerAt(clickedPosition), cb.getPosition());
/*                    CheckerButton cbNew = findByPosition(p);
                    CheckerButton cbOld = findByPosition(clickedPosition);
                    cbOld.setIcon(null);
                    cbNew.setIcon()*/

                    setImageIcons();
                    clickedPosition = null;
                    firstHit = !firstHit;
                }
            }




/*                if (!((CheckerButton) temp).isHighlighted()) {
                if (((CheckerButton) temp).getCheckerColor() == gameBoard.getTurn()) {
                    ArrayList<Position> positions = gameBoard.canGo(gameBoard.getCheckerAt(((CheckerButton) temp).getPosition()));
                    while (!positions.isEmpty()) {
                        int index = 0;
                        CheckerButton checkerButton = findByPosition(positions.get(index));
                        checkerButton.Highlighted();
                        checkerButton.setHighlighted(true);
                    }

                }
            }*/

        }

    }

/*    public void mouseClicked(MouseEvent arg0) {
        Object temp = arg0.getSource();
        Position start = ((CheckerButton) temp).getPosition();
        Checker checker = gameBoard.getCheckerAt(start);
        if (gameBoard.getTurn() == checker.getColor()) {
            if (firstHit) {
                ArrayList<Position> possibleMoves = gameBoard.canGo(checker);
                clickedPosition = start;
                CheckerButton.switchOneButtonClicked();
                perform(possibleMoves, start);
                firstHit = false;
                return;
            } else if (clickedPosition.compareTo(start) == 0) {
                deSelect(start);
                return;
            }
        } else {

            if (gameBoard.move(new Point(clickedPosition.getY(), clickedPosition.getX()),
                    new Point(start.getY(), start.getX()))) {
                back();
                tem = new CheckerButton[8][8];
                updateBoard();
                if (game.isGameOver()) {
                    if (game.getWinner() == 1) {
                        JOptionPane.showMessageDialog(null, game
                                        .getWhitePlayer().getName() + " Wins !!",
                                "Congratulations", JOptionPane.PLAIN_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(null, game
                                        .getBlackPlayer().getName() + " Wins !!",
                                "Congratulations", JOptionPane.PLAIN_MESSAGE);
                    }
                    int x = JOptionPane.showConfirmDialog(null,
                            "Do you want to play again ??",
                            "Confirmation Message", 0);
                    if (x == 0) {
                        this.setVisible(false);
                        restart.setVisible(true);
                    } else {
                        System.exit(0);
                    }
                }
            }
        }
    }*/

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
            if (hovered.getCheckerColor() == gameBoard.getTurn()) {
                System.out.println("Hovered on [" + hovered.getPosition().getX() + "," + hovered.getPosition().getY() + "]");
                //hovered.setIcon(null);
                Checker checker = gameBoard.getCheckerAt(hovered.getPosition());
                if (checker != null) {
                    ArrayList<Position> positions = gameBoard.canGo(checker);
                    for (Position p : positions) {
                        CheckerButton checkerButton = findByPosition(p);
                        checkerButton.setHighlighted(true);
                        checkerButton.setIcon(CheckerButton.imageIcon);
                    }
                }
            }
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        Object temp = e.getSource();
        if(temp instanceof CheckerButton) {
            CheckerButton hovered = (CheckerButton) temp;
            if (hovered.getCheckerColor() == gameBoard.getTurn()) {
                System.out.println("Hovered on [" + hovered.getPosition().getX() + "," + hovered.getPosition().getY() + "]");
                //hovered.setIcon(null);
                Checker checker = gameBoard.getCheckerAt(hovered.getPosition());
                if (checker != null) {
                    ArrayList<Position> positions = gameBoard.canGo(checker);
                    for (Position p : positions) {
                        CheckerButton checkerButton = findByPosition(p);
                        checkerButton.setHighlighted(false);
                        checkerButton.setIcon(matchingImgIcon(checkerButton.getCheckerColor()));
                    }
                }
            }
        }

    }

    private ImageIcon matchingImgIcon(Color c) {
        return c == Color.WHITE ? whitePlayerImg :
                c == Color.BLACK ? blackPlayerImg :
                        null;
    }

    public static void main(String[]args) throws IOException{
      GameWindow gameWindow = new GameWindow();
    }
}
