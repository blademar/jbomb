package jbomb.client.messages.task;

import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import jbomb.client.game.ClientContext;
import jbomb.common.game.Player;
import jbomb.common.messages.NewPlayerMessage;

public class NewPlayerTask implements Task<NewPlayerMessage> {

    @Override
    public void doThis(NewPlayerMessage message) {
        ColorRGBA color = message.getColor();
        Vector3f location = message.getLocation();
        Player player = new Player(location, color);
        ClientContext.PLAYERS.put(message.getPosition(), player);
    }
}
