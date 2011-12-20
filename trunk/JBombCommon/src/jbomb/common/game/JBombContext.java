package jbomb.common.game;

import com.jme3.asset.AssetManager;
import com.jme3.bullet.PhysicsSpace;
import com.jme3.scene.Node;
import java.util.Map;

public class JBombContext {

    public static AssetManager ASSET_MANAGER;
    public static Node ROOT_NODE;
    public static PhysicsSpace PHYSICS_SPACE;
    public static Map<Integer, Player> PLAYERS;
}
