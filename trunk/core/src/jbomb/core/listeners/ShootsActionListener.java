package jbomb.core.listeners;

import com.jme3.input.controls.ActionListener;
import jbomb.core.game.JBombContext;

public class ShootsActionListener implements ActionListener {
    
    @Override
    public void onAction(String name, boolean isPressed, float tpf) {
        if (!isPressed)
            JBombContext.JBOMB.getPlayer().throwBomb();
    }
    
}
