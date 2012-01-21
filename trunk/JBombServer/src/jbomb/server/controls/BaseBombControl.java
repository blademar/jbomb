package jbomb.server.controls;

import com.jme3.scene.control.Control;
import jbomb.common.controls.AbstractBombControl;
import jbomb.common.messages.ExploitBombMessage;
import jbomb.server.game.ServerContext;

public class BaseBombControl extends AbstractBombControl {

    @Override
    protected void doOnExploit(float tpf) {
        long id = (Long) getSpatial().getUserData("id");
        
        ServerContext.SERVER.broadcast(new ExploitBombMessage(id));
    }

    @Override
    protected Control newInstanceOfMe() {
        return new BaseBombControl();
    }
}
