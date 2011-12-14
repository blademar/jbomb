package jbomb.core.listeners;

import com.jme3.input.controls.ActionListener;
import jbomb.core.game.JBombContext;
import jbomb.core.game.Player;

public class BombSecondsListener implements ActionListener {

    @Override
    public void onAction(String name, boolean isPressed, float tpf) {
        if (name.equals("one"))
            setSeconds(1.5f);
        else if (name.equals("two"))
            setSeconds(2.5f);
        else
            setSeconds(3.5f);
    }

    private void setSeconds(float seconds) {
        Player player = JBombContext.JBOMB.getPlayer();
        player.setSeconds(seconds);
        for (int i = 0; i < player.getBombs().length; i++) {
            if (player.getBombs()[i] != null) {
                player.getBombs()[i].setTimeForExplosion(seconds);
            }
        }
    }
    
}
