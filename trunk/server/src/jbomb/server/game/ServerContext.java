package jbomb.server.game;

import com.jme3.network.Server;
import com.jme3.scene.Node;
import jbomb.common.game.JBombContext;

public class ServerContext extends JBombContext {

    private ServerContext() {}
    
    public static Server SERVER;
    public static Node NODE_PLAYERS;
    public static byte PLAYERS_COUNT;
    public static JBombServer APP;
    public static Node NODE_ELEVATORS;
}
