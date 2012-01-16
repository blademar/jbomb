package jbomb.server.main;

import jbomb.server.game.JBombServer;

public class Main {

    public static void main(String[] args) {
        String playersNumber = "four";
        if (args.length != 0) {
            playersNumber = args[0];
        }
        new JBombServer(playersNumber).start();
    }
}
