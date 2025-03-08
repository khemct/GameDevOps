package game;

import inputs.KeyBoardInputs;
import inputs.MouseInputs;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

public class GamePanel extends JPanel {

    private MouseInputs mouseInputs;
    private float xDelta=100,yDelta=100;
    private int frames = 0;
    private long lastCheck = 0;
    private BufferedImage img, subImg;



    public GamePanel(){
        mouseInputs = new MouseInputs(this);

        importImg();

        setPanelSize();
        addKeyListener(new KeyBoardInputs(this));
        addMouseListener(mouseInputs);
        addMouseMotionListener(mouseInputs);
    }

    private void importImg() {
        InputStream is = getClass().getResourceAsStream("/ManWalk.png");

        try {
            img = ImageIO.read(is);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void setPanelSize() {
        Dimension size = new Dimension(1280,800);
        setMaximumSize(size);
        setMinimumSize(size);
        setPreferredSize(size);
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
        subImg = img.getSubimage(2*135,0*140,135,140);
        g.drawImage(subImg,(int)xDelta, (int)yDelta,67,70, null);
        

        frames++;
        if(System.currentTimeMillis() - lastCheck >= 1000){
            lastCheck = System.currentTimeMillis();
            System.out.println("FPS : "+frames);
            frames=0;

        }

        repaint();
    }

}
