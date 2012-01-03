package jbomb.client.listeners.messages;

import com.jme3.network.Client;
import com.jme3.network.Message;
import jbomb.client.messages.task.ThrowBombTask;
import jbomb.common.messages.ThrowBombMessage;

public class ThrowBombListener extends AbstractClientMessageListener<ThrowBombTask, ThrowBombMessage> {

    @Override
    public void messageReceived(Client source, Message m) {
        ThrowBombMessage message = (ThrowBombMessage) m;
        System.out.println("ThrowBombMessage received");
        System.out.println("idClient: " + message.getIdClient());
        System.out.println("Id of new bomb: " + message.getId());
        doTask(new ThrowBombTask(), message);
    }
    
}
