package jbomb.core.listeners;

import jbomb.core.game.JBomb;

public class CharacterActionListener extends JBombActionListener {
    
    public CharacterActionListener(JBomb jbomb) {
        super(jbomb);
    }

    @Override
    public void onAction(String name, boolean isPressed, float tpf) {
        if (name.equals("Left"))
            if (isPressed) jbomb.setLeft(true); else jbomb.setLeft(false);
        else if (name.equals("Right"))
            if (isPressed) jbomb.setRight(true); else jbomb.setRight(false);
        else if (name.equals("Front"))
            if (isPressed) jbomb.setFront(true); else jbomb.setFront(false);
        else if (name.equals("Back")) 
            if (isPressed) jbomb.setBack(true); else jbomb.setBack(false);
        else if (name.equals("Jump"))
            jbomb.getPlayer().jump();
    }
    
}
