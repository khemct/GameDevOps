package game;

import inputs.KeyBoardInputs;
import inputs.MouseInputs;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class GamePanel extends JPanel {

    private MouseInputs mouseInputs;
    private float xDelta=100,yDelta=100;

    private int frames = 0;
    private long lastCheck = 0;



    public GamePanel(){


        mouseInputs = new MouseInputs(this);
        addKeyListener(new KeyBoardInputs(this));
        addMouseListener(mouseInputs);
        addMouseMotionListener(mouseInputs);
    }

    public void changeXDelta(int value){
        this.xDelta += value;

    }
    public void changeYDelta(int value){
        this.yDelta += value;

    }
    public void setRectPos(int x, int y){
        this.xDelta=x;
        this.yDelta=y;

    }

    public void paintComponent(Graphics g){

        super.paintComponent(g);
        

        frames++;
        if(System.currentTimeMillis() - lastCheck >= 1000){
            lastCheck = System.currentTimeMillis();
            System.out.println("FPS : "+frames);
            frames=0;

        }

        repaint();
    }

}
