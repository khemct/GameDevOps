package gamestates;

import entities.EnemyManager;
import entities.Player;
import game.Game;
import levels.LevelManager;
import ui.PauseOverlay;
import utilz.LoadSave;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.Random;

import static utilz.Constants.Environment.*;

public class Playing extends State implements Statemethods {
    private Player player;
    private LevelManager levelManager;
    private EnemyManager enemyManager;
    private PauseOverlay pauseOverlay;
    private boolean paused = false;
    private int xLvOffset;
    private int leftBorder = (int) (0.2 * Game.GAME_WIDTH);
    private int rightBorder = (int) (0.8 * Game.GAME_WIDTH);
    private int lvlTileWide = LoadSave.GetLevelData()[0].length;
    private int maxTileOffset = lvlTileWide - Game.TILE_IN_WIDTH;
    private int maxLvlOffsetX = maxTileOffset * Game.TILES_SIZE;
    private BufferedImage backgroundImg, bigCloud, smallCloud;

    private int[] smallCloudPos;
    private Random rnd = new Random();

    public Playing(Game game) {
        super(game);
        initClasses();

        backgroundImg = LoadSave.GetSpriteAtlas(LoadSave.PLAYING_BG_IMG);
        bigCloud = LoadSave.GetSpriteAtlas(LoadSave.BIG_CLOUD);
        smallCloud = LoadSave.GetSpriteAtlas(LoadSave.SMALL_CLOUD);
        smallCloudPos = new int[8];
        for (int i = 0; i< smallCloudPos.length; i++)
            smallCloudPos[i] = (int) ( 70 * Game.SCALE ) + rnd.nextInt((int)(150 * Game.SCALE));

    }


    private void initClasses(){
        levelManager = new LevelManager(game);
        enemyManager = new EnemyManager(this);
        player = new Player(200,200, (int) (48 * Game.SCALE), (int) (72 * Game.SCALE));
        player.loadLvlData(levelManager.getCurrentLevel().getLevelData());
        pauseOverlay = new PauseOverlay(this);

    }


    @Override
    public void update() {
        if (!paused){
            levelManager.update();
            player.update();
            enemyManager.update();
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
        g.drawImage(backgroundImg,0,0,Game.GAME_WIDTH,Game.GAME_HEIGHT,null);

        drawCloud(g);
        levelManager.draw(g, xLvOffset);
        player.render(g, xLvOffset);
        enemyManager.draw(g, xLvOffset);

        if (paused) {
            g.setColor(new Color(0,0,0,150));
            g.fillRect(0,0,Game.GAME_WIDTH,Game.GAME_HEIGHT);
            pauseOverlay.draw(g);
        }
    }

    private void drawCloud(Graphics g) {

        for (int i = 0; i < 3 ; i++)
        g.drawImage(bigCloud,0 + i * BIG_CLOUD_WIDTH - (int)(xLvOffset * 0.3),(int) (204*Game.SCALE),BIG_CLOUD_WIDTH,BIG_CLOUD_HIEGHT,null);

        for (int i = 0; i < smallCloudPos.length; i++)
        g.drawImage(smallCloud,SMALL_CLOUD_WIDTH * 4 * i - (int)(xLvOffset * 0.7),smallCloudPos[i] ,SMALL_CLOUD_WIDTH,SMALL_CLOUD_HIEGHT,null);
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
