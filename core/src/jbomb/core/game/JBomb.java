package jbomb.core.game;

import com.jme3.material.Material;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;
import com.jme3.texture.Texture;
import com.jme3.util.SkyFactory;
import jbomb.core.utils.MatDefs;

public class JBomb extends BaseGame {

    @Override
    public void simpleInitApp() {
        initSky();
    }
    
    private void makeCube(float x, float y, float z, String name) {
        Box box = new Box(Vector3f.ZERO, x, y, z);
        Geometry geometry = new Geometry(name, box);
        Material material = new Material(assetManager, MatDefs.UNSHADED);
        
    }
    
    private void initFloor() {
        
    }

    private void initSky() {
        String basePath = "textures/sky/space/";
        Texture west = assetManager.loadTexture(basePath + "west.tga");
        Texture east = assetManager.loadTexture(basePath + "east.tga");
        Texture north = assetManager.loadTexture(basePath + "north.tga");
        Texture south = assetManager.loadTexture(basePath + "south.tga");
        Texture up = assetManager.loadTexture(basePath + "up.tga");
        Texture down = assetManager.loadTexture(basePath + "down.tga");
        Spatial sky = SkyFactory.createSky(assetManager, west, east, north, south, up, down);
        rootNode.attachChild(sky);
    }
}
