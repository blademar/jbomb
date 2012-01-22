package jbomb.common.messages;

import com.jme3.network.serializing.Serializable;

@Serializable
public class DamageMessage extends BasePhysicMessage {

    private int damage;

    public DamageMessage() {}

    public DamageMessage(long id, int damage) {
        super(id);
        this.damage = damage;
    }

    public int getDamage() {
        return damage;
    }
}
