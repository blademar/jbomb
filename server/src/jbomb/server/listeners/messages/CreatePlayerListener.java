package jbomb.server.listeners.messages;

import com.jme3.network.HostedConnection;
import com.jme3.network.Message;
import com.jme3.network.MessageListener;
import jbomb.common.messages.StartGameMessage;
import jbomb.server.game.ServerContext;
import org.apache.log4j.Logger;

public class CreatePlayerListener implements MessageListener<HostedConnection> {
    
    private static final Logger LOGGER = Logger.getLogger(CreatePlayerListener.class);

    @Override
    public void messageReceived(HostedConnection s, Message msg) {
        ServerContext.CONNECTED_PLAYERS++;
        LOGGER.debug("Connected players: " + ServerContext.CONNECTED_PLAYERS);
        if (ServerContext.CONNECTED_PLAYERS == ServerContext.PLAYERS_COUNT) {
            ServerContext.APP.startGame();
            ServerContext.SERVER.broadcast(new StartGameMessage());
            LOGGER.debug("Starting game...");
        }
    }
}
