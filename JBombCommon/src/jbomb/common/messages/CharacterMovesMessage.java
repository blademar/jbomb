package jbomb.common.messages;

import com.jme3.math.Vector3f;
import com.jme3.network.AbstractMessage;
import com.jme3.network.serializing.Serializable;

@Serializable
public class CharacterMovesMessage extends AbstractMessage {
    
    private Vector3f walkDirection;
    
    public CharacterMovesMessage() {
        super(false);
    }
    
    public CharacterMovesMessage(Vector3f walkDirection) {
        this.walkDirection = walkDirection;
    }

    public Vector3f getWalkDirection() {
        return walkDirection;
    }

    public void setWalkDirection(Vector3f walkDirection) {
        this.walkDirection = walkDirection;
    }
}
