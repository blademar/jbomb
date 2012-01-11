package jbomb.common.appstates;

import com.jme3.app.state.AppState;
import com.jme3.network.MessageListener;
import java.util.Set;
import jbomb.common.game.IDRepository;
import jbomb.common.messages.BasePhysicMessage;

public interface Manager<T> extends MessageListener<T>, AppState {
    
    void addPhysicObject(long l, Object o);
    
    Object removePhysicObject(long l);
    
    void doOnUpdate(float tpf, BasePhysicMessage message);
    
    Set<Long> keySet();
    
    Object getPhysicObject(long l);
    
    int getPhycicObjectSize();
    
    IDRepository getRepository();
}
