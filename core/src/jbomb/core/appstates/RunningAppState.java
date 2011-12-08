package jbomb.core.appstates;

import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.math.Vector3f;
import jbomb.core.game.JBomb;

public class RunningAppState extends AbstractAppState {
    
    private JBomb jbomb;

    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);
        jbomb = (JBomb) app;
    }
    
    @Override
    public void update(float tpf) {
        Vector3f camDir = jbomb.getCam().getDirection().clone().multLocal(0.2f);
        Vector3f camLeft = jbomb.getCam().getLeft().clone().multLocal(0.1f);
        camDir.y = 0;
        camLeft.y = 0;
        Vector3f walk = new Vector3f();
        walk.set(0, 0, 0);
        
        if (jbomb.isLeft())
            walk.addLocal(camLeft);
        if (jbomb.isRight())
            walk.addLocal(camLeft.negate());
        if (jbomb.isFront()) 
            walk.addLocal(camDir); 
        if (jbomb.isBack()) 
            walk.addLocal(camDir.negate());
        
        jbomb.getPlayer().setWalkDirection(walk);
        jbomb.getCam().setLocation(jbomb.getPlayer().getPhysicsLocation());
    }
    
}
