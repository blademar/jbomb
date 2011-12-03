package jbomb.core.game;

import com.jme3.material.Material;
import com.jme3.math.Vector2f;
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
        super.simpleInitApp();
        initSky();
        initFloor();
        initScene();
    }

    private void makeWall(float x, float y, float z, String primaryTexture, String secundaryTexture, float separation) {
        String boxesPath = "textures/boxes/";
        makeCube(.5f, .5f, .5f, "StaticWall", boxesPath, primaryTexture, new Vector3f(x + .5f, y + 1.5f, z + .5f), new Vector2f(.5f, .5f));
        makeCube(.5f, .5f, .5f, "StaticWall", boxesPath, primaryTexture, new Vector3f(x + .5f, y + .5f, z + .5f), new Vector2f(.5f, .5f));

        makeCube(.5f, .5f, .5f, "StaticWall", boxesPath, secundaryTexture, new Vector3f(x + 1.5f, y + .5f, z + .5f + separation), new Vector2f(.5f, .5f));
        makeCube(.5f, .5f, .5f, "StaticWall", boxesPath, primaryTexture, new Vector3f(x + 1.5f, y + 1.5f, z + .5f), new Vector2f(.5f, .5f));

        makeCube(.5f, .5f, .5f, "StaticWall", boxesPath, primaryTexture, new Vector3f(x + 2.5f, y + 1.5f, z + .5f), new Vector2f(.5f, .5f));
        makeCube(.5f, .5f, .5f, "StaticWall", boxesPath, primaryTexture, new Vector3f(x + 2.5f, y + .5f, z + .5f), new Vector2f(.5f, .5f));

        makeCube(.5f, .5f, .5f, "StaticWall", boxesPath, secundaryTexture, new Vector3f(x + 3.5f, y + 1.5f, z + .5f), new Vector2f(.5f, .5f));
        makeCube(.5f, .5f, .5f, "StaticWall", boxesPath, primaryTexture, new Vector3f(x + 3.5f, y + .5f, z + .5f + separation), new Vector2f(.5f, .5f));

        makeCube(.5f, .5f, .5f, "StaticWall", boxesPath, secundaryTexture, new Vector3f(x + 4.5f, y + .5f, z + .5f), new Vector2f(.5f, .5f));
        makeCube(.5f, .5f, .5f, "StaticWall", boxesPath, primaryTexture, new Vector3f(x + 4.5f, y + 1.5f, z + .5f + separation), new Vector2f(.5f, .5f));
    }
    
    private void initScene() {
        float separation = -.15f;
        makeWall(0, 0, 20, "w_gray1.png", "w_blue.png", separation);
        makeWall(5, 0, 20, "w_gray1.png", "w_darkblue.png", separation);
        makeWall(0, 2, 20, "w_gray1.png", "w_red1.png", separation);
        makeWall(5, 2, 20, "w_gray1.png", "w_red2.png", separation);
        makeWall(-5, 0, 20, "w_gray1.png", "w_yellow.png", separation);
        makeWall(-10, 0, 20, "w_gray1.png", "w_brown.png", separation);
        makeWall(-5, 2, 20, "w_gray1.png", "w_green1.png", separation);
        makeWall(-10, 2, 20, "w_gray1.png", "w_green3.png", separation);
    }

    private void makeCube(float x, float y, float z, String name, String texturePath, String textureName, Vector3f localTranslation, Vector2f scaleTexture) {
        Box box = new Box(Vector3f.ZERO, x, y, z);
        Geometry geometry = new Geometry(name, box);
        Material material = new Material(assetManager, MatDefs.UNSHADED);
        Texture texture = assetManager.loadTexture(texturePath + textureName);
        material.setTexture("ColorMap", texture);
        geometry.setMaterial(material);
        geometry.setLocalTranslation(localTranslation);

        if (scaleTexture != null) {
            box.scaleTextureCoordinates(scaleTexture);
            texture.setWrap(Texture.WrapMode.Repeat);
        }

        rootNode.attachChild(geometry);
    }

    private void makeCube(float x, float y, float z, String name, String texturePath, String textureName, Vector3f localTranslation) {
        makeCube(x, y, z, name, texturePath, textureName, localTranslation, null);
    }

    private void initFloor() {
        makeCube(20f, 0.1f, 20f, "floor", "textures/boxes/", "f_blue.png", new Vector3f(0f, -0.1f, 0f), new Vector2f(20f, 20f));
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
