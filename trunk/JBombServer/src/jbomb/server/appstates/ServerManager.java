package jbomb.server.appstates;

import com.jme3.network.HostedConnection;
import jbomb.common.appstates.AbstractManager;
import jbomb.common.messages.AbstractPhysicMessage;
import jbomb.server.game.ServerContext;

public class ServerManager extends AbstractManager<HostedConnection> {

    @Override
    public void doOnUpdate(float tpf, AbstractPhysicMessage message) {
        modifyScene(message);
        ServerContext.SERVER.broadcast(message);
    }
    
}
