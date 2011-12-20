package jbomb.common.weapons.impl;

import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.material.Material;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.Control;
import com.jme3.scene.shape.Sphere;
import jbomb.common.controls.BaseBombControl;
import jbomb.common.effects.api.Explosion;
import jbomb.common.effects.impl.BaseExplosion;
import jbomb.common.game.JBombContext;
import jbomb.common.sounds.api.Sound;
import jbomb.common.sounds.impl.BaseBombSound;
import jbomb.common.utils.MatDefs;
import jbomb.common.weapons.api.Bomb;

public class BaseBomb implements Bomb {
    
   private Geometry geometry;
   private Sound sound;
   private Explosion explosion;
   private Control control;
   private float timeForExplosion;
   private float radius;
    
    public BaseBomb(String fileName, float radius, Explosion explosion, Sound sound, BaseBombControl baseBombControl, float timeForExplosion, float mass) {
        control = baseBombControl;
        baseBombControl.setBomb(this);
        this.timeForExplosion = timeForExplosion;
        this.explosion = explosion;
        this.sound = sound;
        Sphere sphere = new Sphere(32, 32, radius);
        sphere.setTextureMode(Sphere.TextureMode.Projected);
        geometry = new Geometry("bomb", sphere);
        Material material = new Material(JBombContext.ASSET_MANAGER, MatDefs.UNSHADED);
        material.setTexture("ColorMap", JBombContext.ASSET_MANAGER.loadTexture("textures/bomb/" + fileName));
        geometry.setMaterial(material);
        RigidBodyControl rigidBodyControl = new RigidBodyControl(mass);
        geometry.addControl(rigidBodyControl);
        geometry.addControl(control);
//        rigidBodyControl.setLinearVelocity(JBombContext.BASE_GAME.getCam().getDirection().mult(25f));
        this.radius = radius;
    }
    
    public BaseBomb(String fileName, float radius, Explosion explosion, float timeForExplosion) {
        this(fileName, radius, explosion, new BaseBombSound(), new BaseBombControl(), timeForExplosion, 1f);
    }
    
    public BaseBomb(String fileName, float radius, float timeForExplosion) {
        this(fileName, radius, new BaseExplosion(), new BaseBombSound(), new BaseBombControl(), timeForExplosion, 1f);
    }
    
    public BaseBomb() {
        this("bomb.png", 0.4f, 1f);
    }

    @Override
    public void exploit() {
        Vector3f location = geometry.getControl(RigidBodyControl.class).getPhysicsLocation();
        explosion.setLocation(location);
        explosion.start();
        sound.play(location);
        JBombContext.ROOT_NODE.detachChild(geometry);
    }

    @Override
    public float getTimeForExplosion() {
        return timeForExplosion;
    }
    
    public Spatial getSpatial() {
        return geometry;
    }
    
    public void setExplosion(Explosion explosion) {
        this.explosion = explosion;
    }
    
    public void setLocalTranslation(Vector3f localTranslation) {
        geometry.setLocalTranslation(localTranslation);
    }

    public float getRadius() {
        return radius;
    }

    @Override
    public void setTimeForExplosion(float seconds) {
        timeForExplosion = seconds;
    }
}
