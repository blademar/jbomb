package jbomb.core.weapons.impl;

import jbomb.core.effects.impl.GrandExplosion;

public class GrandBomb extends BaseBomb {
    
    public GrandBomb() {
        super("bomb.png", .4f, new GrandExplosion(), 2f);
    }
}