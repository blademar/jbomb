package jbomb.core.weapons.impl;

import jbomb.core.effects.impl.WormsExplosion;

public class WormsBomb extends BaseBomb {
    public WormsBomb() {
        setExplosion(new WormsExplosion());
    }
}
