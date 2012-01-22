package jbomb.client.listeners.messages;

import com.jme3.network.Client;
import com.jme3.network.Message;
import com.jme3.network.MessageListener;
import jbomb.client.game.ClientContext;
import jbomb.common.messages.DamageMessage;
import org.apache.log4j.Logger;

public class DamageMessageListener implements MessageListener<Client> {

    private static final Logger LOGGER = Logger.getLogger(DamageMessageListener.class);

    @Override
    public void messageReceived(Client source, Message m) {
        DamageMessage damageMessage = (DamageMessage) m;
        if (damageMessage.getId() == ClientContext.CLIENT.getId())
            LOGGER.debug("Damage received: " + damageMessage.getDamage());
    }
}
