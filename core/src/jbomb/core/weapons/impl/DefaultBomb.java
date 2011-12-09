package jbomb.core.weapons.impl;

import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.material.Material;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Sphere;
import jbomb.core.controls.DefaultBombControl;
import jbomb.core.effects.impl.DefaultBombExplosion;
import jbomb.core.game.JBombContext;
import jbomb.core.sounds.impl.DefaultBombSound;
import jbomb.core.utils.MatDefs;
import jbomb.core.weapons.api.Bomb;

public class DefaultBomb implements Bomb {
    
   private Geometry geometry;
   private DefaultBombSound sound = new DefaultBombSound();
   private DefaultBombExplosion explosion = new DefaultBombExplosion();
   private DefaultBombControl control = new DefaultBombControl();
    
    public DefaultBomb(String fileName, float radius) {
        control.setDefaultBomb(this);
        Sphere sphere = new Sphere(32, 32, radius);
        sphere.setTextureMode(Sphere.TextureMode.Projected);
        geometry = new Geometry("bomb", sphere);
        Material material = new Material(JBombContext.ASSET_MANAGER, MatDefs.UNSHADED);
        material.setTexture("ColorMap", JBombContext.ASSET_MANAGER.loadTexture("textures/bomb/" + fileName));
        geometry.setMaterial(material);
        geometry.setLocalTranslation(JBombContext.JBOMB.getCam().getLocation().add(JBombContext.JBOMB.getCam().getDirection().mult(1.5f)));
        RigidBodyControl rigidBodyControl = new RigidBodyControl(1f);
        geometry.addControl(rigidBodyControl);
        geometry.addControl(control);
        JBombContext.JBOMB.getBulletAppState().getPhysicsSpace().add(rigidBodyControl);
        JBombContext.ROOT_NODE.attachChild(geometry);
        rigidBodyControl.setLinearVelocity(JBombContext.JBOMB.getCam().getDirection().mult(25f));
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
        return 3.5f;
    }
    
    public Spatial getSpatial() {
        return geometry;
    }
}
