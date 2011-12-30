package jbomb.server.game;

import com.jme3.network.HostedConnection;
import com.jme3.network.Network;
import com.jme3.network.Server;
import java.io.IOException;
import jbomb.common.appstates.AbstractManager;
import jbomb.common.appstates.Manager;
import jbomb.common.appstates.RunningAppState;
import jbomb.common.game.BaseGame;
import jbomb.common.game.JBombContext;
import jbomb.common.messages.CharacterMovesMessage;
import jbomb.common.messages.ThrowBombMessage;
import jbomb.server.appstates.RunningServerAppState;
import jbomb.server.appstates.ServerManager;
import jbomb.server.listeners.ClientConnectionListener;
import jbomb.server.listeners.messages.ThrowBombListener;

public class JBombServer extends BaseGame {
    
    private Server server;
    private ClientConnectionListener connectionListener = new ClientConnectionListener();
    private ThrowBombListener throwBombListener = new ThrowBombListener();
    
    public JBombServer(String playersNumber) {
        JBombContext.PLAYERS_COUNT = setPlayersCount(playersNumber);
        System.out.println("PlayersCount: " + JBombContext.PLAYERS_COUNT);
    }

    @Override
    public void simpleInitApp() {
        super.simpleInitApp();
        JBombContext.MANAGER.getRepository().reserveID(JBombContext.PLAYERS_COUNT);
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
        Manager<HostedConnection> m = (Manager<HostedConnection>) getManager();
        server.addMessageListener(m, CharacterMovesMessage.class);
        server.addMessageListener(throwBombListener, ThrowBombMessage.class);
    }

    @Override
    protected AbstractManager<?> createManager() {
        return new ServerManager();
    }

    private byte setPlayersCount(String playersNumber) {
        if ("two".equals(playersNumber))
                return 2;
        else if ("three".equals(playersNumber))
            return 3;
        else
            return 4;
    }
}
