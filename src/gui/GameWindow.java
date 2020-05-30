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
import javax.swing.JOptionPane;

public class GameWindow extends JFrame implements MouseListener,
        ActionListener {
    private JFrame frame;
    private CheckerButton[][] checkerButtons;
    private JPanel right;
    private Board gameBoard;
    private JButton play;
    private JButton pause;
    private JButton restart;

    private ImageIcon whitePieceImg;
    private ImageIcon blackPieceImg;

    private Checker clickedChecker;
    private MachinePlayer bot;

    public GameWindow() throws IOException {
        double wid2HeiRatio = 1066.0 / 854.0;
        int windowHeight = 720;
        int windowWidth = (int) (windowHeight * wid2HeiRatio);

        double resizeRatio = windowHeight / 854.0;


        int topHei = (int) (windowHeight * 0.14);
        int menuWid = (int) (windowWidth * 0.31);

        whitePieceImg = getResizedIcon("WhitePlayer.png",(int) (65 * resizeRatio),(int) (65 * resizeRatio));
        blackPieceImg = getResizedIcon("BlackPlayer.png",(int) (65 * resizeRatio),(int) (65 * resizeRatio));


        gameBoard = new Board();
        frame = new JFrame("Lines Of Action");
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
        menu.setPreferredSize(new Dimension((int) (0.92 * menuWid), (int) (0.82 * windowHeight)));

        JPanel menu2 = new JPanel();
        menu2.setOpaque(false);
        menu2.setPreferredSize(new Dimension(menuWid, (int) (0.82 * windowHeight)));
        menu2.setLayout(new BorderLayout());
        menu2.add(menu, BorderLayout.LINE_START);

        JPanel right2 = new JPanel();
        right2.setOpaque(false);
        right2.setLayout(new BorderLayout());

        JPanel rightBtm = new JPanel();
        rightBtm.setOpaque(false);
        rightBtm.setPreferredSize(new Dimension((int) (0.59 * windowWidth), (int) (0.165 * windowHeight)));
        right2.add(rightBtm, BorderLayout.PAGE_END);

        right = new JPanel();
        right.setOpaque(false);
        right.setPreferredSize(new Dimension((int) (0.58 * windowWidth),(int) (0.56 * windowWidth)));
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
        pause =   getButton("PauseBTNpressed.png", "PauseBTNnotpressed.png", btnWidth, bthHeight);
        pause.addActionListener(this);
        menu.add(pause, gbc);
        restart =getButton("RestartBTNpressed.png", "RestartBTNnotpressed.png", btnWidth, bthHeight);
        restart.addActionListener(this);
        menu.add(restart, gbc);

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
        else if(e.getSource()==pause)deactivateBoard();
        else if(e.getSource()==restart) {
            frame.dispose();
            try {
                GameWindow gameWindow = new GameWindow();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }

        }
        play.removeActionListener(this);
    }

    public void makeBoardActive(){
        for (int i = 0; i <8 ; i++) {
            for (int j = 0; j <8 ; j++) {
                checkerButtons[i][j].addMouseListener(this);
            }
        }
    }
    public void deactivateBoard(){
        for (int i = 0; i <8 ; i++) {
            for (int j = 0; j < 8; j++) {
                checkerButtons[i][j].removeMouseListener(this);
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
                Checker c = gameBoard.getCheckerAt(p);
                if (!isFirstCheckerClicked())
                {
                    setFirstCheckerClicked(c);
                    setClickedCanGo(c);
                } else if (isClickedFirstCheckerAgain(c)) {
                    deselectFirstChecker();
                    setImageIcons();
                }
            } else if (isFirstCheckerClicked()&&getCheckerButtonAt(p).isHighlieghted()) {
                gameBoard.makeAMove(clickedChecker, p);
                setImageIcons();
                deselectFirstChecker();
                if (gameBoard.GameOver() != null&&frame.isActive()){
                   deactivateBoard();
                    frame.dispose();
                   endMessage(Color.WHITE);
                }
                botMakeMove(Color.BLACK,gameBoard);
                setImageIcons();
                if (gameBoard.GameOver() != null && frame.isActive()) {
                   deactivateBoard();
                    frame.dispose();
                   endMessage(Color.BLACK);
                }
            }
        }
    }
    public void endMessage(Color color){
        if(color==Color.WHITE){
            JOptionPane.showMessageDialog(frame,"THE WHITE PLAYER WON!!!");
        }
       else JOptionPane.showMessageDialog(frame,"THE BLACK PLAYER WON!!!");
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

    public void setClickedCanGo(Checker checker){
        ArrayList<Position>position = gameBoard.canGo(checker);
        for (Position pos : position) {
            CheckerButton cb = findByPosition(pos);
            cb.setIcon(CheckerButton.imageIcon);
            cb.setHighlighted(true);
        }
    }
    public CheckerButton getCheckerButtonAt(Position position){
        for (CheckerButton[] cArray:checkerButtons){
            for (CheckerButton checkerButton1:cArray)
                if (checkerButton1.getPosition()==position)
                    return checkerButton1;
        }
        return null;
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
            if (gameBoard.getTurn() == gameBoard.getColorOfCheckerAt(p) && !isFirstCheckerClicked()) {
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
            if (gameBoard.getTurn() == gameBoard.getColorOfCheckerAt(p) && !isFirstCheckerClicked())
            {
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
