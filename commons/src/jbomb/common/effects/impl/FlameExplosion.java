
package jbomb.common.effects.impl;

import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;

public class FlameExplosion extends BaseExplosion {
    public FlameExplosion() {
        super("flameExplosion", 6, "flame.png");
    }
    
    @Override
    public void config() {
        emitter.setStartColor(ColorRGBA.Yellow);
        emitter.setEndColor(ColorRGBA.Red);
        emitter.setStartSize(2f);
        emitter.setEndSize(4f);
        emitter.setRandomAngle(true);
        emitter.getParticleInfluencer().setInitialVelocity(Vector3f.UNIT_X);
        emitter.getParticleInfluencer().setVelocityVariation(1f);
    }
    
    @Override
    public float getTimeForDie() {
        return 3f;
    }
}
