package jbomb.core.weapons.impl;

import jbomb.core.effects.impl.GrandExplosion;

public class GrandBomb extends BaseBomb {
    
    public GrandBomb() {
        setExplosion(new GrandExplosion());
    }
}