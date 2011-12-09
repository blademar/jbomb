package jbomb.core.controls;

import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.control.Control;
import jbomb.core.weapons.impl.DefaultBomb;

public class DefaultBombControl extends JBombAbstractControl {
    
    private float time = 0f;
    private DefaultBomb defaultBomb;

    @Override
    protected Control newInstanceOfMe() {
        return new DefaultBombControl();
    }

    @Override
    protected void controlUpdate(float tpf) {
        if (time >= defaultBomb.getTimeForExplosion())
            defaultBomb.exploit();
        else
            time += tpf;
    }

    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {}

    public void setDefaultBomb(DefaultBomb defaultBomb) {
        this.defaultBomb = defaultBomb;
    }
}
