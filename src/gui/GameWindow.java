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
    private MachinePlayer bot;

    public GameWindow() throws IOException {
        double wid2HeiRatio = 1066.0 / 854.0;
        Dimension screenDimension = Toolkit.getDefaultToolkit().getScreenSize();
        int screenHeight = screenDimension.height;
        int windowHeight = (int) (0.8 * screenHeight);
        int windowWidth = (int) (windowHeight * wid2HeiRatio);

        double resizeRatio = windowHeight / 854.0;


        int topHei = (int) (windowHeight * 0.14);
        int menuWid = (int) (windowWidth * 0.3);;

        whitePieceImg = getResizedIcon("WhitePlayer.png",(int) (65 * resizeRatio),(int) (65 * resizeRatio));
        blackPieceImg = getResizedIcon("BlackPlayer.png",(int) (65 * resizeRatio),(int) (65 * resizeRatio));


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
        top.setPreferredSize(new Dimension(windowWidth, topHei));

        JPanel menu = new JPanel();
        menu.setOpaque(false);
        menu.setPreferredSize(new Dimension(260, windowHeight - topHei - 35));

        JPanel menu2 = new JPanel();
        menu2.setOpaque(false);
        menu2.setPreferredSize(new Dimension(menuWid, windowHeight - topHei - 35));
        JPanel menuRight = new JPanel();
        menuRight.setOpaque(false);
        menuRight.setPreferredSize(new Dimension(0, windowHeight - topHei - 35));
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
        right.setPreferredSize(new Dimension(windowWidth - menuWid - 110,600));
        right.setLayout(new GridLayout(8,8));
        updateBoard();
        setImageIcons();

        right2.add(right, BorderLayout.PAGE_START);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;

        int btnWidth = (int) (202 * resizeRatio);
        int bthHeight = (int) (91 * resizeRatio);
        play = getButton("PlayBTNpressed.png", "PlayBTNnotpressed.png", btnWidth, bthHeight);
        play.addActionListener(this);
        menu.add(play, gbc);
        menu.add(getButton("RestartBTNpressed.png", "RestartBTNnotpressed.png", btnWidth, bthHeight), gbc);
        menu.add(getButton("PauseBTNpressed.png", "PauseBTNnotpressed.png", btnWidth, bthHeight), gbc);
        menu.add(getButton("MainMenuBTNpressed.png", "MainMenuBTNnotpressed.png", btnWidth, bthHeight), gbc);

        frame.add(top, BorderLayout.PAGE_START);
        frame.add(menu2, BorderLayout.LINE_START);
        frame.add(right2, BorderLayout.CENTER);

        JPanel rightEmpty = new JPanel();
        rightEmpty.setOpaque(false);
        rightEmpty.setPreferredSize(new Dimension(60, windowHeight - topHei));
        frame.add(rightEmpty, BorderLayout.LINE_END);

        frame.setPreferredSize(new Dimension(windowWidth, windowHeight));
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
                checkerButtons[i][j] = new CheckerButton(i, j);
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
                if (!isFirstCheckerClicked()) {
                    setFirstCheckerClicked(c);
                } else if (isClickedFirstCheckerAgain(c)) {
                    deselectFirstChecker();
                }
            } else if (isFirstCheckerClicked()) {
                gameBoard.makeAMove(clickedChecker, p);
                setImageIcons();
                deselectFirstChecker();
                deleteOpponentChecker(p);
                botMakeMove(Color.BLACK,gameBoard);
                setImageIcons();

            }
        }
    }

    private boolean isFirstCheckerClicked() {
        return clickedChecker != null;
    }

    private void setFirstCheckerClicked(Checker c) {
        clickedChecker = c;
    }

    private boolean isClickedFirstCheckerAgain(Checker c) {
        return clickedChecker == c;
    }

    private void deselectFirstChecker() {
        setFirstCheckerClicked(null);
    }
    public void botMakeMove(Color color,Board board){
        bot = new MachinePlayer(color,board);
        Move botMove =  bot.makeMove(board);
        gameBoard.makeAMove(botMove.getChecker(),botMove.getPosition());
    }
    public void deleteOpponentChecker(Position position){
        if(gameBoard.getColorOfCheckerAt(position)!=gameBoard.getTurn()){
            Player player = gameBoard.getOpponent(gameBoard.getTurn());
            player.deleteChecker(gameBoard.getCheckerAt(position));
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
