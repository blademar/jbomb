package jbomb.core.game;

import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.collision.shapes.CapsuleCollisionShape;
import com.jme3.bullet.control.CharacterControl;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.material.Material;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Sphere;
import com.jme3.texture.Texture;
import com.jme3.util.SkyFactory;
import jbomb.core.listeners.CharacterActionListener;
import jbomb.core.listeners.ShootsActionListener;
import jbomb.core.utils.GeometryUtils;
import jbomb.core.utils.MatDefs;

public class JBomb extends BaseGame {
    
    private GeometryUtils geometryUtils;
    private BulletAppState bulletAppState = bulletAppState = new BulletAppState();
    private CharacterControl player;
    private Vector3f walkDirection = new Vector3f();
    private boolean left = false;
    private boolean right = false;
    private boolean front = false;
    private boolean back = false;
    private ShootsActionListener shootsActionListener = new ShootsActionListener(this);
    private CharacterActionListener characterActionListener = new CharacterActionListener(this);

    @Override
    public void simpleInitApp() {
        super.simpleInitApp();
        geometryUtils = new GeometryUtils(assetManager, rootNode, bulletAppState);
        stateManager.attach(bulletAppState);
        initSky();
        initMappings();
        initFloor();
        initScene();
        initPlayer();
        bulletAppState.getPhysicsSpace().add(getPlayer());
        bulletAppState.getPhysicsSpace().enableDebug(assetManager);
    }
    
    private void initPlayer() {
        player = new CharacterControl(new CapsuleCollisionShape(.55f, 1.7f, 1), 0.45f);
        getPlayer().setJumpSpeed(12);
        getPlayer().setFallSpeed(30);
        getPlayer().setGravity(30);
        getPlayer().setPhysicsLocation(new Vector3f(0, 10, 0));
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
    
    public void makeBomb() {
        Sphere sphere = new Sphere(32, 32, 0.4f);
        sphere.setTextureMode(Sphere.TextureMode.Projected);
        Geometry geometry = new Geometry("bomb", sphere);
        Material material = new Material(assetManager, MatDefs.UNSHADED);
        material.setTexture("ColorMap", assetManager.loadTexture("textures/rocks/bomb.png"));
        geometry.setMaterial(material);
        geometry.setLocalTranslation(cam.getLocation());
        RigidBodyControl rigidBodyControl = new RigidBodyControl(1f);
        geometry.addControl(rigidBodyControl);
        bulletAppState.getPhysicsSpace().add(rigidBodyControl);
        rootNode.attachChild(geometry);
        rigidBodyControl.setLinearVelocity(cam.getDirection().mult(25f));
    }
    
    @Override
    public void simpleUpdate(float tpf) {
        Vector3f camDir = cam.getDirection().clone().multLocal(0.2f);
        Vector3f camLeft = cam.getLeft().clone().multLocal(0.1f);
        walkDirection.set(0, 0, 0);
        if (left)  { walkDirection.addLocal(camLeft); }
        if (right) { walkDirection.addLocal(camLeft.negate()); }
        if (front)    { walkDirection.addLocal(camDir); }
        if (back)  { walkDirection.addLocal(camDir.negate()); }
        player.setWalkDirection(walkDirection);
        cam.setLocation(player.getPhysicsLocation());
    }
    
    private void initMappings() {
        inputManager.addMapping("shoot", new MouseButtonTrigger(MouseInput.BUTTON_LEFT));
        inputManager.addListener(shootsActionListener, "shoot");
        
        inputManager.addMapping("Left", new KeyTrigger(KeyInput.KEY_A));
        inputManager.addMapping("Right", new KeyTrigger(KeyInput.KEY_D));
        inputManager.addMapping("Front", new KeyTrigger(KeyInput.KEY_W));
        inputManager.addMapping("Back", new KeyTrigger(KeyInput.KEY_S));
        inputManager.addMapping("Jump", new KeyTrigger(KeyInput.KEY_SPACE));
        inputManager.addListener(characterActionListener, "Left");
        inputManager.addListener(characterActionListener, "Right");
        inputManager.addListener(characterActionListener, "Front");
        inputManager.addListener(characterActionListener, "Back");
        inputManager.addListener(characterActionListener, "Jump");
    }

    public CharacterControl getPlayer() {
        return player;
    }

    public void setLeft(boolean left) {
        this.left = left;
    }

    public void setRight(boolean right) {
        this.right = right;
    }

    public void setFront(boolean front) {
        this.front = front;
    }

    public void setBack(boolean back) {
        this.back = back;
    }
}
