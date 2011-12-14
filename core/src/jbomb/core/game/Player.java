package jbomb.core.game;

import com.jme3.bullet.collision.shapes.CapsuleCollisionShape;
import com.jme3.bullet.control.CharacterControl;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import jbomb.core.sounds.api.Sound;
import jbomb.core.sounds.impl.InstanceSound;
import jbomb.core.weapons.impl.BaseBomb;
import jbomb.core.weapons.impl.GrandBomb;

public class Player extends CharacterControl {
    
    private Sound throwSound = new InstanceSound("bomb/bounce.wav", 6f);
    private BaseBomb[] bomb = new BaseBomb[3];
    public float seconds = 1.5f;
    
    public Player() {
        super(new CapsuleCollisionShape(.55f, 1.7f), .45f);
        setJumpSpeed(12);
        setFallSpeed(10);
        setGravity(30);
        setPhysicsLocation(new Vector3f(0, 35, 0));
        
        for (int i = 0; i < bomb.length; i++) {
            bomb[i] = new GrandBomb();
            bomb[i].setTimeForExplosion(seconds);
        } 
    }
    
    public void throwBomb() {
        for (int i = 0; i < bomb.length; i++)
            if(bomb[i] != null) {
                throwSound.play(getPhysicsLocation());
                Camera cam = JBombContext.JBOMB.getCam();
                Vector3f location = cam.getLocation().add(cam.getDirection().normalize().mult(0.5f + bomb[i].getRadius()));
                RigidBodyControl control = bomb[i].getSpatial().getControl(RigidBodyControl.class);
                control.setPhysicsLocation(location);
                JBombContext.ROOT_NODE.attachChild(bomb[i].getSpatial());
                JBombContext.JBOMB.getBulletAppState().getPhysicsSpace().add(control);
                control.setLinearVelocity(JBombContext.JBOMB.getCam().getDirection().mult(25f));
                bomb[i] = null;
                break;
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
