package jbomb.client.appstates;

import com.jme3.network.Client;
import jbomb.common.appstates.AbstractManager;
import jbomb.common.messages.AbstractPhysicMessage;

public class ClientManager extends AbstractManager<Client> {

    @Override
    public void doOnUpdate(float tpf, AbstractPhysicMessage message) {
        modifyScene(message);
    }
}
