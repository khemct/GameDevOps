package entities;

import game.Game;

import static utilz.Constants.EnemyConstants.*;
import static utilz.HelpMethods.*;
import static utilz.Constants.Directions.*;

public abstract class Enemy extends Entity{
    protected int aniIndex, enemyState, enemyType;
    protected int aniTick, aniSpeed = 25;
    protected boolean firstUpdate = true;
    protected boolean inAir = false;
    protected float fallSpeed;
    protected float gravity = 0.4f * Game.SCALE;
    protected float walkSpeed = 0.5f * Game.SCALE;
    protected int walkDir = LEFT;

    public Enemy(float x, float y, int width, int height, int enemyType){
        super(x, y, width, height);
        this.enemyType = enemyType;
        initHitBox(x, y, width, height);
    }

    protected void firstUpdateCheck(int[][] lvlData) {
        if (!IsEntityOnFloor(hitbox, lvlData))
            inAir = true;
        firstUpdate = false;
    }

    protected void updateInAir(int[][] lvlData) {
        if(CanMoveHere(hitbox.x,hitbox.y + fallSpeed,hitbox.width,hitbox.height,lvlData)) {
            hitbox.y += fallSpeed;
            fallSpeed += gravity;
        }else {
            inAir = false;
            hitbox.y = GetEntityYPosUnderRoofOrAboveFloor(hitbox, aniSpeed);
        }
    }

    protected void move(int[][] lvlData) {
        float xSpeed = 0 ;
        if(walkDir == LEFT)
            xSpeed = -walkSpeed;
        else
            xSpeed = walkSpeed;
        if (CanMoveHere(hitbox.x, hitbox.y,hitbox.width,hitbox.height,lvlData))
            if(IsFloor(hitbox, xSpeed, lvlData)){
                hitbox.x = xSpeed;
                return;
            }
        changeWalkDir();
    }

    protected void newState(int enemyState) {
        this.enemyState = enemyState;
        aniTick = 0;
        aniIndex = 0;

    }

    protected void updateAnimationTick(){
        aniTick++;
        if (aniTick >= aniSpeed){
            aniTick = 0;
            aniIndex++;
            if (aniIndex >= GetSpriteAmount(enemyType, enemyState)) {
                aniIndex = 0;
            }
        }
    }


    protected void changeWalkDir() {
        if (walkDir == LEFT)
            walkDir = RIGHT;
        else
            walkDir = LEFT;
    }

    public int getAniIndex(){
        return aniIndex;
    }

    public int getEnemyState(){
        return enemyState;
    }


}