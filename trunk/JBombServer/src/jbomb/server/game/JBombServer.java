package jbomb.server.game;

import com.jme3.network.Network;
import com.jme3.network.Server;
import java.io.IOException;
import jbomb.common.appstates.AbstractManager;
import jbomb.common.appstates.RunningAppState;
import jbomb.common.game.BaseGame;
import jbomb.server.appstates.RunningServerAppState;
import jbomb.server.appstates.ServerManager;
import jbomb.server.listeners.ClientConnectionListener;

public class JBombServer extends BaseGame {
    
    private Server server;
    private ClientConnectionListener connectionListener = new ClientConnectionListener();

    @Override
    public void simpleInitApp() {
        super.simpleInitApp();
        try {
            server = Network.createServer(6789);
            addMessageListeners();
            server.addConnectionListener(connectionListener);
            server.start();
        } catch (IOException ex) {
            System.out.println("Error al iniciar el servidor: " + ex);
        }
        ServerContext.SERVER = server;
    }
    
    @Override
    public void destroy() {
        if (server != null && server.isRunning())
            server.close();
        super.destroy();
    }

    @Override
    protected RunningAppState createRunningAppState() {
        return new RunningServerAppState();
    }

    @Override
    public void addMessageListeners() {
        
    }

    @Override
    protected AbstractManager<?> createManager() {
        return new ServerManager();
    }
}
