package jbomb.core.game;

import com.jme3.bullet.collision.shapes.CapsuleCollisionShape;
import com.jme3.bullet.control.CharacterControl;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.math.Vector3f;
import jbomb.core.sounds.api.Sound;
import jbomb.core.sounds.impl.InstanceSound;
import jbomb.core.weapons.impl.BaseBomb;
import jbomb.core.weapons.impl.GrandBomb;

public class Player extends CharacterControl {
    
    private Sound throwSound = new InstanceSound("bomb/bounce.wav", 6f);
    private BaseBomb[] bombs = new BaseBomb[3];
    
    public Player() {
        super(new CapsuleCollisionShape(.55f, 1.7f), .45f);
        setJumpSpeed(12);
        setFallSpeed(10);
        setGravity(30);
        setPhysicsLocation(new Vector3f(0, 35, 0));
        
        for (int i = 0; i < bombs.length; i++)
            bombs[i] = new GrandBomb();
    }
    
    public void throwBomb() {
        for (int i = 0; i < bombs.length; i++)
            if(bombs[i] != null) {
                throwSound.play(getPhysicsLocation());
                JBombContext.ROOT_NODE.attachChild(bombs[i].getSpatial());
                JBombContext.JBOMB.getBulletAppState().getPhysicsSpace().add(bombs[i].getSpatial().getControl(RigidBodyControl.class));
                bombs[i] = null;
                break;
            }
    }
    
    public void reloadBomb(int position) {
        bombs[position] = new GrandBomb();
    }
    
    public BaseBomb[] getBombs() {
        return bombs;
    }
}
