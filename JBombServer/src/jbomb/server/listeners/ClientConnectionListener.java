package jbomb.server.listeners;

import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.network.ConnectionListener;
import com.jme3.network.Filters;
import com.jme3.network.HostedConnection;
import com.jme3.network.Server;
import java.util.Iterator;
import java.util.concurrent.Callable;
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
    public void connectionAdded(Server server, final HostedConnection conn) {
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
                    loc = p.getGeometry().getLocalTranslation();
                    color = p.getColor();
                    ServerContext.SERVER.broadcast(Filters.in(conn),
                            new NewPlayerMessage(loc, color, key));
                }
            }
            final Vector3f loc2 = nextPosition();
            final ColorRGBA color2 = nextColor();
            JBombContext.BASE_GAME.enqueue(new Callable<Void>() {

                @Override
                public Void call() throws Exception {
                    
                    Player player = new Player(loc2, color2);
                    JBombContext.MANAGER.addPhysicObject(conn.getId(), player);
                    ServerContext.playersNode.attachChild(player.getGeometry());
                    return null;
                }
            });
            ServerContext.SERVER.broadcast(Filters.in(conn),
                    new CreatePlayerMessage(loc2, color2));
            ServerContext.SERVER.broadcast(Filters.notEqualTo(conn),
                    new NewPlayerMessage(loc2, color2, conn.getId()));
        }
    }

    @Override
    public void connectionRemoved(Server server, HostedConnection conn) {
        int id = conn.getId();
        LOGGER.debug("Player #" + id + " offline");
        Player player = (Player) JBombContext.MANAGER.removePhysicObject(id);
        ServerContext.playersNode.detachChild(player.getGeometry());
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
