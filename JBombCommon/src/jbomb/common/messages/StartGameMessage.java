package jbomb.common.messages;

import com.jme3.network.AbstractMessage;
import com.jme3.network.serializing.Serializable;

@Serializable
public class StartGameMessage extends AbstractMessage {
    
    private boolean start;
    private byte playersCount;

    public StartGameMessage() {}
    
    public StartGameMessage(boolean start, byte playersCount) {
        this.start = start;
        this.playersCount = playersCount;
    }

    public boolean isStart() {
        return start;
    }

    public byte getPlayersCount() {
        return playersCount;
    }
    
}
