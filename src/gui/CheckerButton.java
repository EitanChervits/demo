package gui;

import engine.Position;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class CheckerButton  extends JButton implements MouseListener,
        ActionListener {
    private engine.Color checkerColor;
    private Position position;
    private boolean highlighted;
    ImageIcon imageIcon = new ImageIcon("resources/GreenTitle.png");

    public CheckerButton(engine.Color color, int x, int y){
        this.position = new Position(x,y);
        setLayout(null);
        this.addMouseListener(this);
        this.checkerColor = color;
        setSize(77,59);
        setContentAreaFilled(false); //sets transparent buttons
        this.highlighted = false;
    }
    public void Highlighted(){
        setHighlighted(true);
        this.setIcon(imageIcon);
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
//keep empty
    }

    @Override
    public void mouseExited(MouseEvent e) {
//keep empty
    }
}
