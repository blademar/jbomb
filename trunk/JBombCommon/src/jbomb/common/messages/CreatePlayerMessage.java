package jbomb.common.messages;

import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.network.serializing.Serializable;
import jbomb.common.game.JBombContext;
import jbomb.common.game.Player;

@Serializable
public class CreatePlayerMessage extends AbstractPhysicMessage {

    private Vector3f location;
    private ColorRGBA color;
    private Player player;

    public CreatePlayerMessage() {}

    public CreatePlayerMessage(Vector3f location, ColorRGBA color) {
        this.color = color;
        this.location = location;
    }

    public Vector3f getLocation() {
        return location;
    }

    public void setLocation(Vector3f location) {
        this.location = location;
    }

    public ColorRGBA getColor() {
        return color;
    }

    public void setColor(ColorRGBA color) {
        this.color = color;
    }

    @Override
    public void applyData() {
        player = new Player(getLocation(), getColor());
        JBombContext.ROOT_NODE.attachChild(player.getGeometry());
    }

    public Player getPlayer() {
        return player;
    }
}
