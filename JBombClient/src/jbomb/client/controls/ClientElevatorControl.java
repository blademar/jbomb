package jbomb.client.controls;

import com.jme3.math.Vector3f;
import jbomb.common.controls.AbstractElevatorControl;

public class ClientElevatorControl extends AbstractElevatorControl {

    public ClientElevatorControl(float maxY, float minY, boolean up) {
        super(maxY, minY, up);
    }

    @Override
    protected void controlUpdate(float tpf) {
        Vector3f currentLocation = spatial.getLocalTranslation();
        if((Boolean) spatial.getUserData("move")) {
            if(state == State.MOVING_UP) {
                if (currentLocation.y >= maxY) {
                    currentLocation.y = maxY;
                    spatial.setLocalTranslation(currentLocation);
                    state = State.MOVING_DOWN;
                    spatial.setUserData("move", false);
                } else {
                    spatial.move(0f, tpf * 3, 0f);
                }
            } else if (state == State.MOVING_DOWN) {
                if (currentLocation.y <= minY) {
                    currentLocation.y = minY;
                    spatial.setLocalTranslation(currentLocation);
                    state = State.MOVING_UP;
                    spatial.setUserData("move", false);
                } else {
                    spatial.move(0f, -tpf * 3, 0f);
                }
            }
        }
        
//        System.out.println((Boolean) spatial.getUserData("move"));
    }
}
