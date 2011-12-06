package jbomb.core.listeners;

import com.jme3.input.controls.ActionListener;
import jbomb.core.game.JBomb;

public abstract class JBombActionListener implements ActionListener {
    
    protected JBomb jbomb;
    
    public JBombActionListener(JBomb jbomb) {
        this.jbomb = jbomb;
    }
}
