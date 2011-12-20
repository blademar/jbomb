package jbomb.common.weapons.impl;

import jbomb.common.controls.BaseBombControl;
import jbomb.common.effects.impl.DefaultExplosion;
import jbomb.common.sounds.impl.DefaultBombSound;

public class DefaultBomb extends BaseBomb {
    
    public DefaultBomb() {
        super("bomb.png", .4f, new DefaultExplosion(), new DefaultBombSound(), new BaseBombControl(), 4f, 1f);
    }
    
}
