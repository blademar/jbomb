package jbomb.client.listeners;

import com.jme3.input.controls.ActionListener;
import jbomb.client.game.ClientContext;

public class ShootsActionListener implements ActionListener {
    
    @Override
    public void onAction(String name, boolean isPressed, float tpf) {
        if (!isPressed)
            ClientContext.PLAYER.throwBomb();
    }
    
}
