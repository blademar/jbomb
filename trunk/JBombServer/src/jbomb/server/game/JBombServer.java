package jbomb.server.game;

import com.jme3.network.HostedConnection;
import com.jme3.network.Network;
import com.jme3.network.Server;
import com.jme3.scene.control.Control;
import java.io.IOException;
import java.net.URL;
import jbomb.common.appstates.BaseManager;
import jbomb.common.appstates.Manager;
import jbomb.common.appstates.RunningAppState;
import jbomb.server.controls.ElevatorControl;
import jbomb.common.game.BaseGame;
import jbomb.common.game.JBombContext;
import jbomb.common.messages.CharacterMovesMessage;
import jbomb.common.messages.ThrowBombMessage;
import jbomb.server.appstates.RunningServerAppState;
import jbomb.server.appstates.ServerManager;
import jbomb.server.listeners.ClientConnectionListener;
import jbomb.server.listeners.messages.ThrowBombListener;
import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

public class JBombServer extends BaseGame {
    
    private static final Logger LOGGER = Logger.getLogger(JBombServer.class);
    private Server server;
    private ClientConnectionListener connectionListener = new ClientConnectionListener();
    private ThrowBombListener throwBombListener = new ThrowBombListener();
    
    public JBombServer(String playersNumber) {
        JBombContext.PLAYERS_COUNT = setPlayersCount(playersNumber);
        LOGGER.debug("PlayersCount: " + JBombContext.PLAYERS_COUNT);
    }

    @Override
    public void simpleInitApp() {
        super.simpleInitApp();
        try {
            server = Network.createServer(6789);
            addMessageListeners();
            server.addConnectionListener(connectionListener);
            server.start();
        } catch (IOException ex) {
            LOGGER.error("Error al iniciar el servidor", ex);
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
    protected BaseManager<?> createManager() {
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

    @Override
    public void loadLog4jConfig() {
        URL urlConfig = BaseGame.class.getResource("/jbomb/server/config/log4j.xml");
        DOMConfigurator.configure(urlConfig);
    }

    @Override
    protected boolean getElevatorServerControlled() {
        return true;
    }

    @Override
    public Control createElevatorControl(float upY, float downY, float freezedSeconds, boolean up) {
        return new ElevatorControl(upY, downY, freezedSeconds, up);
    }
}
