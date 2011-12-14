package jbomb.core.appstates;

import com.jme3.app.state.AbstractAppState;
import com.jme3.audio.Listener;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import jbomb.core.game.JBombContext;
import jbomb.core.weapons.impl.BaseBomb;

public class RunningAppState extends AbstractAppState {
    
    private float timer[] = new float[] {0, 0, 0};
    
    @Override
    public void update(float tpf) {
        moveCam();
        hearingSounds();
        reloadBombs(tpf);
        loadInterface();
    }
    
    private void reloadBombs(float tpf) {
        for (int i = 0; i < timer.length; i++) 
                if(JBombContext.JBOMB.getPlayer().getBombs()[i] == null)
                    if(timer[i] >= 5.5f) {
                        timer[i] = 0;
                        JBombContext.JBOMB.getPlayer().reloadBomb(i);
                    } else {
                        timer[i] += tpf;
                    }
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
    
    private void loadInterface() {
        BaseBomb[] bombs = JBombContext.JBOMB.getPlayer().getBombs();
        byte bombsCount = 0;
        for (BaseBomb b : bombs)
            if (b != null)
                bombsCount++;
        JBombContext.JBOMB.getBombsPictures().setImage(JBombContext.ASSET_MANAGER, "interfaces/pictures/bomb" + bombsCount + ".png", true);
    }
    
}
