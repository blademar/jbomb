package jbomb.core.effects.api;

import com.jme3.math.Vector3f;

public interface Explosion {
    
    void config();
    
    void start();
    
    void stop();
    
    void setLocation(Vector3f location);
    
    float getTimeForDie();
}
