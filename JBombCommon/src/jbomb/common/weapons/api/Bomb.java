package jbomb.common.weapons.api;

import jbomb.common.controls.AbstractBombControl;

public interface Bomb {
    
    void exploit();
    
    void setTimeForExplosion(float seconds);
    
    float getTimeForExplosion();
    
    void setControl(AbstractBombControl control);
}
