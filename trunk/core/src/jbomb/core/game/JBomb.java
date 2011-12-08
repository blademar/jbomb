package jbomb.core.game;

import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.collision.shapes.CapsuleCollisionShape;
import com.jme3.bullet.control.CharacterControl;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.font.BitmapText;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.material.Material;
import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.scene.Geometry;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Sphere;
import com.jme3.texture.Texture;
import com.jme3.util.SkyFactory;
import jbomb.core.appstates.RunningAppState;
import jbomb.core.listeners.CharacterActionListener;
import jbomb.core.listeners.ShootsActionListener;
import jbomb.core.effects.Explosion;
import jbomb.core.utils.GeometryUtils;
import jbomb.core.utils.MatDefs;

public class JBomb extends BaseGame {
    
    private GeometryUtils geometryUtils;
    private BulletAppState bulletAppState = new BulletAppState();
    private RunningAppState runningAppState = new RunningAppState();
    private CharacterControl player;
    private boolean left = false;
    private boolean right = false;
    private boolean front = false;
    private boolean back = false;
    private ShootsActionListener shootsActionListener = new ShootsActionListener(this);
    private CharacterActionListener characterActionListener = new CharacterActionListener(this);
    private Explosion explosion;
    private long sum = 0;

    @Override
    public void simpleInitApp() {
        super.simpleInitApp();
        geometryUtils = new GeometryUtils(assetManager, rootNode, bulletAppState);
        initStateManager();
        initSky();
        initCrossHairs();
        initMappings();
        initFloor();
        initScene();
        initPlayer();
        bulletAppState.getPhysicsSpace().add(getPlayer());
//        bulletAppState.getPhysicsSpace().enableDebug(assetManager);
        explosion = new Explosion(this);
        explosion.initExplosion();
    }
    
    private void initStateManager() {
        stateManager.attach(bulletAppState);
        stateManager.attach(runningAppState);
    }
    
    private void initPlayer() {
        player = new CharacterControl(new CapsuleCollisionShape(.55f, 1.7f, 1), 0.45f);
        getPlayer().setJumpSpeed(12);
        getPlayer().setFallSpeed(30);
        getPlayer().setGravity(30);
        getPlayer().setPhysicsLocation(new Vector3f(0, 10, 0));
    }

    private void initScene() {
        geometryUtils.makePlane(40f, 40f, "north_glass", "textures/glass/sunbeam_t1.png", new Vector3f(-20f, 0f, -20f),
                true);
        geometryUtils.makePlane(40f, 40f, "south_glass", "textures/glass/sunbeam_t1.png", new Vector3f(20f, 0f, 20f),
                new Quaternion().fromAngleAxis(FastMath.PI, Vector3f.UNIT_Y), true);
        geometryUtils.makePlane(40f, 40f, "west_glass", "textures/glass/sunbeam_t1.png", new Vector3f(-20f, 0f, 20f),
                new Quaternion().fromAngleAxis(FastMath.PI / 2, Vector3f.UNIT_Y), true);
        geometryUtils.makePlane(40f, 40f, "east_glass", "textures/glass/sunbeam_t1.png", new Vector3f(20f, 0f, -20f),
                new Quaternion().fromAngleAxis(-FastMath.PI / 2, Vector3f.UNIT_Y), true);
        geometryUtils.makePlane(40f, 40f, "up_glass", "textures/glass/sunbeam_t1.png", 
                new Vector3f(-20f, 40f, -20f), new Quaternion().fromAngleAxis(FastMath.PI / 2, Vector3f.UNIT_X), true);
    }

    private void initFloor() {
        Geometry geometry = geometryUtils.makePlane(
                40f, 40f, "floor", "textures/boxes/f_blue.png", new Vector3f(-20f, 0f, 20f),
                new Quaternion().fromAngleAxis(-FastMath.PI / 2, Vector3f.UNIT_X),new Vector2f(20f, 20f), true);
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
        material.setTexture("ColorMap", assetManager.loadTexture("textures/rocks/rock.png"));
        geometry.setMaterial(material);
        geometry.setLocalTranslation(cam.getLocation().add(cam.getDirection().mult(1.5f)));
        RigidBodyControl rigidBodyControl = new RigidBodyControl(1f);
        geometry.addControl(rigidBodyControl);
        bulletAppState.getPhysicsSpace().add(rigidBodyControl);
        rootNode.attachChild(geometry);
        rigidBodyControl.setLinearVelocity(cam.getDirection().mult(25f));
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
    
    protected void initCrossHairs() {
        guiNode.detachAllChildren();
        guiFont = assetManager.loadFont("Interface/Fonts/Default.fnt");
        BitmapText ch = new BitmapText(guiFont, false);
        ch.setSize(guiFont.getCharSet().getRenderedSize() * 2);
        ch.setText("+"); // crosshairs
        ch.setLocalTranslation( // center
                settings.getWidth() / 2 - guiFont.getCharSet().getRenderedSize() / 3 * 2,
                settings.getHeight() / 2 + ch.getLineHeight() / 2, 0);
        guiNode.attachChild(ch);
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
    
    public Camera getCam() {
        return cam;
    }

    public boolean isLeft() {
        return left;
    }

    public boolean isRight() {
        return right;
    }

    public boolean isFront() {
        return front;
    }

    public boolean isBack() {
        return back;
    }
}
