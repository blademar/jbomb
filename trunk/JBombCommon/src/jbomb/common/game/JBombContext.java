package jbomb.common.game;

import com.jme3.asset.AssetManager;
import com.jme3.bullet.PhysicsSpace;
import com.jme3.scene.Node;
import jbomb.common.appstates.Manager;

public class JBombContext {

    public static AssetManager ASSET_MANAGER;
    public static Node ROOT_NODE;
    public static PhysicsSpace PHYSICS_SPACE;
    public static BaseGame BASE_GAME;
    public static Manager MANAGER;
    public static boolean STARTED;
    public static byte PLAYERS_COUNT;
    public static float MESSAGES_PER_SECOND = 4f;
}
