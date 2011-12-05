package jbomb.core.game;

import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;
import com.jme3.texture.Texture;
import com.jme3.util.SkyFactory;
import jbomb.core.utils.GeometryUtils;

public class JBomb extends BaseGame {
    
    private GeometryUtils geometryUtils = new GeometryUtils(assetManager, rootNode);

    @Override
    public void simpleInitApp() {
        super.simpleInitApp();
        initSky();
        initFloor();
        initScene();
    }

    private void initScene() {
        geometryUtils.makePlaneXY(20f, 20f, "south_glass", "textures/glass/sunbeam_t1.png", new Vector3f(0f, 20f, 20f), true);
        geometryUtils.makePlaneYZ(20f, 20f, "west_glass", "textures/glass/sunbeam_t1.png", new Vector3f(-20f, 20f, 0f), true);
        geometryUtils.makePlaneYZ(20f, 20f, "east_glass", "textures/glass/sunbeam_t1.png", new Vector3f(20f, 20f, 0f), true);
        geometryUtils.makePlaneXZ(20f, 20f, "up_glass", "textures/glass/sunbeam_t1.png", new Vector3f(0f, 40f, 0f), true);
    }

    private void initFloor() {
        geometryUtils.makePlaneXZ(20f, 20f, "floor", "textures/boxes/f_blue.png", Vector3f.ZERO, new Vector2f(20f, 20f), true);
    }

    private void initSky() {
        String basePath = "textures/sky/space2/";
        Texture west = assetManager.loadTexture(basePath + "west.png");
        Texture east = assetManager.loadTexture(basePath + "east.png");
        Texture north = assetManager.loadTexture(basePath + "north.png");
        Texture south = assetManager.loadTexture(basePath + "south.png");
        Texture up = assetManager.loadTexture(basePath + "up.png");
        Texture down = assetManager.loadTexture(basePath + "down.png");
        Spatial sky = SkyFactory.createSky(assetManager, west, east, north, south, up, down);
        rootNode.attachChild(sky);
    }
}
