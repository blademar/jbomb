package jbomb.core.game;

import com.jme3.bullet.BulletAppState;
import com.jme3.font.BitmapText;
import com.jme3.input.KeyInput;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.scene.Geometry;
import com.jme3.scene.Spatial;
import com.jme3.texture.Texture;
import com.jme3.util.SkyFactory;
import jbomb.core.appstates.RunningAppState;
import jbomb.core.listeners.BombSecondsListener;
import jbomb.core.listeners.CharacterActionListener;
import jbomb.core.listeners.ShootsActionListener;
import jbomb.core.scene.Elevator;
import jbomb.core.utils.GeometryUtils;

public class JBomb extends BaseGame {
    
    private BulletAppState bulletAppState = new BulletAppState();
    private RunningAppState runningAppState = new RunningAppState();
    private boolean left = false;
    private boolean right = false;
    private boolean front = false;
    private boolean back = false;
    private Player player;
    private ShootsActionListener shootsActionListener = new ShootsActionListener();
    private CharacterActionListener characterActionListener = new CharacterActionListener();
    private BombSecondsListener bombSecondsListener = new BombSecondsListener();

    @Override
    public void simpleInitApp() {
        super.simpleInitApp();
        JBombContext.ASSET_MANAGER = assetManager;
        JBombContext.ROOT_NODE = rootNode;
        JBombContext.JBOMB = this;
        player = new Player();
        initStateManager();
        initSky();
        initCrossHairs();
        initMappings();
        initFloor();
        initScene();
        bulletAppState.getPhysicsSpace().add(player);
//        bulletAppState.getPhysicsSpace().enableDebug(assetManager);
    }
    
    private void initStateManager() {
        stateManager.attach(bulletAppState);
        stateManager.attach(runningAppState);
    }
    
    private void initScene() {
        GeometryUtils.makePlane(40f, 40f, "north_glass", "textures/glass/sunbeam_t1.png", new Vector3f(-20f, 0f, -20f),
                true);
        GeometryUtils.makePlane(40f, 40f, "south_glass", "textures/glass/sunbeam_t1.png", new Vector3f(20f, 0f, 20f),
                new Quaternion().fromAngleAxis(FastMath.PI, Vector3f.UNIT_Y), true);
        GeometryUtils.makePlane(40f, 40f, "west_glass", "textures/glass/sunbeam_t1.png", new Vector3f(-20f, 0f, 20f),
                new Quaternion().fromAngleAxis(FastMath.PI / 2, Vector3f.UNIT_Y), true);
        GeometryUtils.makePlane(40f, 40f, "east_glass", "textures/glass/sunbeam_t1.png", new Vector3f(20f, 0f, -20f),
                new Quaternion().fromAngleAxis(-FastMath.PI / 2, Vector3f.UNIT_Y), true);
        GeometryUtils.makePlane(40f, 40f, "up_glass", "textures/glass/sunbeam_t1.png", 
                new Vector3f(-20f, 40f, -20f), new Quaternion().fromAngleAxis(FastMath.PI / 2, Vector3f.UNIT_X), true);
        
        makeFirtPlatform();
        makeSecondPlatform();
        makeThirdPlatform();
    }

    private void initFloor() {
        Geometry floor = GeometryUtils.makePlane(
                                     40f, 40f, "floor", "textures/boxes/f_blue.png", new Vector3f(-20f, 0f, 20f),
                                     new Quaternion().fromAngleAxis(-FastMath.PI / 2, Vector3f.UNIT_X),new Vector2f(20f, 20f), true);
    }
    
    private void makeFirtPlatform() {
        float height = 9.9f;
        String texture = "textures/boxes/f_purple.png", name = "firstPlatform";
        
        GeometryUtils.makeCube(
                5f, 0.1f, 5f, name, texture, new Vector3f(0f, height, 0f), new Vector2f(5f, 5f), true);
        GeometryUtils.makeCube(
                2f, 0.1f, 5.5f, name, texture, new Vector3f(0f, height, 10.5f), new Vector2f(5.5f, 2f), true);
        GeometryUtils.makeCube(
                14f, 0.1f, 2f, name, texture, new Vector3f(6f, height, 18f), new Vector2f(2f, 14f), true);
        GeometryUtils.makeCube(
                2f, 0.1f, 12f, name, texture, new Vector3f(18f, height, 4f), new Vector2f(12f, 2f), true);
        GeometryUtils.makeCube(
                5.5f, 0.1f, 2f, name, texture, new Vector3f(-10.5f, height, 0f), new Vector2f(2f, 5.5f), true);
        GeometryUtils.makeCube(
                2f, 0.1f, 14f, name, texture, new Vector3f(-18f, height, -6f), new Vector2f(14f, 2f), true);
        GeometryUtils.makeCube(
                12f, 0.1f, 2f, name, texture, new Vector3f(-4f, height, -18f), new Vector2f(2f, 14f), true);
        
        new Elevator(new Vector3f(-9f, .1f, 18f), 9.9f, .1f, 3f, true);
        new Elevator(new Vector3f(9f, .1f, -18f), 9.9f, .1f, 3f, true);
        new Elevator(new Vector3f(18f, 9.9f, -9f), 9.9f, .1f, 3f, false);
        new Elevator(new Vector3f(-18f, 9.9f, 9f), 9.9f, .1f, 3f, false);
    }
    
    private void makeSecondPlatform() {
        float height = 19.9f;
        String texture = "textures/boxes/f_gray.png", name = "secondPlatform";
        
        GeometryUtils.makeCube(
                4f, 0.1f, 4f, name, texture, new Vector3f(0f, height, 0f), new Vector2f(4f, 4f), true);
        GeometryUtils.makeCube(
                5f, 0.1f, 5f, name, texture, new Vector3f(15f, height, 15f), new Vector2f(5f, 5f), true);
        GeometryUtils.makeCube(
                5f, 0.1f, 5f, name, texture, new Vector3f(-15f, height, -15f), new Vector2f(5f, 5f), true);
        GeometryUtils.makeCube(
                5f, 0.1f, 5f, name, texture, new Vector3f(-15f, height, 15f), new Vector2f(5f, 5f), true);
        GeometryUtils.makeCube(
                5f, 0.1f, 5f, name, texture, new Vector3f(15f, height, -15f), new Vector2f(5f, 5f), true);
        GeometryUtils.makeCube(
                10f, 0.1f, 2f, name, texture, new Vector3f(0f, height, 13f), new Vector2f(2f, 10f), true);
        GeometryUtils.makeCube(
                10f, 0.1f, 2f, name, texture, new Vector3f(0f, height, -13f), new Vector2f(2f, 10f), true);
        GeometryUtils.makeCube(
                2f, 0.1f, 10f, name, texture, new Vector3f(13f, height, 0f), new Vector2f(10f, 2f), true);
        GeometryUtils.makeCube(
                2f, 0.1f, 10f, name, texture, new Vector3f(-13f, height, 0f), new Vector2f(10f, 2f), true);
        GeometryUtils.makeCube(
                2f, 0.1f, 3.5f, name, texture, new Vector3f(0f, height, 7.5f), new Vector2f(3.5f, 2f), true);
        GeometryUtils.makeCube(
                2f, 0.1f, 3.5f, name, texture, new Vector3f(0f, height, -7.5f), new Vector2f(3.5f, 2f), true);
        GeometryUtils.makeCube(
                3.5f, 0.1f, 2f, name, texture, new Vector3f(7.5f, height, 0f), new Vector2f(2f, 3.5f), true);
        GeometryUtils.makeCube(
                3.5f, 0.1f, 2f, name, texture, new Vector3f(-7.5f, height, 0f), new Vector2f(2f, 3.5f), true);
        
        new Elevator(new Vector3f(-9f, 10f, 18f), 19.9f, 10f, 3f, true);
        new Elevator(new Vector3f(9f, 10f, -18f), 19.9f, 10f, 3f, true);
        new Elevator(new Vector3f(16f, 19.9f, 9f), 19.9f, 10.1f, 3f, false);
        new Elevator(new Vector3f(-16f, 19.9f, -9f), 19.9f, 10.1f, 3f, false);
    }
    
    private void makeThirdPlatform() {
        float height = 29.9f;
        String texture = "textures/boxes/f_orange.png", name = "thirdPlatform";
        
        GeometryUtils.makeCube(
                4f, 0.1f, 2f, name, texture, new Vector3f(0f, height, 0f), new Vector2f(2f, 4f), true);
        GeometryUtils.makeCube(
                3f, 0.1f, 3f, name, texture, new Vector3f(12f, height, 3f), new Vector2f(3f, 3f), true);
        GeometryUtils.makeCube(
                3f, 0.1f, 3f, name, texture, new Vector3f(3f, height, 12f), new Vector2f(3f, 3f), true);
        GeometryUtils.makeCube(
                3f, 0.1f, 3f, name, texture, new Vector3f(-12f, height, 12f), new Vector2f(3f, 3f), true);
        GeometryUtils.makeCube(
                3f, 0.1f, 3f, name, texture, new Vector3f(-12f, height, -3f), new Vector2f(3f, 3f), true);
        GeometryUtils.makeCube(
                3f, 0.1f, 3f, name, texture, new Vector3f(-3f, height, -12f), new Vector2f(3f, 3f), true);
        GeometryUtils.makeCube(
                3f, 0.1f, 3f, name, texture, new Vector3f(12f, height, -12f), new Vector2f(3f, 3f), true);
        GeometryUtils.makeCube(
                1f, 0.1f, 3.5f, name, texture, new Vector3f(3f, height, 5.5f), new Vector2f(3.5f, 1f), true);
        GeometryUtils.makeCube(
                1f, 0.1f, 3.5f, name, texture, new Vector3f(-3f, height, -5.5f), new Vector2f(3.5f, 1f), true);
        GeometryUtils.makeCube(
                2.5f, 0.1f, 1f, name, texture, new Vector3f(6.5f, height, 1f), new Vector2f(1f, 2.5f), true);
        GeometryUtils.makeCube(
                2.5f, 0.1f, 1f, name, texture, new Vector3f(-6.5f, height, -1f), new Vector2f(1f, 2.5f), true);
        GeometryUtils.makeCube(
                1f, 0.1f, 4.5f, name, texture, new Vector3f(12f, height, -4.5f), new Vector2f(4.5f, 1f), true);
        GeometryUtils.makeCube(
                1f, 0.1f, 4.5f, name, texture, new Vector3f(-12f, height, 4.5f), new Vector2f(4.5f, 1f), true);
        GeometryUtils.makeCube(
                4.5f, 0.1f, 1f, name, texture, new Vector3f(-4.5f, height, 12f), new Vector2f(1f, 4.5f), true);
        GeometryUtils.makeCube(
                4.5f, 0.1f, 1f, name, texture, new Vector3f(4.5f, height, -12f), new Vector2f(1f, 4.5f), true);
        
        new Elevator(new Vector3f(16f, 29.9f, 3f), 29.9f, 20.1f, 3f, false);
        new Elevator(new Vector3f(-16f, 29.9f, -3f), 29.9f, 20.1f, 3f, false);
        new Elevator(new Vector3f(14f, 20.1f, -16f), 29.9f, 20.1f, 3f, true);
        new Elevator(new Vector3f(-14f, 20.1f, 16f), 29.9f, 20.1f, 3f, true);
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
    
    private void initMappings() {
        inputManager.addMapping("shoot", new MouseButtonTrigger(MouseInput.BUTTON_LEFT));
        inputManager.addListener(shootsActionListener, "shoot");
        inputManager.addMapping("Left", new KeyTrigger(KeyInput.KEY_A));
        inputManager.addMapping("Right", new KeyTrigger(KeyInput.KEY_D));
        inputManager.addMapping("Front", new KeyTrigger(KeyInput.KEY_W));
        inputManager.addMapping("Back", new KeyTrigger(KeyInput.KEY_S));
        inputManager.addMapping("Jump", new KeyTrigger(KeyInput.KEY_SPACE));
        inputManager.addMapping("one", new KeyTrigger(KeyInput.KEY_1), new KeyTrigger(KeyInput.KEY_NUMPAD1));
        inputManager.addMapping("two", new KeyTrigger(KeyInput.KEY_2), new KeyTrigger(KeyInput.KEY_NUMPAD2));
        inputManager.addMapping("three", new KeyTrigger(KeyInput.KEY_3), new KeyTrigger(KeyInput.KEY_NUMPAD3));
        inputManager.addListener(characterActionListener, "Left");
        inputManager.addListener(characterActionListener, "Right");
        inputManager.addListener(characterActionListener, "Front");
        inputManager.addListener(characterActionListener, "Back");
        inputManager.addListener(characterActionListener, "Jump");
        inputManager.addListener(bombSecondsListener, "one");
        inputManager.addListener(bombSecondsListener, "two");
        inputManager.addListener(bombSecondsListener, "three");
    }
    
    protected void initCrossHairs() {
        guiFont = assetManager.loadFont("Interface/Fonts/Default.fnt");
        BitmapText ch = new BitmapText(guiFont, false);
        ch.setSize(guiFont.getCharSet().getRenderedSize() * 2);
        ch.setText("+");
        ch.setLocalTranslation(
                settings.getWidth() / 2 - guiFont.getCharSet().getRenderedSize() / 3 * 2,
                settings.getHeight() / 2 + ch.getLineHeight() / 2, 0);
        guiNode.attachChild(ch);
    }
    
    public Player getPlayer() {
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
    
    public float getSpeed() {
        return speed;
    }

    public BulletAppState getBulletAppState() {
        return bulletAppState;
    }
}