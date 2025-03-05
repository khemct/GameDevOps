package game;

import inputs.KeyBoardInputs;
import inputs.MouseInputs;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GamePanel extends JPanel {

    private MouseInputs mouseInputs;
    private int xDelta=100,yDelta=100;

    public GamePanel(){

        mouseInputs = new MouseInputs(this);
        addKeyListener(new KeyBoardInputs(this));
        addMouseListener(mouseInputs);
        addMouseMotionListener(mouseInputs);
    }

    public void changeXDelta(int value){
        this.xDelta += value;
        repaint();
    }
    public void changeYDelta(int value){
        this.yDelta += value;
        repaint();
    }
    public void setRectPos(int x, int y){
        this.xDelta=x;
        this.yDelta=y;
        repaint();
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        g.fillRect(xDelta,yDelta,100,50 );
    }
}
