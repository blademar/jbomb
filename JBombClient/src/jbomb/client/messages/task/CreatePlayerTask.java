package jbomb.client.messages.task;

import jbomb.client.game.ClientContext;
import jbomb.client.game.ClientPlayer;
import jbomb.common.game.JBombContext;
import jbomb.common.messages.CreatePlayerMessage;

public class CreatePlayerTask implements Task<CreatePlayerMessage> {

    @Override
    public void doThis(CreatePlayerMessage message) {
        ClientPlayer player = new ClientPlayer(message.getLocation(), message.getColor());
        JBombContext.ROOT_NODE.attachChild(player.getGeometry());
        ClientContext.PLAYER = player;
        ClientContext.APP.getCam().setLocation(message.getLocation());
    }
}
