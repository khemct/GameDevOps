package game;

import entities.Player;
import levels.LevelManager;

import java.awt.*;

public class Game implements Runnable {

    private GameWindow gameWindow;
    private GamePanel gamePanel;
    private Thread gameThread;
    private final int FPS_SET = 120;
    private final int UPS_SET = 200;
    private Player player;
    private LevelManager levelManager;

    public final static int TILE_DEFAULT_SIZE = 32;
    public final static float SCALE = 2f;
    public final static int TILE_IN_WIDTH = 26;
    public final static int TILE_IN_HEIGHT = 14;
    public final static int TILES_SIZE = (int) (TILE_IN_WIDTH * SCALE);
    public final static int GAME_WIDTH = TILES_SIZE * TILE_IN_WIDTH;
    public final static int GAME_HEIGHT = TILES_SIZE * TILE_IN_HEIGHT;


    public Game(){
        initClasses();

        gamePanel = new GamePanel(this);
        gameWindow = new GameWindow(gamePanel);
        gamePanel.requestFocus();

        startGameLoop();


    }

    private void initClasses(){

        player = new Player(200,200);
        levelManager = new LevelManager(this);

    }

    private void startGameLoop(){
        gameThread = new Thread(this);
        gameThread.start();
    }

    public void update(){

        player.update();
        levelManager.update();
    }

    public void render(Graphics g){
        levelManager.draw(g);
        player.render(g);
    }

    @Override
    public void run() {

        double timePerFrame = 1000000000.0/FPS_SET;
        double timePerUpdate = 1000000000.0/UPS_SET;
        long previousTime = System.nanoTime();

        int frames = 0;
        int updates = 0;
        long lastCheck =System.currentTimeMillis();

        double deltaU = 0;
        double deltaF = 0;

        while (true){

            long currentTime = System.nanoTime();

            deltaU += (currentTime - previousTime)/timePerUpdate;
            deltaF += (currentTime - previousTime)/timePerFrame;
            previousTime = currentTime;

            if(deltaU >= 1 ){
                update();
                updates++;
                deltaU--;
            }

            if(deltaF >= 1){
                gamePanel.repaint();
                frames++;
                deltaF--;
            }



            if(System.currentTimeMillis() - lastCheck >= 1000){
                lastCheck = System.currentTimeMillis();
                System.out.println("FPS : "+frames+" | UPS : "+updates);

                frames=0;
                updates=0;

            }

        }

    }

    public void windowLostFocus(){
        player.resetDirBooleans();
    }

    public Player getPlayer(){
        return player;
    }
}