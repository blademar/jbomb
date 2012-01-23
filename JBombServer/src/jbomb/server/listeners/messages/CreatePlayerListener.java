package jbomb.server.listeners.messages;

import com.jme3.network.HostedConnection;
import com.jme3.network.Message;
import com.jme3.network.MessageListener;
import jbomb.common.game.JBombContext;
import jbomb.common.messages.StartGameMessage;
import jbomb.server.game.ServerContext;
import org.apache.log4j.Logger;

public class CreatePlayerListener implements MessageListener<HostedConnection> {

    private byte connectedPlayers = 0;
    private static final Logger LOGGER = Logger.getLogger(CreatePlayerListener.class);

    @Override
    public void messageReceived(HostedConnection s, Message msg) {
        connectedPlayers++;
        LOGGER.debug("Connected players: " + connectedPlayers);
        if (connectedPlayers == ServerContext.PLAYERS_COUNT) {
            JBombContext.STARTED = true;
            ServerContext.SERVER.broadcast(new StartGameMessage(true, ServerContext.PLAYERS_COUNT));
            LOGGER.debug("Starting game...");
        }
    }
}
