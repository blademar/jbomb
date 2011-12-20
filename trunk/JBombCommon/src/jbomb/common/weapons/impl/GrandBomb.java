package jbomb.common.weapons.impl;

import jbomb.common.effects.impl.GrandExplosion;

public class GrandBomb extends BaseBomb {
    
    public GrandBomb() {
        super("bomb.png", .4f, new GrandExplosion(), 1.5f);
    }
}
