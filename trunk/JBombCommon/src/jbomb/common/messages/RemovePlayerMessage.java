package jbomb.common.messages;

import com.jme3.network.AbstractMessage;
import com.jme3.network.serializing.Serializable;

@Serializable
public class RemovePlayerMessage extends AbstractMessage {
    
    private int position;
    
    public RemovePlayerMessage() {}
    
    public RemovePlayerMessage(int position) {
        this.position = position;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
