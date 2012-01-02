package jbomb.common.messages;

import com.jme3.math.Vector3f;
import com.jme3.network.serializing.Serializable;

@Serializable
public class ThrowBombMessage extends BasePhysicMessage {
    
    private long idClient = 0;
    private Vector3f location = new Vector3f();
    
    public ThrowBombMessage() {}
    
    public ThrowBombMessage(long id, long idClient, Vector3f location) {
        super(id);
        this.idClient = idClient;
        this.location = location;
    }

    @Override
    public void applyData() {}

    public long getIdClient() {
        return idClient;
    }

    public void setIdClient(long idClient) {
        this.idClient = idClient;
    }

    public Vector3f getLocation() {
        return location;
    }

    public void setLocation(Vector3f location) {
        this.location = location;
    }
}
