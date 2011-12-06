package jbomb.core.game;

import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.collision.shapes.CapsuleCollisionShape;
import com.jme3.bullet.control.CharacterControl;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;
import com.jme3.texture.Texture;
import com.jme3.util.SkyFactory;
import jbomb.core.utils.GeometryUtils;

public class JBomb extends BaseGame implements ActionListener {
    
    private GeometryUtils geometryUtils;
    private BulletAppState bulletAppState;
    private CharacterControl player;
    private Vector3f walkDirection = new Vector3f();
    private boolean left = false, right = false, front = false, back = false;

    @Override
    public void simpleInitApp() {
        super.simpleInitApp();
        bulletAppState = new BulletAppState();
        geometryUtils = new GeometryUtils(assetManager, rootNode, bulletAppState);
        stateManager.attach(bulletAppState);
        initSky();
        initFloor();
        initScene();
        initPlayer();
        
        bulletAppState.getPhysicsSpace().add(player);
//        bulletAppState.getPhysicsSpace().enableDebug(assetManager);
    }
    
    private void initPlayer() {
        player = new CharacterControl(new CapsuleCollisionShape(.55f, 1.7f, 1), 0.45f);
        player.setJumpSpeed(30);
        player.setFallSpeed(40);
        player.setGravity(100);
        player.setPhysicsLocation(new Vector3f(0, 10, 0));
        setUpKeys();
    }
    
    private void setUpKeys() {
        inputManager.addMapping("Left", new KeyTrigger(KeyInput.KEY_A));
        inputManager.addMapping("Right", new KeyTrigger(KeyInput.KEY_D));
        inputManager.addMapping("Front", new KeyTrigger(KeyInput.KEY_W));
        inputManager.addMapping("Back", new KeyTrigger(KeyInput.KEY_S));
        inputManager.addMapping("Jump", new KeyTrigger(KeyInput.KEY_SPACE));
        inputManager.addListener(this, "Left");
        inputManager.addListener(this, "Right");
        inputManager.addListener(this, "Front");
        inputManager.addListener(this, "Back");
        inputManager.addListener(this, "Jump");
      }

    private void initScene() {
        geometryUtils.makePlaneXY(20f, 20f, "north_glass", "textures/glass/sunbeam_t1.png", new Vector3f(0f, 20f, -20f), true);
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

    @Override
    public void onAction(String name, boolean isPressed, float tpf) {
        if (name.equals("Left")) {
            if (isPressed) { left = true; } else { left = false; }
        } else if (name.equals("Right")) {
            if (isPressed) { right = true; } else { right = false; }
        } else if (name.equals("Front")) {
            if (isPressed) { front = true; } else { front = false; }
        } else if (name.equals("Back")) {
            if (isPressed) { back = true; } else { back = false; }
        } else if (name.equals("Jump")) {
            player.jump();
        }
    }
    
//    @Override
//    public void simpleUpdate(float tpf) {
//        Vector3f camDir = cam.getDirection().clone().multLocal(0.2f);
//        Vector3f camLeft = cam.getLeft().clone().multLocal(0.1f);
//        walkDirection.set(0, 0, 0);
//        if (left)  { walkDirection.addLocal(camLeft); }
//        if (right) { walkDirection.addLocal(camLeft.negate()); }
//        if (front)    { walkDirection.addLocal(camDir); }
//        if (back)  { walkDirection.addLocal(camDir.negate()); }
//        player.setWalkDirection(walkDirection);
//        cam.setLocation(player.getPhysicsLocation());
//    }
}
