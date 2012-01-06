package jbomb.common.controls;

import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.control.Control;

public class AbstractElevatorControl extends JBombAbstractControl {

    protected float timer = 0, maxY, minY, freezed;
    protected State state;

    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {}
    
    protected enum State {
        MOVING_UP, MOVING_DOWN, WAITING_UP, WAITING_DOWN;
    }
    
    public AbstractElevatorControl() {
        maxY = 1; minY = -1; freezed = 10;
    }
    
    public AbstractElevatorControl(float maxY, float minY, float freezedSeconds, boolean up) {
        this.maxY = maxY;
        this.minY = minY;
        this.freezed = freezedSeconds;
        if (up)
            state = State.MOVING_UP;
        else
            state = State.MOVING_DOWN;
    }
    
    public AbstractElevatorControl(float maxY, float minY, boolean up) {
        this(maxY, minY, -1f, up);
    }
    
    @Override
    protected Control newInstanceOfMe() {
        return new AbstractElevatorControl();
    }

    @Override
    protected void controlUpdate(float tpf) {
        Vector3f currentLocation = spatial.getLocalTranslation();
        if(state == State.MOVING_UP) {
            if (currentLocation.y >= maxY) {
                currentLocation.y = maxY;
                spatial.setLocalTranslation(currentLocation);
                state = State.WAITING_UP;
                timer = 0;
            } else {
                spatial.move(0f, tpf * 3, 0f);
            }
        } else if (state == State.MOVING_DOWN) {
            if (currentLocation.y <= minY) {
                currentLocation.y = minY;
                spatial.setLocalTranslation(currentLocation);
                state = State.WAITING_DOWN;
                timer = 0;
            } else {
                spatial.move(0f, -tpf * 3, 0f);
            }
        }
    }

}
