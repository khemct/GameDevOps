package inputs;

import game.GamePanel;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class MouseInputs implements MouseListener, MouseMotionListener {

    private GamePanel gamePanel;
    public MouseInputs(GamePanel gamePanel){
        this.gamePanel = gamePanel;

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        System.out.println("mClick");

    }

    @Override
    public void mousePressed(MouseEvent e) {
        System.out.println("mPress");
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        System.out.println("mRelease");
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        System.out.println("mEnter");
    }

    @Override
    public void mouseExited(MouseEvent e) {
        System.out.println("mExit");
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        System.out.println("mDrag");
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        gamePanel.setRectPos(e.getX(),e.getY());
        System.out.println("mMove");
    }
}
