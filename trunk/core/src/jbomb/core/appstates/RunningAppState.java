package jbomb.core.appstates;

import com.jme3.app.state.AbstractAppState;
import com.jme3.audio.Listener;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import jbomb.core.game.JBombContext;

public class RunningAppState extends AbstractAppState {
    
    private float timeToReload = 0;
    
    @Override
    public void update(float tpf) {
        moveCam();
        hearingSounds();
        reloadBombs(tpf);
    }
    
    private void reloadBombs(float tpf) {
        if(timeToReload >= 5.5) {
            timeToReload = 0;
            JBombContext.JBOMB.getPlayer().reloadBombs();
        } else
            timeToReload += tpf;
    }
    
    private void moveCam() {
        Camera cam = JBombContext.JBOMB.getCam();
        Vector3f camDir = cam.getDirection().clone().multLocal(0.2f);
        Vector3f camLeft = cam.getLeft().clone().multLocal(0.1f);
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
    
    private void hearingSounds() {
        Camera cam = JBombContext.JBOMB.getCam();
        Listener listener = JBombContext.JBOMB.getListener();
        listener.setLocation(cam.getLocation());
        listener.setRotation(cam.getRotation());
    }
    
}
