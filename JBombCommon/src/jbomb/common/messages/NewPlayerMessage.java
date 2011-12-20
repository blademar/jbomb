package jbomb.common.messages;

import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.network.serializing.Serializable;

@Serializable
public class NewPlayerMessage extends CreatePlayerMessage {
    
    private int position;
    
    public NewPlayerMessage() {}
    
    public NewPlayerMessage(Vector3f location, ColorRGBA color, int position) {
        super(location, color);
        this.position = position;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
