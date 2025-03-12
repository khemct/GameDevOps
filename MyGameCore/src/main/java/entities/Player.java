package entities;

import game.Game;
import utilz.LoadSave;
import java.awt.*;
import java.awt.image.BufferedImage;
import static utilz.Constants.PlayerConstants.*;
import static utilz.HelpMethods.CanMoveHere;

public class Player extends Entity{

    private BufferedImage[][] animations;
    private int aniTick, aniIndex, aniSpeed = 20;
    private int playerAction = IDLE;
    private boolean moving = false, attacking = false;
    private boolean left, up, right, down;
    private float playerSpeed = 2.2f;
    private int[][] lvlData;
    private float xDrawOffset = 21  * Game.SCALE;
    private float yDrawOffset = 4  * Game.SCALE;

    public Player(float x, float y, int width, int height){
        super(x, y, width, height);
        loadAnimation();
        initHitBox(x, y, 20 * Game.SCALE, 28 * Game.SCALE);

    }

    public void update(){

        updatePos();
        updateAnimationTick();
        setAnimation();


    }

    public void render(Graphics g){

        g.drawImage(animations[playerAction][aniIndex],(int)(hitbox.x - xDrawOffset), (int)(hitbox.y - yDrawOffset),128,80, null);
        drawHitbox(g);

    }

    private void setAnimation() {
        int startAni = playerAction;

        if (attacking)
            playerAction = ATTACK_1;
        else if (moving)
            playerAction = RUNNING;
        else
            playerAction = IDLE;

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
                aniIndex = 0;
                if (playerAction == ATTACK_1)  // รีเซ็ตการโจมตีหลังจากแอนิเมชันโจมตีเท่านั้น
                    attacking = false;
            }

        }
    }

    private void updatePos() {

        moving = false;
        if (!left && !right && !up && !down)
            return;;

            float xSpeed = 0, ySpeed = 0;

        if (left && !right)
            xSpeed = -playerSpeed;
         else if (right && !left)
            xSpeed = playerSpeed;

        if (up && !down)
            ySpeed = -playerSpeed;
         else if (down && !up)
            ySpeed = playerSpeed;
/*
        if (CanMoveHere(x + xSpeed, y + ySpeed, width, height, lvlData)){
            this.x += xSpeed;
            this.y += ySpeed;
            moving = true;
        }
*/
        if (CanMoveHere(hitbox.x + xSpeed, hitbox.y + ySpeed, hitbox.width, hitbox.height, lvlData)){
            hitbox.x += xSpeed;
            hitbox.y += ySpeed;
            moving = true;

        }
    }

    private void loadAnimation() {
        BufferedImage img = LoadSave.GetSpriteAtlas(LoadSave.PLAYER_ATLAS);
        animations = new BufferedImage[9][]; // รองรับทุกแอคชัน

        animations[IDLE] = new BufferedImage[5];
        animations[RUNNING] = new BufferedImage[6];
        animations[ATTACK_1] = new BufferedImage[3];
        animations[HIT] = new BufferedImage[4];
        animations[JUMP] = new BufferedImage[3];
        animations[ATTACK_JUMP_1] = new BufferedImage[3];
        animations[ATTACK_JUMP_2] = new BufferedImage[3];
        animations[GROUND] = new BufferedImage[2];
        animations[FALLING] = new BufferedImage[1];

        for (int j = 0; j < animations.length; j++) {
            if (animations[j] != null) { // เช็คว่า array นั้นมีอยู่จริง
                for (int i = 0; i < animations[j].length; i++)
                    animations[j][i] = img.getSubimage(i * 64, j * 40, 64, 40);
            }
        }
    }


    public void loadLvlData(int[][] lvlData){
        this.lvlData = lvlData;

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
