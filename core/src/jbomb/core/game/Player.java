package jbomb.core.game;

import com.jme3.bullet.collision.shapes.CapsuleCollisionShape;
import com.jme3.bullet.control.CharacterControl;
import com.jme3.math.Vector3f;
import jbomb.core.sounds.api.Sound;
import jbomb.core.sounds.impl.InstanceSound;
import jbomb.core.weapons.impl.GrandBomb;

public class Player extends CharacterControl {
    
    private Sound throwSound = new InstanceSound("bomb/bounce.wav", 6f);
    private int bombsAmount = 3;
    
    public Player() {
        super(new CapsuleCollisionShape(.55f, 1.7f), .45f);
        setJumpSpeed(12);
        setFallSpeed(10);
        setGravity(30);
        setPhysicsLocation(new Vector3f(0, 35, 0));
    }
    
    public void throwBomb() {
        if(bombsAmount > 0) {
            bombsAmount--;
            throwSound.play(getPhysicsLocation());
            new GrandBomb();
        }
    }
    
    public void reloadBombs() {
        bombsAmount = 3;
    }
}
