package jbomb.common.messages;

import com.jme3.network.serializing.Serializable;
import com.jme3.scene.Geometry;
import jbomb.common.game.JBombContext;

@Serializable
public class ElevatorMovesMessage extends BasePhysicMessage {
    
    public ElevatorMovesMessage() {}
    
    public ElevatorMovesMessage(long id) {
        super(id);
    }

    @Override
    public void applyData() {
        Geometry geometry = (Geometry) JBombContext.MANAGER.getPhysicObject(getId());
        geometry.setUserData("move", true);
    }
    
}
