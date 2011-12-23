package jbomb.client.listeners.messages;

import com.jme3.network.Client;
import com.jme3.network.Message;
import jbomb.client.messages.task.NewPlayerTask;
import jbomb.common.messages.NewPlayerMessage;

public class NewPlayerListener extends AbstractClientMessageListener<NewPlayerTask, NewPlayerMessage> {

    @Override
    public void messageReceived(Client source, Message m) {
        NewPlayerMessage message = (NewPlayerMessage) m;
        System.out.println("Adding new player # " + message.getId() + "...");
        doTask(new NewPlayerTask(), message);
    }
    
}
