package jbomb.server.listeners;

import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.network.ConnectionListener;
import com.jme3.network.Filters;
import com.jme3.network.HostedConnection;
import com.jme3.network.Server;
import java.util.Iterator;
import jbomb.common.game.JBombContext;
import jbomb.common.game.Player;
import jbomb.common.messages.CreatePlayerMessage;
import jbomb.common.messages.NewPlayerMessage;
import jbomb.common.messages.RemovePlayerMessage;
import jbomb.server.game.ServerContext;
import org.apache.log4j.Logger;

public class ClientConnectionListener implements ConnectionListener {

    private static final Logger LOGGER = Logger.getLogger(ClientConnectionListener.class);
    private Vector3f[] positions = {new Vector3f(0f, 0f, -5f), new Vector3f(-5f, 0f, 0f),
        new Vector3f(0f, 0f, 5f), new Vector3f(5f, 0f, 0f)};
    private ColorRGBA[] colors = {ColorRGBA.Blue, ColorRGBA.Red, ColorRGBA.Yellow, ColorRGBA.Green};
    private byte currentColor = 0;
    private byte currentPosition = 0;

    @Override
    public void connectionAdded(Server server, HostedConnection conn) {
        if (!JBombContext.STARTED) {
            LOGGER.debug("Player #" + conn.getId() + " online.");
            Vector3f loc = null;
            ColorRGBA color = null;
            Iterator<Long> it = JBombContext.MANAGER.keySet().iterator();
            long key = 0;
            Player p = null;
            Object physicObject = null;
            while (it.hasNext()) {
                key = it.next();
                 physicObject = JBombContext.MANAGER.getPhysicObject(key);
                if (physicObject instanceof Player) {
                    p = (Player) physicObject;
                    loc = p.getControl().getPhysicsLocation();
                    color = p.getColor();
                    ServerContext.SERVER.broadcast(Filters.in(conn),
                            new NewPlayerMessage(loc, color, key));
                }
            }
            loc = nextPosition();
            color = nextColor();
            Player player = new Player(loc, color);
            JBombContext.MANAGER.addPhysicObject(conn.getId(), player);
            JBombContext.ROOT_NODE.attachChild(player.getGeometry());
            ServerContext.SERVER.broadcast(Filters.in(conn),
                    new CreatePlayerMessage(loc, color));
            ServerContext.SERVER.broadcast(Filters.notEqualTo(conn),
                    new NewPlayerMessage(loc, color, conn.getId()));
        }
    }

    @Override
    public void connectionRemoved(Server server, HostedConnection conn) {
        int id = conn.getId();
        LOGGER.debug("Player #" + id + " offline");
        Player player = (Player) JBombContext.MANAGER.removePhysicObject(id);
        JBombContext.ROOT_NODE.detachChild(player.getGeometry());
        ServerContext.SERVER.broadcast(Filters.notEqualTo(conn), new RemovePlayerMessage(id));
    }

    private ColorRGBA nextColor() {
        currentColor++;
        if (currentColor == 4) {
            currentColor = 0;
        }
        return colors[currentColor];
    }

    private Vector3f nextPosition() {
        currentPosition++;
        if (currentPosition == 4) {
            currentPosition = 0;
        }
        return positions[currentPosition];
    }
}
