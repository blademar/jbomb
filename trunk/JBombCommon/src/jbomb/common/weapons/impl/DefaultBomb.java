package jbomb.common.weapons.impl;

import jbomb.common.effects.impl.DefaultExplosion;
import jbomb.common.sounds.impl.DefaultBombSound;

public class DefaultBomb extends BaseBomb {
    
    public DefaultBomb() {
        super("bomb.png", .4f, new DefaultExplosion(), new DefaultBombSound(), 4f, 1f);
    }
    
}
