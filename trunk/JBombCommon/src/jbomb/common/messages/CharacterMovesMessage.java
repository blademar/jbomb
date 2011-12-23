package jbomb.common.messages;

import com.jme3.bullet.control.CharacterControl;
import com.jme3.math.Vector3f;
import com.jme3.network.serializing.Serializable;
import jbomb.common.game.JBombContext;
import jbomb.common.game.Player;

@Serializable
public class CharacterMovesMessage extends AbstractPhysicMessage {

    public Vector3f location = new Vector3f();
    public Vector3f walkDirection = new Vector3f();
    public Vector3f viewDirection = new Vector3f();

    public CharacterMovesMessage() {
    }

    public CharacterMovesMessage(long id, CharacterControl character) {
        super(id);
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
        Player p = (Player) JBombContext.MANAGER.getPhysicObject(getId());
        if (p != null) {
            System.out.println("Moving player #" + getId());
            CharacterControl c = p.getControl();
            c.setPhysicsLocation(location);
            c.setWalkDirection(walkDirection);
            c.setViewDirection(viewDirection);
        }

    }
}
