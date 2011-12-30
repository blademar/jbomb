package jbomb.client.listeners;

import com.jme3.input.controls.ActionListener;
import jbomb.client.game.ClientContext;
import jbomb.common.game.JBombContext;

public class BombSecondsListener implements ActionListener {

    @Override
    public void onAction(String name, boolean isPressed, float tpf) {
        if (name.equals("one")) {
            setSeconds(1f);
            changeImage(1);
        } else if (name.equals("two")) {
            setSeconds(2f);
            changeImage(2);
        } else {
            setSeconds(3f);
            changeImage(3);
        }
    }

    private void setSeconds(float seconds) {
//        ClientPlayer player = ClientContext.PLAYER;
//        player.setSeconds(seconds);
//        for (int i = 0; i < player.getBombs().length; i++) {
//            if (player.getBombs()[i] != null) {
//                player.getBombs()[i].setTimeForExplosion(seconds);
//            }
//        }
    }
    
    private void changeImage(int value) {
        ClientContext.APP.getBombsSecondsPictures().setImage(JBombContext.ASSET_MANAGER, 
                "interfaces/pictures/glass_numbers_" + value + ".png", true);
    }
}
