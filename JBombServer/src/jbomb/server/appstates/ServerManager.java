package jbomb.server.appstates;

import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.network.HostedConnection;
import jbomb.common.appstates.AbstractManager;
import jbomb.common.messages.BasePhysicMessage;
import jbomb.common.messages.CoordinateBombMessage;
import jbomb.common.weapons.impl.GrandBomb;
import jbomb.server.game.ServerContext;

public class ServerManager extends AbstractManager<HostedConnection> {

    @Override
    public void doOnUpdate(float tpf, BasePhysicMessage message) {
        message.applyData();
        ServerContext.SERVER.broadcast(message);
        coordinateBombs();
    }
    
    private void coordinateBombs() {
        for (long l : physicsObjects.keySet()) {
            Object o = physicsObjects.get(l);
            if (o instanceof GrandBomb) {
                GrandBomb gb = (GrandBomb) o;
                CoordinateBombMessage cbm = new CoordinateBombMessage(l,
                        gb.getSpatial().getControl(RigidBodyControl.class));
                ServerContext.SERVER.broadcast(cbm);
            }
        }
    }
}
