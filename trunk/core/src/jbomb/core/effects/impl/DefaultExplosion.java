package jbomb.core.effects.impl;

public class DefaultExplosion extends BaseExplosion {
    
    public DefaultExplosion() {
        super("defaultBombExplosion", "flame.png");
    }

    @Override
    public void config() {
        super.config();
        emitter.setStartSize(1.5f);
        emitter.setEndSize(3f);
        emitter.setSelectRandomImage(true);
        emitter.getParticleInfluencer().setVelocityVariation(1.3f);
    }
    
    
}
