package jbomb.client.messages.task;

import jbomb.client.game.ClientContext;
import jbomb.common.game.Player;
import jbomb.common.messages.RemovePlayerMessage;

public class RemovePlayerTask implements Task<RemovePlayerMessage> {

    @Override
    public void doThis(RemovePlayerMessage message) {
        Player player = ClientContext.PLAYERS.remove(message.getPosition());
        ClientContext.ROOT_NODE.detachChild(player.getGeometry());
    }
    
}
