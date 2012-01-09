package jbomb.client.listeners.messages;

import com.jme3.network.Client;
import com.jme3.network.Message;
import jbomb.client.messages.task.ThrowBombTask;
import jbomb.common.messages.ThrowBombMessage;
import org.apache.log4j.Logger;

public class ThrowBombListener extends AbstractClientMessageListener<ThrowBombTask, ThrowBombMessage> {

    private static final Logger LOGGER = Logger.getLogger(ThrowBombListener.class);
    
    @Override
    public void messageReceived(Client source, Message m) {
        ThrowBombMessage message = (ThrowBombMessage) m;
        LOGGER.debug("ThrowBombMessage received");
        LOGGER.debug("idClient: " + message.getIdClient());
        LOGGER.debug("Id of new bomb: " + message.getId());
        doTask(new ThrowBombTask(), message);
    }
    
}
