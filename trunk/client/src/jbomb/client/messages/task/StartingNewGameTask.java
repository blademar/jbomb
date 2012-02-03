package jbomb.client.messages.task;

import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;
import jbomb.client.game.ClientContext;
import jbomb.common.game.JBombContext;
import jbomb.common.messages.StartingNewGameMessage;

public class StartingNewGameTask implements Task<StartingNewGameMessage> {

    @Override
    public void doThis(StartingNewGameMessage message) {
        ClientContext.APP.resetGame();
        for (Spatial s : JBombContext.NODE_ELEVATORS.getChildren()) {
            s.setLocalTranslation((Vector3f) s.getUserData("initialLocation"));
        }
        ClientContext.PLAYER.setInitialPosition(message.getInitialPosition());
    }
}
