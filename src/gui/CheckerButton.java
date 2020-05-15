package gui;

import engine.Color;
import engine.Position;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Ellipse2D;

public class CheckerButton  extends JButton implements MouseListener,
        ActionListener {
/*    private engine.Color checkerColor;*/
    private Position position;
    private boolean highlighted;
    private static boolean oneButtonClicked = false;
    //ImageIcon imageIcon = new ImageIcon("resources/GreenTile.png");
    protected static ImageIcon imageIcon = GameWindow.getResizedIcon("GreenTile.png", 60,60);

    public CheckerButton(engine.Color color, /*int x, int y,*/int i,int j){
        this.position = new Position(i,j);
        setLayout(null);
        this.addMouseListener(this);
        //this.checkerColor = color;
        //setBounds(x, y, 85, 85);
        //setPreferredSize(new Dimension(72,72));
        setOpaque(false);
        setContentAreaFilled(false); //sets transparent buttons
        setBorderPainted(false);
        this.highlighted = false;
    }
    public void Highlighted(){
        setHighlighted(true);
        this.setIcon(imageIcon);
        validate();
        repaint();
    }

/*    public Color getCheckerColor() {
        return checkerColor;
    }*/

    public Position getPosition() {
        return position;
    }

    public boolean isHighlighted() {
        return highlighted;
    }

    public void setHighlighted(boolean bool){
        highlighted = bool;
    }

    public static void switchOneButtonClicked() {
        oneButtonClicked = !oneButtonClicked;
    }

    public static boolean isOneButtonClicked() {
        return oneButtonClicked;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
//keep empty
    }

    @Override
    public void mouseReleased(MouseEvent e) {
//keep empty
    }

    @Override
    public void mouseEntered(MouseEvent e) {
/*        setFocusable(true);
        grabFocus();
        setEnabled(true);*/
    }

    @Override
    public void mouseExited(MouseEvent e) {
/*        setFocusable(false);
        grabFocus();
        setEnabled(false);*/
    }
}
