package jbomb.common.weapons.api;

public interface Bomb {
    
    void exploit();
    
    void setTimeForExplosion(float seconds);
    
    float getTimeForExplosion();
}
