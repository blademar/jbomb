package jbomb.client.appstates;

import com.jme3.network.Client;
import jbomb.common.appstates.BaseManager;
import jbomb.common.messages.BasePhysicMessage;

public class ClientManager extends BaseManager<Client> {

    @Override
    public void doOnUpdate(float tpf, BasePhysicMessage message) {
        message.applyData();
    }
}
