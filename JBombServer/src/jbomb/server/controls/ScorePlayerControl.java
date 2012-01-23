package jbomb.server.controls;

import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.Control;
import jbomb.common.controls.JBombAbstractControl;
import jbomb.common.messages.DamageMessage;
import jbomb.server.game.ServerContext;

public class ScorePlayerControl extends JBombAbstractControl {

    private volatile float damage;
    private float time, maxTime = 1f;
    private float temporalDamage;
    private int id;
    private float health;

    @Override
    protected Control newInstanceOfMe() {
        return new ScorePlayerControl();
    }

    @Override
    protected void controlUpdate(float tpf) {
        time += tpf;
        if (time >= maxTime) {
            time = 0;
            if (damage > 0) {
                synchronized(this) {
                    temporalDamage = damage;
                    damage = 0;
                }
                health = (Float) spatial.getUserData("health");
                health -= temporalDamage;
                spatial.setUserData("health", health);
                ServerContext.SERVER.broadcast(new DamageMessage((long) id, health));
            }
        }
    }

    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {
    }

    public synchronized void sumDamage(float value) {
        damage += value;
    }

    @Override
    public void setSpatial(Spatial spatial) {
        super.setSpatial(spatial);
        id = (Integer) spatial.getUserData("id");
    }
}
