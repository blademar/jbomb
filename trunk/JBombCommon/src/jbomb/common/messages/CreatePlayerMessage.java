package jbomb.common.messages;

import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.network.AbstractMessage;
import com.jme3.network.serializing.Serializable;

@Serializable
public class CreatePlayerMessage extends AbstractMessage {
    
    private Vector3f location;
    private ColorRGBA color;
    
    public CreatePlayerMessage() {}
    
    public CreatePlayerMessage(Vector3f location, ColorRGBA color) {
        this.color = color;
        this.location = location;
    }

    public Vector3f getLocation() {
        return location;
    }

    public void setLocation(Vector3f location) {
        this.location = location;
    }

    public ColorRGBA getColor() {
        return color;
    }

    public void setColor(ColorRGBA color) {
        this.color = color;
    }
}
