package jbomb.client.listeners.messages;

import com.jme3.network.Client;
import com.jme3.network.Message;
import com.jme3.network.MessageListener;
import jbomb.common.game.JBombContext;
import jbomb.common.messages.StartGameMessage;
import org.apache.log4j.Logger;

public class StartGameListener implements MessageListener<Client> {

    private static final Logger LOGGER = Logger.getLogger(StartGameListener.class);
    
    @Override
    public void messageReceived(Client source, Message m) {
        JBombContext.STARTED = ((StartGameMessage) m).isStart();
        LOGGER.debug("Starting game...");
    }
    
}
