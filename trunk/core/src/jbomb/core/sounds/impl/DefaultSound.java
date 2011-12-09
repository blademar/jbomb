package jbomb.core.sounds.impl;

import com.jme3.audio.AudioNode;
import com.jme3.math.Vector3f;
import jbomb.core.game.JBombContext;
import jbomb.core.sounds.api.Sound;

public class DefaultSound implements Sound {
    
    private AudioNode audioNode;
    private Type type;
    
    public DefaultSound(String fileName, Type type, boolean buffer, float volume) {
        audioNode = new AudioNode(JBombContext.ASSET_MANAGER, 
                (type == Type.INSTANCE ? "sounds/instance/" : "sounds/background/") + fileName, 
                buffer);
        audioNode.setVolume(volume);
        this.type = type;
        if (type == Type.BACKGROUND) {
            audioNode.setLooping(true);
            audioNode.setPositional(false);
        }
    }

    @Override
    public void play(Vector3f location) {
        if (type == Type.INSTANCE) {
            audioNode.setLocalTranslation(location);
            audioNode.playInstance();
        } else
            audioNode.play();
    }
    
    public enum Type {
        BACKGROUND, INSTANCE
    }
}
