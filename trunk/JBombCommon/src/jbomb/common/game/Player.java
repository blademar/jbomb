package jbomb.common.game;

import com.jme3.bullet.collision.shapes.CapsuleCollisionShape;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.control.Control;
import com.jme3.scene.shape.Sphere;
import jbomb.common.controls.AbstractBombControl;
import jbomb.common.controls.ThrowBombControl;
import jbomb.common.sounds.api.Sound;
import jbomb.common.sounds.impl.InstanceSound;
import jbomb.common.utils.MatDefs;
import jbomb.common.weapons.impl.BaseBomb;
import jbomb.common.weapons.impl.GrandBomb;

public class Player {

    protected Geometry geometry;
    private RigidBodyControl control;
    private ColorRGBA color;
    protected Sound throwSound = new InstanceSound("bomb/bounce.wav", 6f);
    private Vector3f viewDirection = new Vector3f();
    protected Vector3f location = new Vector3f();

    public Player(Vector3f location, ColorRGBA color) {
        Sphere s = new Sphere(10, 10, .55f);
        geometry = new Geometry("player", s);
        Material m = new Material(JBombContext.ASSET_MANAGER, MatDefs.UNSHADED);
        m.setColor("Color", color);
        this.color = color;
        geometry.setMaterial(m);
        this.location.set(location);
        setControl(createControl());
    }

    public Geometry getGeometry() {
        return geometry;
    }

    public ColorRGBA getColor() {
        return color;
    }
    
    public void throwBomb(Vector3f location, long idPhysicObject, AbstractBombControl abc,
            byte timeExplosion, boolean enableSound) {
        BaseBomb bomb = new GrandBomb(enableSound);
        bomb.setTimeForExplosion(timeExplosion);
        if (abc != null)
            bomb.setControl(abc);
        bomb.getSpatial().setUserData("id", idPhysicObject);
        if (enableSound)
            throwSound.play(control.getPhysicsLocation());
        JBombContext.MANAGER.addPhysicObject(idPhysicObject, bomb);
        ThrowBombControl tbc = bomb.getSpatial().getControl(ThrowBombControl.class);
        tbc.setPhysicsLocation(location);
        JBombContext.ROOT_NODE.attachChild(bomb.getSpatial());
        JBombContext.PHYSICS_SPACE.add(tbc);
        tbc.setDirection(getViewDirection().mult(25f));
    }

    public void setViewDirection(Vector3f viewDirection) {
        geometry.getLocalRotation().lookAt(viewDirection, Vector3f.UNIT_Y);
        this.viewDirection.set(viewDirection);
    }

    protected void setControl(Control control) {
        this.control = (RigidBodyControl) control;
        this.control.setMass(1f);
        this.control.setKinematic(true);
        JBombContext.PHYSICS_SPACE.add(this.control);
        geometry.addControl(control);
        geometry.setLocalTranslation(location);
    }

    protected Control createControl() {
        return new RigidBodyControl(new CapsuleCollisionShape(.55f, 1.7f), .45f);
    }
    
    public Vector3f getViewDirection() {
        return viewDirection;
    }
}
