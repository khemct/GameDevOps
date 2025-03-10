package game;

import inputs.KeyBoardInputs;
import inputs.MouseInputs;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import static game.Game.GAME_HEIGHT;
import static game.Game.GAME_WIDTH;
import static utilz.Constants.PlayerConstants.*;
import static utilz.Constants.Directions.*;


public class GamePanel extends JPanel {

    private MouseInputs mouseInputs;
    private Game game;

    public GamePanel(Game game){
        mouseInputs = new MouseInputs(this);
        this.game = game;

        setPanelSize();
        addKeyListener(new KeyBoardInputs(this));
        addMouseListener(mouseInputs);
        addMouseMotionListener(mouseInputs);
    }


    private void setPanelSize() {
        Dimension size = new Dimension(GAME_WIDTH,GAME_HEIGHT);
        setMaximumSize(size);
        setMinimumSize(size);
        setPreferredSize(size);
        System.out.println("size : "+GAME_WIDTH+ " : " +GAME_HEIGHT);
    }

    public void updateGame(){

    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        game.render(g);
    }

    public Game getGame(){
        return game;
    }

}