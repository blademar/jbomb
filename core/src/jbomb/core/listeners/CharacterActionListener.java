package jbomb.core.listeners;

import com.jme3.input.controls.ActionListener;
import jbomb.core.game.JBombContext;
import jbomb.core.game.Player;

public class CharacterActionListener implements ActionListener {
        
    @Override
    public void onAction(String name, boolean isPressed, float tpf) {
        if (name.equals("Left"))
            if (isPressed) JBombContext.JBOMB.setLeft(true); else JBombContext.JBOMB.setLeft(false);
        else if (name.equals("Right"))
            if (isPressed) JBombContext.JBOMB.setRight(true); else JBombContext.JBOMB.setRight(false);
        else if (name.equals("Front"))
            if (isPressed) JBombContext.JBOMB.setFront(true); else JBombContext.JBOMB.setFront(false);
        else if (name.equals("Back")) 
            if (isPressed) JBombContext.JBOMB.setBack(true); else JBombContext.JBOMB.setBack(false);
        else if (name.equals("Jump"))
            JBombContext.JBOMB.getPlayer().jump();
        else if (name.equals("one"))
            setSeconds(1.5f);
        else if (name.equals("two"))
            setSeconds(2.5f);
        else if (name.equals("three"))
            setSeconds(3.5f);
    }
    
    private void setSeconds(float seconds) {
        Player player = JBombContext.JBOMB.getPlayer();
        player.setSeconds(seconds);
        for (int i = 0; i < player.getBombs().length; i++)
            if(player.getBombs()[i] != null)
                player.getBombs()[i].setTimeForExplosion(seconds);
    }
}
