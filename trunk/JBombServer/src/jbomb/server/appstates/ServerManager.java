package jbomb.server.appstates;

import com.jme3.network.HostedConnection;
import jbomb.common.appstates.BaseManager;
import jbomb.common.messages.BasePhysicMessage;
import jbomb.server.game.ServerContext;

public class ServerManager extends BaseManager<HostedConnection> {

    @Override
    public void doOnUpdate(float tpf, BasePhysicMessage message) {
        message.applyData();
        ServerContext.SERVER.broadcast(message);
    }
}
