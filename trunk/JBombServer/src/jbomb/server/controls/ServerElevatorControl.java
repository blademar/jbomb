package jbomb.server.controls;

import jbomb.common.controls.AbstractElevatorControl;
import jbomb.common.messages.ElevatorMovesMessage;
import jbomb.server.game.ServerContext;

public class ServerElevatorControl extends AbstractElevatorControl {

    public ServerElevatorControl(float maxY, float minY, float freezedSeconds, boolean up) {
        super(maxY, minY, freezedSeconds, up);
    }
    
    @Override
    protected void controlUpdate(float tpf) {
        super.controlUpdate(tpf);
        if (state == State.WAITING_UP) {
            if (timer >= freezed) {
                timer = 0;
                state = State.MOVING_DOWN;
                ServerContext.SERVER.broadcast(new ElevatorMovesMessage((Long) spatial.getUserData("id")));
            } else {
                timer += tpf;
            }
        } else if(state == State.WAITING_DOWN) {
            if (timer >= freezed) {
                timer = 0;
                state = State.MOVING_UP;
                ServerContext.SERVER.broadcast(new ElevatorMovesMessage((Long) spatial.getUserData("id")));
            } else {
                timer += tpf;
            }
        }
        
//        System.out.println(spatial.getLocalTranslation());
//        System.out.println(state);
    }
}
