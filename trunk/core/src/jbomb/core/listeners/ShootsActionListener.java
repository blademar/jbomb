package jbomb.core.listeners;

import jbomb.core.game.JBomb;

public class ShootsActionListener extends JBombActionListener {

    public ShootsActionListener(JBomb jbomb) {
        super(jbomb);
    }
    
    @Override
    public void onAction(String name, boolean isPressed, float tpf) {
        if (!isPressed)
            jbomb.makeBomb();
    }
    
}
