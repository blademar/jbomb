package jbomb.common.effects.impl;

import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;

public class MeteoriteExplosion extends MultiExplosion {

    private RoundsparkExplosion roundsparkExplosion;
    private FlameExplosion flameExplosion1, flameExplosion2;  
    private StarExplosion starExplosion;
    
    @Override
    public void config() {
        flameExplosion1 = new FlameExplosion();
        flameExplosion2 = new FlameExplosion();
        roundsparkExplosion = new RoundsparkExplosion();
        starExplosion = new StarExplosion();

        flameExplosion1.emitter.setStartColor(ColorRGBA.Red);
        flameExplosion1.emitter.setEndColor(ColorRGBA.Yellow);
        flameExplosion1.emitter.setStartSize(.6f);
        flameExplosion1.emitter.setEndSize(2.2f);
        flameExplosion1.emitter.getParticleInfluencer().setInitialVelocity(Vector3f.UNIT_XYZ);
        flameExplosion1.emitter.getParticleInfluencer().setVelocityVariation(.5f);
        flameExplosion1.setTimeforDie(2f);
        addExplosion(flameExplosion1);

        flameExplosion2.emitter.setStartColor(ColorRGBA.Yellow);
        flameExplosion2.emitter.setEndColor(ColorRGBA.Orange);
        flameExplosion2.emitter.setStartSize(.6f);
        flameExplosion2.emitter.setEndSize(2.2f);
        flameExplosion2.emitter.getParticleInfluencer().setInitialVelocity(Vector3f.UNIT_XYZ);
        flameExplosion2.emitter.getParticleInfluencer().setVelocityVariation(.5f);
        flameExplosion2.setTimeforDie(2f);
        addExplosion(flameExplosion2);
        
        roundsparkExplosion.emitter.setStartColor(ColorRGBA.Red);
        roundsparkExplosion.emitter.setEndColor(ColorRGBA.Red);
        roundsparkExplosion.emitter.setStartSize(2f);
        roundsparkExplosion.emitter.setEndSize(4f); 
        roundsparkExplosion.emitter.getParticleInfluencer().setInitialVelocity(Vector3f.ZERO);
        roundsparkExplosion.emitter.getParticleInfluencer().setVelocityVariation(3f);
        roundsparkExplosion.setTimeforDie(2f);
        addExplosion(roundsparkExplosion);
        
        starExplosion.emitter.setStartColor(ColorRGBA.Yellow);
        starExplosion.emitter.setEndColor(ColorRGBA.Red);
        starExplosion.emitter.setStartSize(2f);
        starExplosion.emitter.setEndSize(4f); 
        starExplosion.emitter.getParticleInfluencer().setInitialVelocity(Vector3f.ZERO);
        starExplosion.emitter.getParticleInfluencer().setVelocityVariation(3f);
        starExplosion.setTimeforDie(2f);
        addExplosion(starExplosion);
    }
}
