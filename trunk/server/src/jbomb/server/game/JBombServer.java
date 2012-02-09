package jbomb.server.game;

import com.jme3.network.HostedConnection;
import com.jme3.network.Network;
import com.jme3.network.Server;
import com.jme3.scene.Node;
import com.jme3.scene.control.Control;
import java.io.IOException;
import java.net.URL;
import jbomb.common.appstates.AbstractManager;
import jbomb.common.game.BaseGame;
import jbomb.common.game.JBombContext;
import jbomb.common.messages.CharacterMovesMessage;
import jbomb.common.messages.CreatePlayerMessage;
import jbomb.common.messages.ThrowBombMessage;
import jbomb.server.appstates.CounterAppState;
import jbomb.server.appstates.RunningServerAppState;
import jbomb.server.appstates.ServerManager;
import jbomb.server.controls.ElevatorControl;
import jbomb.server.listeners.BombCollisionListener;
import jbomb.server.listeners.ClientConnectionListener;
import jbomb.server.listeners.messages.CreatePlayerListener;
import jbomb.server.listeners.messages.ThrowBombListener;
import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

public class JBombServer extends BaseGame {
    
    private static final Logger LOGGER = Logger.getLogger(JBombServer.class);
    private Server server;
    private ClientConnectionListener connectionListener = new ClientConnectionListener();
    private ThrowBombListener throwBombListener = new ThrowBombListener();
    private CreatePlayerListener createPlayerListener = new CreatePlayerListener();
    private CounterAppState counterAppState = new CounterAppState();
    
    public JBombServer(String playersNumber) {
        ServerContext.PLAYERS_COUNT = setPlayersCount(playersNumber);
        LOGGER.debug("PlayersCount: " + ServerContext.PLAYERS_COUNT);
    }

    @Override
    public void simpleInitApp() {
        super.simpleInitApp();
        guiNode.detachAllChildren();
        JBombContext.PHYSICS_SPACE.addCollisionListener(new BombCollisionListener());
        ServerContext.NODE_PLAYERS = new Node("Node_players");
        JBombContext.ROOT_NODE.attachChild(ServerContext.NODE_PLAYERS);
        try {
            server = Network.createServer(6789);
            addMessageListeners();
            server.addConnectionListener(connectionListener);
            server.start();
        } catch (IOException ex) {
            LOGGER.error("Error al iniciar el servidor", ex);
        }
        ServerContext.APP = this;
        ServerContext.SERVER = server;
    }
    
    @Override
    public void destroy() {
        if (server != null && server.isRunning())
            server.close();
        super.destroy();
    }

    @Override
    protected void initStateManager() {
        super.initStateManager();
    }
    
    public void initCounter() {
        if (!stateManager.hasState(counterAppState))
            stateManager.attach(counterAppState);
        else
            counterAppState.setEnabled(true);
    }
    
    public void startGame() {
        stateManager.attach(new RunningServerAppState());
        ServerContext.ROUND_FINISHED = false;
    }
    
    public void resetGame() {
        stateManager.getState(RunningServerAppState.class).setEnabled(false);
    }
    
    public boolean isRunning() {
        RunningServerAppState state = stateManager.getState(RunningServerAppState.class);
        if (state != null)
            return state.isEnabled();
        else 
            return false;
    }

    @Override
    public void addMessageListeners() {
        AbstractManager<HostedConnection> m = (AbstractManager<HostedConnection>) getManager();
        server.addMessageListener(m, CharacterMovesMessage.class);
        server.addMessageListener(throwBombListener, ThrowBombMessage.class);
        server.addMessageListener(createPlayerListener, CreatePlayerMessage.class);
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
