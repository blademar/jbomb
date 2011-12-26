package jbomb.client.listeners.messages;

import com.jme3.network.Client;
import com.jme3.network.Message;
import com.jme3.network.MessageListener;
import jbomb.common.game.JBombContext;
import jbomb.common.messages.StartGameMessage;

public class StartGameListener implements MessageListener<Client> {

    @Override
    public void messageReceived(Client source, Message m) {
        JBombContext.STARTED = ((StartGameMessage) m).isStart();
        System.out.println("Starting game...");
    }
    
}
