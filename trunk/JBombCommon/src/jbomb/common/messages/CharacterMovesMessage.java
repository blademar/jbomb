package jbomb.common.messages;

import com.jme3.bullet.control.CharacterControl;
import com.jme3.math.FastMath;
import com.jme3.math.Vector3f;
import com.jme3.network.serializing.Serializable;
import jbomb.common.game.JBombContext;
import jbomb.common.game.Player;

@Serializable
public class CharacterMovesMessage extends AbstractPhysicMessage {
    
    private Vector3f walkDirection = new Vector3f();
    private Vector3f viewDirection = new Vector3f();
    private Vector3f location = new Vector3f();
    private boolean isFirstInterpolation;
    private Vector3f oldPosition = new Vector3f();

    public CharacterMovesMessage() {}

    public CharacterMovesMessage(long id, CharacterControl character, Vector3f walk) {
        super(id);
        walkDirection.set(walk);
        viewDirection.set(character.getViewDirection());
        location.set(character.getPhysicsLocation());
    }

    @Override
    public void applyData() {
        Player p = (Player) JBombContext.MANAGER.getPhysicObject(getId());
        if (p != null) {
            CharacterControl c = p.getControl();
            c.setPhysicsLocation(location);
            c.setWalkDirection(walkDirection);
            c.setViewDirection(viewDirection);
        }
    }

    @Override
    public void interpolate(float percent) {
        Player p = (Player) JBombContext.MANAGER.getPhysicObject(getId());
        if (p != null) {
            if (!isFirstInterpolation) {
                oldPosition.set(p.getControl().getPhysicsLocation());
                isFirstInterpolation = true;
            }
            p.getControl().setPhysicsLocation(FastMath.interpolateLinear(percent, oldPosition, location));
            p.getControl().setWalkDirection(walkDirection);
            System.out.println("Interpolating...");
        }
    }
}