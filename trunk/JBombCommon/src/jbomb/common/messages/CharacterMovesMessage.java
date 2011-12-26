package jbomb.common.messages;

import com.jme3.bullet.control.CharacterControl;
import com.jme3.math.Vector3f;
import com.jme3.network.serializing.Serializable;
import jbomb.common.game.JBombContext;
import jbomb.common.game.Player;

@Serializable
public class CharacterMovesMessage extends AbstractPhysicMessage {
    
    private Vector3f walkDirection = new Vector3f();
    private Vector3f viewDirection = new Vector3f();
    private Vector3f location = new Vector3f();;
    private float maxTime = 1f / JBombContext.MESSAGES_PER_SECOND;
    private float sum;

    public CharacterMovesMessage() {}

    public CharacterMovesMessage(long id, CharacterControl character) {
        super(id);
        walkDirection.set(character.getWalkDirection());
        viewDirection.set(character.getViewDirection());
        location.set(character.getPhysicsLocation());
    }

    @Override
    public void applyData() {
        Player p = (Player) JBombContext.MANAGER.getPhysicObject(getId());
        if (p != null) {
            CharacterControl c = p.getControl();
            c.setPhysicsLocation(location);
            c.setWalkDirection(getWalkDirection());
            c.setViewDirection(getViewDirection());
        }
    }

    public Vector3f getWalkDirection() {
        return walkDirection;
    }

    public Vector3f getViewDirection() {
        return viewDirection;
    }

    @Override
    public void doPrediction(float tpf) {
        Player p = (Player) JBombContext.MANAGER.getPhysicObject(getId());
        Vector3f step = new Vector3f(walkDirection);
        if (p != null) {
            sum += tpf;
            System.out.println("sum: " + (sum / maxTime));
            step.multLocal(sum / maxTime);
            p.getControl().setWalkDirection(step);
        }
    }
}
