package ui;

import game.Game;
import gamestates.Gamestate;
import gamestates.Playing;

import java.awt.*;
import java.awt.event.KeyEvent;

public class GameOverOveralay {

    private Playing playing;
    public GameOverOveralay(Playing playing){
        this.playing = playing;
    }

    public  void  draw(Graphics g){
        g.setColor(new Color(0,0,0,200));
        g.fillRect(0,0, Game.GAME_WIDTH,Game.GAME_HEIGHT);

        g.setColor(Color.white);
        g.drawString("GAME OVER", Game.GAME_WIDTH / 2,150);
        g.drawString("Press esc to enter Main menu!",Game.GAME_WIDTH / 2,300);
    }

    public void keyPressed(KeyEvent e){
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE){
            playing.resetAll();
            Gamestate.state = Gamestate.MENU;
        }

    }
}
