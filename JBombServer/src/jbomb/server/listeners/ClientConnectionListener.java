package jbomb.server.listeners;

import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.network.ConnectionListener;
import com.jme3.network.Filters;
import com.jme3.network.HostedConnection;
import com.jme3.network.Server;
import jbomb.common.game.Player;
import jbomb.common.messages.CreatePlayerMessage;
import jbomb.common.messages.NewPlayerMessage;
import jbomb.common.messages.RemovePlayerMessage;
import jbomb.server.game.ServerContext;

public class ClientConnectionListener implements ConnectionListener {

    private Vector3f[] positions = {new Vector3f(0f, 0f, -5f), new Vector3f(-5f, 0f, 0f),
        new Vector3f(0f, 0f, 5f), new Vector3f(5f, 0f, 0f)};
    private ColorRGBA[] colors = {ColorRGBA.Blue, ColorRGBA.Red, ColorRGBA.Yellow, ColorRGBA.Green};
    private byte currentColor = 0;
    private byte currentPosition = 0;

    @Override
    public void connectionAdded(Server server, HostedConnection conn) {
        if (!ServerContext.START) {
            System.out.println("Player #" + conn.getId() + " online.");
            Vector3f loc = null;
            ColorRGBA color = null;
            for (Integer i : ServerContext.PLAYERS.keySet()) {
                Player p = ServerContext.PLAYERS.get(i);
                loc = p.getControl().getPhysicsLocation();
                color = p.getColor();
                ServerContext.SERVER.broadcast(Filters.in(conn), 
                    new NewPlayerMessage(loc, color, i));
            }
            loc = nextPosition();
            color = nextColor();
            Player player = new Player(loc, color);
            ServerContext.PLAYERS.put(conn.getId(), player);
            ServerContext.SERVER.broadcast(Filters.in(conn), 
                    new CreatePlayerMessage(loc, color));
            ServerContext.SERVER.broadcast(Filters.notEqualTo(conn), 
                    new NewPlayerMessage(loc, color, conn.getId()));
        }
    }

    @Override
    public void connectionRemoved(Server server, HostedConnection conn) {
        int position = conn.getId();
        System.out.println("Player #" + conn.getId() + " offline");
        Player player = ServerContext.PLAYERS.remove(position);
        ServerContext.ROOT_NODE.detachChild(player.getGeometry());
        ServerContext.SERVER.broadcast(Filters.notEqualTo(conn), new RemovePlayerMessage(position));
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
