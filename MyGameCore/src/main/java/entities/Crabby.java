package entities;


import game.Game;

import static utilz.Constants.Directions.LEFT;
import static utilz.Constants.EnemyConstants.*;
import static utilz.HelpMethods.*;

public class Crabby extends Enemy{

    public Crabby(float x, float y){
        super(x, y, CRABBY_WIDTH, CRABBY_HEIGHT, CRABBY);
        initHitBox(x,y,(int)( 22 * Game.SCALE), (int)(19 * Game.SCALE));
    }

    public void update(int[][] lvlData){
        updateMove(lvlData);
        updateAnimationTick();
    }


    private void updateMove(int[][] lvlData){
        if(firstUpdate)
            firstUpdateCheck(lvlData);

        if(inAir)
            updateInAir(lvlData);

        else {
            switch (enemyState){
                case IDLE:
                    newState(RUNNING);
                case RUNNING:
                    move(lvlData);
                    break;
            }

        }

    }

}