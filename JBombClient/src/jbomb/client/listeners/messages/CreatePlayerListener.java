package jbomb.client.listeners.messages;

import com.jme3.network.Client;
import com.jme3.network.Message;
import jbomb.client.messages.task.CreatePlayerTask;
import jbomb.common.messages.CreatePlayerMessage;

public class CreatePlayerListener extends AbstractClientMessageListener<CreatePlayerTask, CreatePlayerMessage> {

    @Override
    public void messageReceived(Client source, Message m) {
        System.out.println("Creating player...");
        doTask(new CreatePlayerTask(), (CreatePlayerMessage) m);
    }
}
