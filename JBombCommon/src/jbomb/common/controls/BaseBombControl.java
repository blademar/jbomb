package jbomb.common.controls;

import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.control.Control;
import jbomb.common.weapons.api.Bomb;

public class BaseBombControl extends JBombAbstractControl {
    
    private float time = 0f;
    private Bomb bomb;

    @Override
    protected Control newInstanceOfMe() {
        return new BaseBombControl();
    }

    @Override
    protected void controlUpdate(float tpf) {
        if (time >= bomb.getTimeForExplosion())
            bomb.exploit();
        else
            time += tpf;
    }

    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {}

    public void setBomb(Bomb bomb) {
        this.bomb = bomb;
    }
}
