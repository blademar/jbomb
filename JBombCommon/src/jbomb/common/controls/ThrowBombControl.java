package jbomb.common.controls;

import com.jme3.bullet.PhysicsSpace;
import com.jme3.bullet.PhysicsTickListener;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.math.Vector3f;
import org.apache.log4j.Logger;

public class ThrowBombControl extends RigidBodyControl implements PhysicsTickListener {

    private boolean throwBomb = false;
    private Vector3f direction;
    private static final Logger LOGGER = Logger.getLogger(ThrowBombControl.class);

    public ThrowBombControl(float mass) {
        super(mass);
    }

    @Override
    public void prePhysicsTick(PhysicsSpace ps, float f) {
        if (throwBomb) {
            LOGGER.debug("Aplicando fuerza a bomba");
            setLinearVelocity(direction);
            throwBomb = false;
        }
    }

    @Override
    public void physicsTick(PhysicsSpace ps, float f) {}
    
    public void setDirection(Vector3f direction) {
        this.direction = direction;
        throwBomb = true;
    }

    @Override
    public void setPhysicsSpace(PhysicsSpace space) {
        super.setPhysicsSpace(space);
        space.addTickListener(this);
    }
}
