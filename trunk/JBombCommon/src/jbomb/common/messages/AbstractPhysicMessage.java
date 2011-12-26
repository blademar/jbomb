package jbomb.common.messages;

import com.jme3.network.AbstractMessage;
import com.jme3.network.serializing.Serializable;

@Serializable
public abstract class AbstractPhysicMessage extends AbstractMessage implements PhysicMessage {
    
    private long id;

    public AbstractPhysicMessage() {}
    
    public AbstractPhysicMessage(long id) {
        this.id = id;
    }

    @Override
    public long getId() {
        return id;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }
    
    @Override
    public void interpolate(float percent) {}
}
