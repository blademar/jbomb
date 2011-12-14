package jbomb.core.listeners;

import com.jme3.input.controls.ActionListener;
import jbomb.core.game.JBombContext;

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
        else
            JBombContext.JBOMB.getPlayer().jump();
    }
}
