package jbomb.client.listeners.messages;

import com.jme3.network.Client;
import com.jme3.network.Message;
import jbomb.client.messages.task.StartGameTask;
import jbomb.common.messages.StartGameMessage;

public class StartGameListener extends AbstractClientMessageListener<StartGameTask, StartGameMessage> {

    @Override
    public void messageReceived(Client source, Message m) {
        System.out.println("Starting game...");
        doTask(new StartGameTask(), (StartGameMessage) m);
    }
}
