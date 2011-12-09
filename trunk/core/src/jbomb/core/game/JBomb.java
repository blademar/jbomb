package jbomb.core.game;

import com.jme3.bullet.BulletAppState;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.font.BitmapText;
import com.jme3.input.MouseInput;
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
import jbomb.core.listeners.CharacterActionListener;
import jbomb.core.listeners.ShootsActionListener;
import jbomb.core.utils.GeometryUtils;

public class JBomb extends BaseGame {
    
    private GeometryUtils geometryUtils;
    private BulletAppState bulletAppState = new BulletAppState();
    private RunningAppState runningAppState = new RunningAppState();
    private boolean left = false;
    private boolean right = false;
    private boolean front = false;
    private boolean back = false;
    private ShootsActionListener shootsActionListener = new ShootsActionListener();
    private CharacterActionListener characterActionListener = new CharacterActionListener();
    private Player player = new Player();

    @Override
    public void simpleInitApp() {
        super.simpleInitApp();
        JBombContext.ASSET_MANAGER = assetManager;
        JBombContext.ROOT_NODE = rootNode;
        JBombContext.JBOMB = this;
        initStateManager();
        initSky();
        initCrossHairs();
        initMappings();
        initFloor();
        initScene();
        getBulletAppState().getPhysicsSpace().add(getPlayer());
//        bulletAppState.getPhysicsSpace().enableDebug(assetManager);
    }
    
    private void initStateManager() {
        stateManager.attach(getBulletAppState());
        stateManager.attach(runningAppState);
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
