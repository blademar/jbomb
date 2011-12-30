package jbomb.client.game;

import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import jbomb.common.game.JBombContext;
import jbomb.common.game.Player;
import jbomb.common.sounds.api.Sound;
import jbomb.common.sounds.impl.InstanceSound;
import jbomb.common.weapons.impl.BaseBomb;
import jbomb.common.weapons.impl.GrandBomb;

public class ClientPlayer extends Player {
    
    
    private BaseBomb[] bomb = new BaseBomb[3];
    public float seconds = 1.5f;

    public ClientPlayer(Vector3f location, ColorRGBA color) {
        super(location, color);
    }

    @Override
    public void throwBomb(Vector3f location, long idPhysicObject) {
        for (int i = 0; i < bomb.length; i++) {
            if (bomb[i] != null) {
                JBombContext.MANAGER.addPhysicObject(idPhysicObject, bomb[i]);
                throwSound.play(control.getPhysicsLocation());
                RigidBodyControl bombControl = bomb[i].getSpatial().getControl(RigidBodyControl.class);
                bombControl.setPhysicsLocation(location);
                JBombContext.ROOT_NODE.attachChild(bomb[i].getSpatial());
                JBombContext.PHYSICS_SPACE.add(bombControl);
                bombControl.setLinearVelocity(control.getViewDirection().mult(25f));
                bomb[i] = null;
                break;
            }
        }
    }

    public void setSeconds(float seconds) {
        this.seconds = seconds;
    }

    public void reloadBomb(int position) {
        bomb[position] = new GrandBomb();
        bomb[position].setTimeForExplosion(seconds);
    }

    public BaseBomb[] getBombs() {
        return bomb;
    }
}
