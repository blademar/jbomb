package jbomb.core.game;

import com.jme3.bullet.collision.shapes.CapsuleCollisionShape;
import com.jme3.bullet.control.CharacterControl;
import com.jme3.math.Vector3f;
import jbomb.core.weapons.impl.GrandBomb;

public class Player extends CharacterControl {
    
    public Player() {
        super(new CapsuleCollisionShape(.55f, 1.7f, 1), 0.45f);
        setJumpSpeed(12);
        setFallSpeed(30);
        setGravity(30);
        setPhysicsLocation(new Vector3f(0, 20, 0));
    }
    
    public void throwBomb() {
        new GrandBomb();
    }
    
}
