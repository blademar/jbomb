package jbomb.server.listeners;

import com.jme3.bullet.collision.PhysicsCollisionEvent;
import com.jme3.bullet.collision.PhysicsCollisionListener;
import com.jme3.scene.Spatial;
import jbomb.server.controls.ScorePlayerControl;
import jbomb.server.game.ServerContext;
import org.apache.log4j.Logger;

public class BombCollisionListener implements PhysicsCollisionListener {

    private static final Logger LOGGER = Logger.getLogger(BombCollisionListener.class);
    private Spatial nodeA, nodeB;

    @Override
    public void collision(PhysicsCollisionEvent event) {
        nodeA = event.getNodeA();
        nodeB = event.getNodeB();
        if (nodeA != null && nodeB != null) {
            if (nodeA.getParent() == ServerContext.NODE_PLAYERS
                    && ("bomb".equals(nodeB.getName()) || "wormsExplosion".equals(nodeB.getName()))) {
                nodeA.getControl(ScorePlayerControl.class).sumDamage(1);
                LOGGER.debug("Collision: " + nodeA.getName() + " with " + nodeB.getName());
            } else if (nodeB.getParent() == ServerContext.NODE_PLAYERS
                    && ("bomb".equals(nodeA.getName()) || "wormsExplosion".equals(nodeA.getName()))) {
                nodeB.getControl(ScorePlayerControl.class).sumDamage(1);
                LOGGER.debug("Collision: " + nodeB.getName() + " with " + nodeA.getName());
            }
        }
    }
}
