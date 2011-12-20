package jbomb.common.messages;

import com.jme3.network.AbstractMessage;
import com.jme3.network.serializing.Serializable;

@Serializable
public class StartGameMessage extends AbstractMessage {
    
    private boolean start;
    
    public StartGameMessage() {}
    
    public StartGameMessage(boolean start) {
        this.start = start;
    }

    public boolean isStart() {
        return start;
    }

    public void setStart(boolean start) {
        this.start = start;
    }
}
