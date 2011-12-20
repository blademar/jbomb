package jbomb.client.game;

import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import jbomb.common.game.Player;
import jbomb.common.sounds.api.Sound;
import jbomb.common.sounds.impl.InstanceSound;
import jbomb.common.weapons.impl.BaseBomb;
import jbomb.common.weapons.impl.GrandBomb;

public class ClientPlayer extends Player {

    private Sound throwSound = new InstanceSound("bomb/bounce.wav", 6f);
    private BaseBomb[] bomb = new BaseBomb[3];
    public float seconds = 1.5f;

    public ClientPlayer(Vector3f location, ColorRGBA color) {
        super(location, color);
//        for (int i = 0; i < bomb.length; i++) {
//            bomb[i] = new GrandBomb();
//            bomb[i].setTimeForExplosion(seconds);
//        }
    }

    public void throwBomb() {
        for (int i = 0; i < bomb.length; i++) {
            if (bomb[i] != null) {
                throwSound.play(control.getPhysicsLocation());
                Camera cam = ClientContext.APP.getCam();
                Vector3f location = cam.getLocation().add(cam.getDirection().normalize().mult(0.5f + bomb[i].getRadius()));
                RigidBodyControl bombControl = bomb[i].getSpatial().getControl(RigidBodyControl.class);
                bombControl.setPhysicsLocation(location);
                ClientContext.ROOT_NODE.attachChild(bomb[i].getSpatial());
                ClientContext.PHYSICS_SPACE.add(bombControl);
                bombControl.setLinearVelocity(ClientContext.APP.getCam().getDirection().mult(25f));
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
