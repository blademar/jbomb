package jbomb.common.messages;

import com.jme3.bullet.control.CharacterControl;
import com.jme3.math.Vector3f;
import com.jme3.network.serializing.Serializable;

@Serializable
public class CharacterMovesMessage extends AbstractPhysicMessage {
    
    public Vector3f location = new Vector3f();
    public Vector3f walkDirection = new Vector3f();
    public Vector3f viewDirection = new Vector3f();
    
    public CharacterMovesMessage() {}

    public CharacterMovesMessage(long id, CharacterControl character) {
        character.getPhysicsLocation(location);
        this.walkDirection.set(character.getWalkDirection());
        this.viewDirection.set(character.getViewDirection());
    }

    public void readData(CharacterControl character) {
        character.getPhysicsLocation(location);
        this.walkDirection.set(character.getWalkDirection());
        this.viewDirection.set(character.getViewDirection());
    }

    @Override
    public void applyData() {
    }
}
