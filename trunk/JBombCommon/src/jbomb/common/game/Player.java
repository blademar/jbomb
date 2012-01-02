package jbomb.common.game;

import com.jme3.bullet.collision.shapes.CapsuleCollisionShape;
import com.jme3.bullet.control.CharacterControl;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Sphere;
import jbomb.common.sounds.api.Sound;
import jbomb.common.sounds.impl.InstanceSound;
import jbomb.common.utils.MatDefs;
import jbomb.common.weapons.impl.BaseBomb;
import jbomb.common.weapons.impl.GrandBomb;

public class Player {

    private Geometry geometry;
    protected CharacterControl control;
    private ColorRGBA color;
    protected Sound throwSound = new InstanceSound("bomb/bounce.wav", 6f);

    public Player(Vector3f location, ColorRGBA color) {
        control = new CharacterControl(new CapsuleCollisionShape(.55f, 1.7f), .45f);
        control.setJumpSpeed(12);
        control.setFallSpeed(10);
        control.setGravity(30);
        Sphere s = new Sphere(10, 10, .55f);
        geometry = new Geometry("player", s);
        Material m = new Material(JBombContext.ASSET_MANAGER, MatDefs.UNSHADED);
        m.setColor("Color", color);
        this.color = color;
        geometry.setMaterial(m);
        geometry.addControl(control);
        control.setPhysicsLocation(location);
        JBombContext.PHYSICS_SPACE.add(control);
    }

    public Geometry getGeometry() {
        return geometry;
    }

    public CharacterControl getControl() {
        return control;
    }

    public void jump() {
        control.jump();
    }

    public ColorRGBA getColor() {
        return color;
    }

    public void throwBomb(Vector3f location, long idPhysicObject) {
        BaseBomb bomb = new GrandBomb(false);
        bomb.getSpatial().setUserData("id", idPhysicObject);
        JBombContext.MANAGER.addPhysicObject(idPhysicObject, bomb);
        RigidBodyControl bombControl = bomb.getSpatial().getControl(RigidBodyControl.class);
        bombControl.setPhysicsLocation(location);
        JBombContext.ROOT_NODE.attachChild(bomb.getSpatial());
        JBombContext.PHYSICS_SPACE.add(bombControl);
        bombControl.setLinearVelocity(control.getViewDirection().mult(25f));
    }
}
