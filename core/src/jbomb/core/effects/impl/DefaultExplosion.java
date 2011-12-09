package jbomb.core.effects.impl;

import com.jme3.effect.ParticleEmitter;
import com.jme3.effect.ParticleMesh;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import jbomb.core.controls.DefaultExplosionControl;
import jbomb.core.effects.api.Explosion;
import jbomb.core.game.JBombContext;
import jbomb.core.utils.MatDefs;

public class DefaultExplosion implements Explosion {
    
    protected ParticleEmitter emitter;
    private int x, y;
    private DefaultExplosionControl control = new DefaultExplosionControl();
    
    public DefaultExplosion(String name, int numParticles, String fileName, int x, int y) {
        control.setDefaultExplosion(this);
        emitter = new ParticleEmitter(name, ParticleMesh.Type.Triangle, numParticles);
        Material m = new Material(JBombContext.ASSET_MANAGER, MatDefs.PARTICLE);
        m.setTexture("Texture", JBombContext.ASSET_MANAGER.loadTexture("textures/explosion/" + fileName));
        emitter.setMaterial(m);
        emitter.addControl(control);
        this.x = x;
        this.y = y;
        config();
    }
    
    public DefaultExplosion(String name, String fileName, int x, int y) {
        this(name, 30, fileName, x, y);
    }
    
    public DefaultExplosion(String name, String fileName) {
        this(name, 30, fileName, 1, 1);
    }
    
    public DefaultExplosion(String name, int numParticles, String fileName) {
        this(name, numParticles, fileName, 1, 1);
    }

    @Override
    public void config() {
        emitter.setImagesX(x);
        emitter.setImagesY(y);
        emitter.setStartColor(ColorRGBA.Red);
        emitter.setEndColor(ColorRGBA.Yellow);
        emitter.getParticleInfluencer().setInitialVelocity(new Vector3f(0, 0, 0));
        emitter.setStartSize(1.5f);
        emitter.setEndSize(0.1f);
        emitter.setGravity(0, 0, 0);
        emitter.setLowLife(1f);
        emitter.setHighLife(3f);
        emitter.getParticleInfluencer().setVelocityVariation(0.3f);
    }

    @Override
    public void start() {
        JBombContext.ROOT_NODE.attachChild(emitter);
    }

    @Override
    public void stop() {
        JBombContext.ROOT_NODE.detachChild(emitter);
    }
    
    @Override
    public void setLocation(Vector3f location) {
        emitter.setLocalTranslation(location);
    }

    @Override
    public float getTimeForDie() {
        return 1f;
    }
}
