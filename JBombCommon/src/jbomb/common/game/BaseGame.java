package jbomb.common.game;

import com.jme3.app.SimpleApplication;
import com.jme3.bullet.BulletAppState;
import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.network.serializing.Serializer;
import com.jme3.scene.Geometry;
import com.jme3.scene.Spatial;
import com.jme3.texture.Texture;
import com.jme3.util.SkyFactory;
import jbomb.common.appstates.BaseManager;
import jbomb.common.appstates.Manager;
import jbomb.common.appstates.RunningAppState;
import jbomb.common.controls.AbstractElevatorControl;
import jbomb.common.messages.CharacterMovesMessage;
import jbomb.common.messages.CoordinateBombMessage;
import jbomb.common.messages.CreatePlayerMessage;
import jbomb.common.messages.ExploitBombMessage;
import jbomb.common.messages.ElevatorMovesMessage;
import jbomb.common.messages.NewPlayerMessage;
import jbomb.common.messages.RemovePlayerMessage;
import jbomb.common.messages.StartGameMessage;
import jbomb.common.messages.ThrowBombMessage;
import jbomb.common.scene.Elevator;
import jbomb.common.utils.GeometryUtils;

public abstract class BaseGame extends SimpleApplication {
    
    private BulletAppState bulletAppState = new BulletAppState();
    private RunningAppState runningAppState = createRunningAppState();
    private Manager<?> manager = createManager();

    @Override
    public void simpleInitApp() {
        flyCam.setMoveSpeed(40f);
        setPauseOnLostFocus(false);
        
        initStateManager();
        initContext();
        registerMessages();
        initSky();
        initFloor();
        initWalls();
        initPlatforms();
        initScene();
    }
    
    protected void initStateManager() {
        stateManager.attach(bulletAppState);
        stateManager.attach(getManager());
        stateManager.attach(runningAppState);
    }
    
    private void initWalls() {
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
    }
    
    private void initScene() {
        new Elevator(new Vector3f(-9f, .1f, 18f), 9.9f, .1f, 3f, true);
        new Elevator(new Vector3f(9f, .1f, -18f), 9.9f, .1f, 3f, true);
        new Elevator(new Vector3f(18f, 9.9f, -9f), 9.9f, .1f, 3f, false);
        new Elevator(new Vector3f(-18f, 9.9f, 9f), 9.9f, .1f, 3f, false);

        new Elevator(new Vector3f(-9f, 10.1f, 18f), 19.9f, 10.1f, 3f, true);
        new Elevator(new Vector3f(9f, 10.1f, -18f), 19.9f, 10.1f, 3f, true);
        new Elevator(new Vector3f(16f, 19.9f, 9f), 19.9f, 10.1f, 3f, false);
        new Elevator(new Vector3f(-16f, 19.9f, -9f), 19.9f, 10.1f, 3f, false);

        new Elevator(new Vector3f(16f, 29.9f, 3f), 29.9f, 20.1f, 3f, false);
        new Elevator(new Vector3f(-16f, 29.9f, -3f), 29.9f, 20.1f, 3f, false);
        new Elevator(new Vector3f(14f, 20.1f, -16f), 29.9f, 20.1f, 3f, true);
        new Elevator(new Vector3f(-14f, 20.1f, 16f), 29.9f, 20.1f, 3f, true); 
    }

    private void initFloor() {
        Geometry floor = GeometryUtils.makePlane(
                                     40f, 40f, "floor", "textures/boxes/f_gray.png", new Vector3f(-20f, 0f, 20f),
                                     new Quaternion().fromAngleAxis(-FastMath.PI / 2, Vector3f.UNIT_X),new Vector2f(20f, 20f), true);
    }
    
    private void makeFirtPlatform() {
        float height = 9.9f;
        String texture = "textures/boxes/f_blue.png", name = "firstPlatform";
        
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
    }
    
    private void makeSecondPlatform() {
        float height = 19.9f;
        String texture = "textures/boxes/f_purple.png", name = "secondPlatform";
        
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
    
    private void initPlatforms() {
        makeFirtPlatform();
        makeSecondPlatform();
        makeThirdPlatform();
    } 
    
    public BulletAppState getBulletAppState() {
        return bulletAppState;
    }
    
    protected abstract RunningAppState createRunningAppState();
    
    protected abstract void addMessageListeners();
    
    private void registerMessages() {
        Serializer.registerClass(CharacterMovesMessage.class);
        Serializer.registerClass(StartGameMessage.class);
        Serializer.registerClass(CreatePlayerMessage.class);
        Serializer.registerClass(NewPlayerMessage.class);
        Serializer.registerClass(RemovePlayerMessage.class);
        Serializer.registerClass(ElevatorMovesMessage.class);
        Serializer.registerClass(CoordinateBombMessage.class);
        Serializer.registerClass(ExploitBombMessage.class);
        Serializer.registerClass(ThrowBombMessage.class);
    }

    protected abstract BaseManager<?> createManager();

    private void initContext() {
        JBombContext.ASSET_MANAGER = assetManager;
        JBombContext.ROOT_NODE = rootNode;
        JBombContext.PHYSICS_SPACE = bulletAppState.getPhysicsSpace();
        JBombContext.BASE_GAME = this;
        JBombContext.MANAGER = getManager();
    }

    protected Manager<?> getManager() {
        return manager;
    }
    
    public abstract AbstractElevatorControl createElevatorControl(float maxY, float minY, float seconds, boolean up);
}
