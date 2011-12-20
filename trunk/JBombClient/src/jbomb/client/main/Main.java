package jbomb.client.main;

import com.jme3.system.JmeContext.Type;
import jbomb.client.game.JBombClient;

public class Main {

    public static void main(String[] args) {
        new JBombClient().start(Type.Display);
    }
}
