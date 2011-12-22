package jbomb.client.messages.task;

import jbomb.common.messages.AbstractPhysicMessage;

public class PhysicTask implements Task<AbstractPhysicMessage> {

    @Override
    public void doThis(AbstractPhysicMessage message) {
        message.applyData();
    }
    
}
