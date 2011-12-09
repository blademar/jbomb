package jbomb.core.weapons.impl;

import jbomb.core.controls.BaseBombControl;
import jbomb.core.effects.impl.DefaultExplosion;
import jbomb.core.sounds.impl.DefaultBombSound;

public class DefaultBomb extends BaseBomb {
    
    public DefaultBomb() {
        super("bomb.png", .4f, new DefaultExplosion(), new DefaultBombSound(), new BaseBombControl(), 4f, 1f);
    }
    
}
