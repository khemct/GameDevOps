package inputs;

import game.GamePanel;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyBoardInputs implements KeyListener {

    private GamePanel gamePanel;
    public KeyBoardInputs(GamePanel gamePanel){
        this.gamePanel = gamePanel;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()){
            case KeyEvent.VK_W:
                gamePanel.changeYDelta(-5);
                System.out.println("W");
                break;
            case KeyEvent.VK_A:
                gamePanel.changeXDelta(-5);
                System.out.println("A");
                break;
            case KeyEvent.VK_S:
                gamePanel.changeYDelta(+5);
                System.out.println("S");
                break;
            case KeyEvent.VK_D:
                gamePanel.changeXDelta(+5);
                System.out.println("D");
                break;
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
