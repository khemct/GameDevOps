package entities;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import static utilz.Constants.Directions.*;
import static utilz.Constants.Directions.DOWN;
import static utilz.Constants.PlayerConstants.*;

public class Player extends Entity{

    private BufferedImage[][] animations;
    private int aniTick, aniIndex, aniSpeed = 160;
    private int playerAction = IDLE;
    private boolean moving = false;
    private boolean left, up, right, down;

    public Player(float x, float y){
        super(x, y);
        loadAnimation();
    }

    public void update(){

        updateAnimationTick();
        setAnimation();
        updatePos();

    }

    public void render(Graphics g){

        g.drawImage(animations[playerAction][aniIndex],(int)x, (int)y,67,70, null);

    }

    private void setAnimation() {
        if (moving)
            playerAction = RUNNING;
        else
            playerAction = IDLE;
    }

    private void updateAnimationTick() {
        aniTick++;
        if(aniTick >= aniSpeed) {
            aniTick = 0;
            aniIndex++;

            System.out.println("aniIndex: " + aniIndex + " / GetSpriteAmount: " + GetSpriteAmount(playerAction));
            // เพิ่มการตรวจสอบไม่ให้ aniIndex เกินขอบเขตของ array
            if(aniIndex >= animations[playerAction].length) {
                aniIndex = 0;  // รีเซ็ต index เมื่อเกินขอบเขต
            }
        }
    }

    private void updatePos() {



    }

    private void loadAnimation() {

        InputStream is = getClass().getResourceAsStream("/ManWalk.png");

        try {
            BufferedImage img = ImageIO.read(is);

            animations = new BufferedImage[3][5];
            for(int j = 0; j < animations.length; j++)
                for(int i = 0; i < animations[j].length; i++)
                    animations[j][i] = img.getSubimage(i * 135, j * 140, 135, 140);

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

    public boolean isLeft() {
        return left;
    }

    public void setLeft(boolean left) {
        this.left = left;
    }
    public boolean isUp() {
        return up;
    }

    public void setUp(boolean up) {
        this.up = up;
    }
    public boolean isRight() {
        return right;
    }

    public void setRight(boolean right) {
        this.right = right;
    }

    public boolean isDown() {
        return down;
    }

    public void setDown(boolean down) {
        this.down = down;
    }
}
