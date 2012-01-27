package jbomb.client.appstates;

import com.jme3.audio.Listener;
import com.jme3.bullet.control.CharacterControl;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import jbomb.client.game.ClientContext;
import jbomb.common.appstates.RunningAppState;
import jbomb.common.game.JBombContext;
import jbomb.common.messages.CharacterMovesMessage;
import jbomb.common.weapons.impl.BaseBomb;

public class RunningClientAppState extends RunningAppState {

    private float[] timer = new float[]{0, 0, 0};
    private float time;
    private float maxTime = 1f / JBombContext.MESSAGES_PER_SECOND;

    @Override
    public void update(float tpf) {
        moveCam(tpf);
        hearingSounds();
        reloadBombs(tpf);
        loadInterface();
    }

    private void reloadBombs(float tpf) {
        if (JBombContext.STARTED) {
            for (int i = 0; i < timer.length; i++) {
                if (ClientContext.PLAYER.getBombs()[i] == null) {
                    if (timer[i] >= 5f) {
                        timer[i] = 0;
                        ClientContext.PLAYER.reloadBomb(i);
                    } else {
                        timer[i] += tpf;
                    }
                }
            }
        }
    }

    private void moveCam(float tpf) {
        if (JBombContext.STARTED) {
            time += tpf;

            Camera cam = ClientContext.APP.getCam();
            Vector3f camDir = cam.getDirection().clone().multLocal(0.2f);
            Vector3f camLeft = cam.getLeft().clone().multLocal(0.1f);
            camDir.y = 0;
            camLeft.y = 0;
            Vector3f walk = new Vector3f();
            walk.set(0, 0, 0);

            if (ClientContext.APP.isLeft()) {
                walk.addLocal(camLeft);
            }
            if (ClientContext.APP.isRight()) {
                walk.addLocal(camLeft.negate());
            }
            if (ClientContext.APP.isFront()) {
                walk.addLocal(camDir);
            }
            if (ClientContext.APP.isBack()) {
                walk.addLocal(camDir.negate());
            }

            CharacterControl c = ClientContext.PLAYER.getControl();
            c.setViewDirection(cam.getDirection());
            if (time >= maxTime) {
                time = 0;
                ClientContext.CLIENT.send(
                        new CharacterMovesMessage(ClientContext.CLIENT.getId(), c, walk));
            }
            c.setWalkDirection(walk);
            ClientContext.APP.getCam().setLocation(c.getPhysicsLocation());
        }
    }

    private void hearingSounds() {
        Camera cam = ClientContext.APP.getCam();
        Listener listener = JBombContext.BASE_GAME.getListener();
        listener.setLocation(cam.getLocation());
        listener.setRotation(cam.getRotation());
    }

    private void loadInterface() {
        if (JBombContext.STARTED) {
            BaseBomb[] bombs = ClientContext.PLAYER.getBombs();
            byte bombsCount = 0;
            for (BaseBomb b : bombs) {
                if (b != null) {
                    bombsCount++;
                }
            }
            ClientContext.APP.getBombsPictures().setImage(JBombContext.ASSET_MANAGER, "interfaces/pictures/bomb" + bombsCount + ".png", true);
        }
    }
}
