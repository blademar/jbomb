package jbomb.server.game;

import com.jme3.network.Server;
import com.jme3.scene.Node;
import jbomb.common.game.JBombContext;

public class ServerContext extends JBombContext {
    public static Server SERVER;
    public static Node playersNode;
}
