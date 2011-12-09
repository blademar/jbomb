package jbomb.core.controls;

import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.control.Control;
import jbomb.core.effects.impl.DefaultExplosion;

public class DefaultExplosionControl extends JBombAbstractControl {
    
    private float time;
    private DefaultExplosion defaultExplosion;

    @Override
    protected Control newInstanceOfMe() {
        return new DefaultExplosionControl();
    }

    @Override
    protected void controlUpdate(float tpf) {
        if(time >= defaultExplosion.getTimeForDie()) {
            defaultExplosion.stop();
        } else {
            time += tpf;
        }
    }

    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {}
    
    public void setDefaultExplosion(DefaultExplosion defaultExplosion) {
        this.defaultExplosion = defaultExplosion;
    }
}
