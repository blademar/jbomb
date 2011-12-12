package jbomb.core.controls;

import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.control.Control;

public class ElevatorControl extends JBombAbstractControl {

    private float timer = 0, upY, downY, freezed;
    private boolean up, down;
    
    public ElevatorControl() {
        upY = 1; downY = -1; freezed = 10; 
        up = true; down = false;
    }
    
    public ElevatorControl(float upY, float downY, float freezedSeconds, boolean up) {
        this.upY = upY;
        this.downY = downY;
        this.freezed = freezedSeconds;
        this.up = up;
        this.down = !up;
    }
    
    @Override
    protected Control newInstanceOfMe() {
        return new ElevatorControl();
    }

    @Override
    protected void controlUpdate(float tpf) {
        Vector3f currentLocation = spatial.getControl(RigidBodyControl.class).getPhysicsLocation();
        
        if(up && !down) {
            if(currentLocation.y >= upY) {
                up = !up;
                currentLocation.y = upY;
                spatial.getControl(RigidBodyControl.class).setPhysicsLocation(currentLocation);
            } else {
                currentLocation.y += tpf * 3.2;
                spatial.getControl(RigidBodyControl.class).setPhysicsLocation(currentLocation); 
            }     
        } else if(!up && down) {
            if(currentLocation.y <= downY) {
                down = !down;
                currentLocation.y = downY;
                spatial.getControl(RigidBodyControl.class).setPhysicsLocation(currentLocation);
            } else {
                currentLocation.y -= tpf * 3.2;
                spatial.getControl(RigidBodyControl.class).setPhysicsLocation(currentLocation);
            }
        } else {
            if(!up && !down)
                if(timer >= freezed) {
                    timer = 0;
                    if(currentLocation.y >= upY)
                        down = !down;
                    else
                        up = !up;
                } else
                    timer += tpf;
        }
    }

    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {    }
    
    public void setDirection(boolean up) {
        this.up = up;
        this.down = !up;
    } 
}
