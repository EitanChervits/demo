package gui;

import engine.Position;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class CheckerButton  extends JButton implements MouseListener,
        ActionListener {

    private Position position;
    private boolean highlighted;

    protected static ImageIcon imageIcon = GameWindow.getResizedIcon("GreenTile.png", 60,60);

    public CheckerButton(int i,int j){
        this.position = new Position(i,j);
        setLayout(null);
        this.addMouseListener(this);

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

    public Position getPosition() {
        return position;
    }

    public boolean isHighlieghted() {
        return highlighted;
    }

    public void setHighlighted(boolean bool){
        highlighted = bool;
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
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
}
