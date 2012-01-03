package jbomb.server.listeners.messages;

import com.jme3.math.Vector3f;
import com.jme3.network.HostedConnection;
import com.jme3.network.Message;
import com.jme3.network.MessageListener;
import jbomb.common.game.JBombContext;
import jbomb.common.game.Player;
import jbomb.common.messages.ThrowBombMessage;
import jbomb.server.controls.BaseBombControl;
import jbomb.server.game.ServerContext;

public class ThrowBombListener implements MessageListener<HostedConnection> {

    @Override
    public void messageReceived(HostedConnection source, Message m) {
        System.out.println("ThrowBombMessage received");
        long idPhysicObject = JBombContext.MANAGER.getRepository().nextFree();
        System.out.println("Id of new bomb: " + idPhysicObject);
        Player player = (Player) JBombContext.MANAGER.getPhysicObject(source.getId());
        Vector3f bombLocation = player.getControl().getPhysicsLocation().add(player.getControl().getViewDirection().normalize().mult(1.2f));
        ServerContext.SERVER.broadcast(new ThrowBombMessage(idPhysicObject, source.getId(), bombLocation));
        player.throwBomb(bombLocation, idPhysicObject, new BaseBombControl());
    }
}
