package jbomb.client.messages.task;

import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import jbomb.client.game.ClientContext;
import jbomb.client.game.ClientPlayer;
import jbomb.common.messages.CreatePlayerMessage;

public class CreatePlayerTask implements Task<CreatePlayerMessage> {

    @Override
    public void doThis(CreatePlayerMessage message) {
        ColorRGBA color = message.getColor();
        Vector3f location = message.getLocation();
        ClientPlayer player = new ClientPlayer(location, color);
        ClientContext.APP.setPlayer(player);
        ClientContext.PLAYER = player;
    }
}
