package jbomb.client.appstates;

import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import jbomb.client.game.ClientContext;
import jbomb.common.appstates.RunningAppState;

public class RunningClientAppState extends RunningAppState {
     private float[] timer = new float[] {0, 0, 0};
    
    @Override
    public void update(float tpf) {
//        moveCam();
//        hearingSounds();
//        reloadBombs(tpf);
//        loadInterface();
    }
    
//    private void reloadBombs(float tpf) {
//        for (int i = 0; i < timer.length; i++) 
//                if(JBombContext.BASE_GAME.getPlayer().getBombs()[i] == null)
//                    if(timer[i] >= 5f) {
//                        timer[i] = 0;
//                        JBombContext.BASE_GAME.getPlayer().reloadBomb(i);
//                    } else {
//                        timer[i] += tpf;
//                    }
//    }
    
    private void moveCam() {
        Camera cam = ClientContext.APP.getCam();
        Vector3f camDir = cam.getDirection().clone().multLocal(0.2f);
        Vector3f camLeft = cam.getLeft().clone().multLocal(0.1f);
        camDir.y = 0;
        camLeft.y = 0;
        Vector3f walk = new Vector3f();
        walk.set(0, 0, 0);
        
        if (ClientContext.APP.isLeft())
            walk.addLocal(camLeft);
        if (ClientContext.APP.isRight())
            walk.addLocal(camLeft.negate());
        if (ClientContext.APP.isFront()) 
            walk.addLocal(camDir); 
        if (ClientContext.APP.isBack()) 
            walk.addLocal(camDir.negate());
        
        
        
//        ClientContext.APP.getPlayer().setWalkDirection(walk);
//        ClientContext.APP.getCam().setLocation(ClientContext.APP.getPlayer().getPhysicsLocation());
    }
    
//    private void hearingSounds() {
//        Camera cam = JBombContext.BASE_GAME.getCam();
//        Listener listener = JBombContext.BASE_GAME.getListener();
//        listener.setLocation(cam.getLocation());
//        listener.setRotation(cam.getRotation());
//    }
//    
//    private void loadInterface() {
//        BaseBomb[] bombs = JBombContext.BASE_GAME.getPlayer().getBombs();
//        byte bombsCount = 0;
//        for (BaseBomb b : bombs)
//            if (b != null)
//                bombsCount++;
//        JBombContext.BASE_GAME.getBombsPictures().setImage(JBombContext.ASSET_MANAGER, "interfaces/pictures/bomb" + bombsCount + ".png", true);
//    }
}
