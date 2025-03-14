package entities;

import game.Game;
import utilz.LoadSave;
import java.awt.*;
import java.awt.image.BufferedImage;
import static utilz.Constants.PlayerConstants.*;
import static utilz.HelpMethods.*;

public class Player extends Entity{

    private BufferedImage[][] animations;
    private int aniTick, aniIndex, aniSpeed = 25;
    private int playerAction = IDLE;
    private boolean moving = false, attacking = false;
    private boolean left, up, right, down, jump;
    private float playerSpeed = 2.2f;
    private int[][] lvlData;
    private float xDrawOffset = 21  * Game.SCALE;
    private float yDrawOffset = 4  * Game.SCALE;
    //jump and gravity
    private float airSpeed = -1.0f;
    private float gravity = 0.04f * Game.SCALE;
    private float jumpSpeed = -2.25f * Game.SCALE;
    private float fallSpeedAfterCollision = 0.5f * Game.SCALE;
    private boolean inAir = false;


    public Player(float x, float y, int width, int height){
        super(x, y, width, height);
        loadAnimation();
        initHitBox(x, y, 20 * Game.SCALE, 27 * Game.SCALE);

    }

    public void update(){

        updatePos();
        updateAnimationTick();
        setAnimation();


    }

    public void render(Graphics g, int lvlOffset){

        g.drawImage(animations[playerAction][aniIndex],(int)(hitbox.x - xDrawOffset) - lvlOffset, (int)(hitbox.y - yDrawOffset),128,80, null);
        //drawHitbox(g);

    }

    private void setAnimation() {
        int startAni = playerAction;


        if (moving)
            playerAction = RUNNING;
        else
            playerAction = IDLE;

        if (inAir){
            if (airSpeed<0)
                playerAction = JUMP;
            else
                playerAction = FALLING;
        }

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
                aniIndex = 0;
                if (playerAction == ATTACK_1)  // รีเซ็ตการโจมตีหลังจากแอนิเมชันโจมตีเท่านั้น
                    attacking = false;
            }

        }
    }

    private void updatePos() {

        moving = false;

        if (jump)
            jump();
//        if (!left && !right && !inAir)
//            return;
        if(!inAir)
            if((!left && !right) || (right && left))
                return;

        float xSpeed = 0;

        if (left )
            xSpeed -= playerSpeed;
        if (right )
            xSpeed += playerSpeed;
        if(!inAir){
            if (!IsEntityOnFloor(hitbox, lvlData)){
                inAir = true;
            }
        }

        if(inAir){
            if (CanMoveHere(hitbox.x, hitbox.y + airSpeed, hitbox.width, hitbox.height, lvlData)){
                hitbox.y += airSpeed;
                airSpeed += gravity;
                updateXPos(xSpeed);
            }else {
                hitbox.y = GetEntityYPosUnderRoofOrAboveFloor(hitbox,airSpeed);
                if(airSpeed > 0)
                    resetInAir();
                else
                    airSpeed = fallSpeedAfterCollision;
                updateXPos(xSpeed);
            }

        }else
            updateXPos(xSpeed);
        moving = true;
    }

    private void jump() {
        if(inAir)
            return;
        inAir = true;
        airSpeed = jumpSpeed;
    }

    private void resetInAir() {
        inAir = false;
        airSpeed = 0;
    }

    private void updateXPos(float xSpeed) {

        if (CanMoveHere(hitbox.x + xSpeed, hitbox.y , hitbox.width, hitbox.height, lvlData)){
            hitbox.x += xSpeed;
        }else {
            hitbox.x = GetEntityXPosNextToWall(hitbox, xSpeed);
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
        if(!IsEntityOnFloor(hitbox, lvlData))
            inAir = true;

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

    public void setJump(boolean jump){
        this.jump = jump;
    }
}
