package gamestates;

import entities.Player;
import game.Game;
import levels.LevelManager;
import ui.PauseOverlay;
import utilz.LoadSave;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class Playing extends State implements Statemethods {
    private Player player;
    private LevelManager levelManager;
    private PauseOverlay pauseOverlay;
    private boolean paused = false;
    private int xLvOffset;
    private int leftBorder = (int) (0.2 * Game.GAME_WIDTH);
    private int rightBorder = (int) (0.8 * Game.GAME_WIDTH);
    private int lvlTileWide = LoadSave.GetLevelData()[0].length;
    private int maxTileOffset = lvlTileWide - Game.TILE_IN_WIDTH;
    private int maxLvlOffsetX = maxTileOffset * Game.TILES_SIZE;

    public Playing(Game game) {
        super(game);
        initClasses();
    }


    private void initClasses(){
        levelManager = new LevelManager(game);
        player = new Player(200,200, (int) (48 * Game.SCALE), (int) (72 * Game.SCALE));
        player.loadLvlData(levelManager.getCurrentLevel().getLevelData());
        pauseOverlay = new PauseOverlay(this);

    }


    @Override
    public void update() {
        if (!paused){
            levelManager.update();
            player.update();
            checkCloseToBorder();
        } else {
            pauseOverlay.update();
        }
    }

    private void checkCloseToBorder() {
        int playerX = (int) player.getHitbox().x;
        int diff = playerX-xLvOffset;
        if (diff > rightBorder)
            xLvOffset += diff - rightBorder;
        else if(diff < leftBorder)
            xLvOffset += diff - leftBorder;

        if(xLvOffset > maxLvlOffsetX)
            xLvOffset = maxTileOffset;
        else if (xLvOffset < 0 )
            xLvOffset=0;
    }

    @Override
    public void draw(Graphics g) {
        levelManager.draw(g, xLvOffset);
        player.render(g, xLvOffset);

        if (paused) {
            g.setColor(new Color(0,0,0,150));
            g.fillRect(0,0,Game.GAME_WIDTH,Game.GAME_HEIGHT);
            pauseOverlay.draw(g);
        }
    }

    public void mouseDragged(MouseEvent e){
        if (paused)
            pauseOverlay.mouseDragged(e);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        System.out.println("mClick");
        if (e.getButton() == MouseEvent.BUTTON1)
            player.setAttacking(true);

    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (paused)
            pauseOverlay.mousePressed(e);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (paused)
            pauseOverlay.mouseReleased(e);
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if (paused)
            pauseOverlay.mouseMoved(e);
    }

    public void unpauseGame(){
        paused = false;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()){
            case KeyEvent.VK_A:
                player.setLeft(true);
                break;
            case KeyEvent.VK_D:
                player.setRight(true);
                break;
            case  KeyEvent.VK_SPACE:
                player.setJump(true);
                break;
            case KeyEvent.VK_ESCAPE:
                paused = !paused;
                break;

        }
    }



    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
                case KeyEvent.VK_A:
                    player.setLeft(false);
                    break;
                case KeyEvent.VK_D:
                    player.setRight(false);
                    break;
                case KeyEvent.VK_SPACE:
                    player.setJump(false);
                    break;
        }

    }

    public void windowLostFocus(){
        player.resetDirBooleans();
    }

    public Player getPlayer(){
        return player;
    }

}
