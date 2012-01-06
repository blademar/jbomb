package jbomb.client.listeners.messages;

import com.jme3.network.Client;
import com.jme3.network.Message;
import com.jme3.network.MessageListener;
import jbomb.common.messages.ElevatorMovesMessage;

public class ElevatorMovesListener implements MessageListener<Client> {

    @Override
    public void messageReceived(Client source, Message m) {
        ElevatorMovesMessage message = (ElevatorMovesMessage) m;
        message.applyData();
    }
    
}
