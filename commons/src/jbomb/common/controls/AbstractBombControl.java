package jbomb.common.controls;

import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import jbomb.common.weapons.api.Bomb;

public abstract class AbstractBombControl extends JBombAbstractControl {
    
    private float time = 0f;
    protected Bomb bomb;

    @Override
    protected void controlUpdate(float tpf) {
        if (time >= bomb.getTimeForExplosion()) {
            doBeforeExploit(tpf);
            bomb.exploit();
        }
        else
            time += tpf;
    }

    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {}

    public void setBomb(Bomb bomb) {
        this.bomb = bomb;
    }

    protected abstract void doBeforeExploit(float tpf);
}
