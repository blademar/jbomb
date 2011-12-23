package jbomb.client.messages.task;

import jbomb.client.game.ClientContext;
import jbomb.common.game.JBombContext;
import jbomb.common.messages.StartGameMessage;

public class StartGameTask implements Task<StartGameMessage> {

    @Override
    public void doThis(StartGameMessage message) {
        JBombContext.STARTED = message.isStart();
        ClientContext.APP.getCam().setLocation(ClientContext.PLAYER.getControl().getPhysicsLocation());
    }
    
}
