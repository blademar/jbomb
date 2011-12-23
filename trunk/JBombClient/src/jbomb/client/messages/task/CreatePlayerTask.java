package jbomb.client.messages.task;

import jbomb.client.game.ClientContext;
import jbomb.common.messages.CreatePlayerMessage;

public class CreatePlayerTask implements Task<CreatePlayerMessage> {

    @Override
    public void doThis(CreatePlayerMessage message) {
        message.applyData();
        ClientContext.PLAYER = message.getPlayer();
        ClientContext.APP.getCam().setLocation(message.getLocation());
    }
}
