package entities;

import utilz.LoadSave;

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
    private boolean moving = false, attacking = false;
    private boolean left, up, right, down;
    private float playerSpeed = 2.0f;

    public Player(float x, float y){
        super(x, y);
        loadAnimation();
    }

    public void update(){

        updatePos();
        updateAnimationTick();
        setAnimation();


    }

    public void render(Graphics g){

        g.drawImage(animations[playerAction][aniIndex],(int)x, (int)y,67,70, null);

    }

    private void setAnimation() {
        int startAni = playerAction;

        if (moving)
            playerAction = RUNNING;
        else
            playerAction = IDLE;

        if (attacking)
            playerAction = ATTACK_1;

        if (startAni != playerAction)
            resetAniTick();
    }

    private void resetAniTick(){
        aniTick = 0;
        aniIndex = 0;
    }

    private void updateAnimationTick() {
        aniTick++;
        if(aniTick >= aniSpeed) {
            aniTick = 0;
            aniIndex++;

            System.out.println("aniIndex: " + aniIndex + " / GetSpriteAmount: " + GetSpriteAmount(playerAction));
            // เพิ่มการตรวจสอบไม่ให้ aniIndex เกินขอบเขตของ array
            if(aniIndex >= GetSpriteAmount(playerAction)) {
                aniIndex = 0;  // รีเซ็ต index เมื่อเกินขอบเขต
                attacking = false;
            }
        }
    }

    private void updatePos() {

        moving = false;

        if (left && !right){
            x -= playerSpeed;
            moving = true;

        } else if (right && !left){
            x += playerSpeed;
            moving = true;
        }

        if (up && !down){
            y -= playerSpeed;
            moving = true;

        } else if (down && !up){
            y += playerSpeed;
            moving = true;
        }

    }

    private void loadAnimation() {

            BufferedImage img = LoadSave.GetSpriteAtlas(LoadSave.PLAYER_ATLAS);

            animations = new BufferedImage[3][5];
            for (int j = 0; j < animations.length; j++)
                for (int i = 0; i < animations[j].length; i++)
                    animations[j][i] = img.getSubimage(i * 135, j * 140, 135, 140);

    }

    public void resetDirBooleans(){
        left = false;
        right = false;
        up = false;
        down = false;
    }

    public void setAttacking(boolean attacking) {
        this.attacking = attacking;
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
