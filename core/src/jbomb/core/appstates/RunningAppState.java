package jbomb.core.appstates;

import com.jme3.app.state.AbstractAppState;
import com.jme3.math.Vector3f;
import jbomb.core.game.JBombContext;

public class RunningAppState extends AbstractAppState {
    
    @Override
    public void update(float tpf) {
        Vector3f camDir = JBombContext.JBOMB.getCam().getDirection().clone().multLocal(0.2f);
        Vector3f camLeft = JBombContext.JBOMB.getCam().getLeft().clone().multLocal(0.1f);
        camDir.y = 0;
        camLeft.y = 0;
        Vector3f walk = new Vector3f();
        walk.set(0, 0, 0);
        
        if (JBombContext.JBOMB.isLeft())
            walk.addLocal(camLeft);
        if (JBombContext.JBOMB.isRight())
            walk.addLocal(camLeft.negate());
        if (JBombContext.JBOMB.isFront()) 
            walk.addLocal(camDir); 
        if (JBombContext.JBOMB.isBack()) 
            walk.addLocal(camDir.negate());
        
        JBombContext.JBOMB.getPlayer().setWalkDirection(walk);
        JBombContext.JBOMB.getCam().setLocation(JBombContext.JBOMB.getPlayer().getPhysicsLocation());
    }
    
}
