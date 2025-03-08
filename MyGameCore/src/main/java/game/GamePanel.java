package game;

import inputs.KeyBoardInputs;
import inputs.MouseInputs;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class GamePanel extends JPanel {

    private MouseInputs mouseInputs;
    private float xDelta=100,yDelta=100;
    private int frames = 0;
    private long lastCheck = 0;
    private BufferedImage img;
    private BufferedImage[][] animations;
    private int aniTick, aniIndex, aniSpeed = 160;



    public GamePanel(){
        mouseInputs = new MouseInputs(this);

        importImg();
        loadAnimation();

        setPanelSize();
        addKeyListener(new KeyBoardInputs(this));
        addMouseListener(mouseInputs);
        addMouseMotionListener(mouseInputs);
    }

    private void loadAnimation() {
        animations = new BufferedImage[3][5];

        for(int j = 0; j < animations.length; j++)
            for(int i = 0; i < animations[j].length; i++)
                animations[j][i] = img.getSubimage(i*135,j*140 ,135, 140);

    }

    private void importImg() {
        InputStream is = getClass().getResourceAsStream("/ManWalk.png");

        try {
            img = ImageIO.read(is);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
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

    private void updateAnimationTick() {

        aniTick++;
        if(aniTick >= aniSpeed) {
            aniTick = 0;
            aniIndex++;
            if(aniIndex >= 5) {
                aniIndex = 0;
            }
        }

    }

    public void paintComponent(Graphics g){

        super.paintComponent(g);

        updateAnimationTick();

        g.drawImage(animations[1][aniIndex],(int)xDelta, (int)yDelta,67,70, null);
        

        frames++;
        if(System.currentTimeMillis() - lastCheck >= 1000){
            lastCheck = System.currentTimeMillis();
            System.out.println("FPS : "+frames);
            frames=0;

        }

        repaint();
    }

}
