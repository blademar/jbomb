package jbomb.client.listeners.messages;

import com.jme3.network.Client;
import com.jme3.network.Message;
import jbomb.client.messages.task.RemovePlayerTask;
import jbomb.common.messages.RemovePlayerMessage;

public class RemovePlayerListener extends AbstractClientMessageListener<RemovePlayerTask, RemovePlayerMessage> {

    @Override
    public void messageReceived(Client source, Message m) {
        RemovePlayerMessage message = (RemovePlayerMessage) m;
        System.out.println("Removing player #" + message.getPosition());
        doTask(new RemovePlayerTask(), message);
    }
    
}
