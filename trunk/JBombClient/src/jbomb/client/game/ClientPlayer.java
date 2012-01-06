package jbomb.client.game;

import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import jbomb.common.game.JBombContext;
import jbomb.common.game.Player;
import jbomb.common.weapons.impl.BaseBomb;
import jbomb.common.weapons.impl.GrandBomb;

public class ClientPlayer extends Player {
    
    
    private BaseBomb[] bomb = new BaseBomb[3];
    private byte seconds = 1;
    private volatile boolean hasBombs = true; 

    public ClientPlayer(Vector3f location, ColorRGBA color) {
        super(location, color);
        for(int i = 0; i < bomb.length; i++)
            reloadBomb(i);
    }
    
    public void throwBomb(Vector3f location, long idPhysicObject) {
        for (int i = 0; i < bomb.length; i++) {
            if (bomb[i] != null) {
                JBombContext.MANAGER.addPhysicObject(idPhysicObject, bomb[i]);
                bomb[i].getSpatial().setUserData("id", idPhysicObject);
                throwSound.play(control.getPhysicsLocation());
                RigidBodyControl bombControl = bomb[i].getSpatial().getControl(RigidBodyControl.class);
                bombControl.setPhysicsLocation(location);
                JBombContext.ROOT_NODE.attachChild(bomb[i].getSpatial());
                JBombContext.PHYSICS_SPACE.add(bombControl);
                bombControl.setLinearVelocity(control.getViewDirection().mult(25f));
                bomb[i] = null;
                if (bomb[0] == null && bomb[1] == null && bomb[2] == null)
                    setHasBombs(false);
                break;
            }
        }
    }

    public void setSeconds(byte seconds) {
        this.seconds = seconds;
    }

    public void reloadBomb(int position) {
        bomb[position] = new GrandBomb(true);
        bomb[position].setTimeForExplosion(getSeconds());
        setHasBombs(true);
    }

    public BaseBomb[] getBombs() {
        return bomb;
    }

    public void setHasBombs(boolean hasBombs) {
        this.hasBombs = hasBombs;
    }

    public boolean isHasBombs() {
        return hasBombs;
    }

    public byte getSeconds() {
        return seconds;
    }
}
