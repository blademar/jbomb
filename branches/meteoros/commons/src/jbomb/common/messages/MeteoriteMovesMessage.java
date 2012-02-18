package jbomb.common.messages;

import com.jme3.math.FastMath;
import com.jme3.math.Vector3f;
import com.jme3.network.serializing.Serializable;
import jbomb.common.game.JBombContext;
import jbomb.common.scene.Meteorite;
import org.apache.log4j.Logger;

@Serializable
public class MeteoriteMovesMessage extends BasePhysicMessage {
    
    private static final Logger LOGGER = Logger.getLogger(MeteoriteMovesMessage.class);
    private Vector3f location = new Vector3f();
    private Vector3f oldPosition = new Vector3f();
    private boolean isFirstInterpolation;

    public MeteoriteMovesMessage() {
        
    }
    
    public MeteoriteMovesMessage(long id, Vector3f localTraslation) {
        super(id);
        this.location = localTraslation;
    }

    @Override
    public void applyData() {
        LOGGER.debug("Meteorite Message.");
        Meteorite m = (Meteorite) JBombContext.MANAGER.getPhysicObject(getId());
        if (m != null)
            m.getGeometry().setLocalTranslation(getLocation());
    }

    @Override
    public void interpolate(float percent) {
        LOGGER.debug("Interpolating meteorite.");
        Meteorite m = (Meteorite) JBombContext.MANAGER.getPhysicObject(getId());
        if (m != null) {
            if (!isFirstInterpolation) {
                oldPosition.set(m.getGeometry().getLocalTranslation());
                isFirstInterpolation = true;
            }
            m.getGeometry().setLocalTranslation(FastMath.interpolateLinear(percent, oldPosition, location));
        }
    }

    public Vector3f getLocation() {
        return location;
    }
}
