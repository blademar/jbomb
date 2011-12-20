package jbomb.server.main;

import com.jme3.system.JmeContext.Type;
import jbomb.server.game.JBombServer;

public class Main {

    public static void main(String[] args) {
        new JBombServer().start(Type.Headless);
    }
}
