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
import com.jme3.scene.control.Control;
import com.jme3.texture.Texture;
import com.jme3.util.SkyFactory;
import java.net.URL;
import jbomb.common.appstates.AbstractManager;
import jbomb.common.appstates.RunningAppState;
import jbomb.common.messages.CharacterMovesMessage;
import jbomb.common.messages.CoordinateBombMessage;
import jbomb.common.messages.CreatePlayerMessage;
import jbomb.common.messages.DamageMessage;
import jbomb.common.messages.ElevatorMovesMessage;
import jbomb.common.messages.ExploitBombMessage;
import jbomb.common.messages.NewPlayerMessage;
import jbomb.common.messages.RemovePlayerMessage;
import jbomb.common.messages.StartGameMessage;
import jbomb.common.messages.ThrowBombMessage;
import jbomb.common.scene.Elevator;
import jbomb.common.utils.GeometryUtils;
import org.apache.log4j.xml.DOMConfigurator;

public abstract class BaseGame extends SimpleApplication {
    
    private BulletAppState bulletAppState = new BulletAppState();
    private RunningAppState runningAppState = createRunningAppState();
    private AbstractManager<?> manager = createManager();
    
    public BaseGame() {
        URL urlConfig = BaseGame.class.getResource("/jbomb/common/config/log4j.xml");
        DOMConfigurator.configure(urlConfig);
        loadLog4jConfig();
    }

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
        bulletAppState.getPhysicsSpace().enableDebug(assetManager);
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
        
        makeWallsAtBase();
        makeWallsAtFirstPlatform();
    }
    
    private void initScene() {
        new Elevator(new Vector3f(-9f, .1f, 18f), 9.9f, .1f, 3f, true, getElevatorServerControlled());
        new Elevator(new Vector3f(9f, .1f, -18f), 9.9f, .1f, 3f, true, getElevatorServerControlled());
        new Elevator(new Vector3f(18f, 9.9f, -9f), 9.9f, .1f, 3f, false, getElevatorServerControlled());
        new Elevator(new Vector3f(-18f, 9.9f, 9f), 9.9f, .1f, 3f, false, getElevatorServerControlled());

        new Elevator(new Vector3f(-9f, 10.1f, 18f), 19.9f, 10.1f, 3f, true, getElevatorServerControlled());
        new Elevator(new Vector3f(9f, 10.1f, -18f), 19.9f, 10.1f, 3f, true, getElevatorServerControlled());
        new Elevator(new Vector3f(16f, 19.9f, 9f), 19.9f, 10.1f, 3f, false, getElevatorServerControlled());
        new Elevator(new Vector3f(-16f, 19.9f, -9f), 19.9f, 10.1f, 3f, false, getElevatorServerControlled());

        new Elevator(new Vector3f(16f, 29.9f, 3f), 29.9f, 20.1f, 3f, false, getElevatorServerControlled());
        new Elevator(new Vector3f(-16f, 29.9f, -3f), 29.9f, 20.1f, 3f, false, getElevatorServerControlled());
        new Elevator(new Vector3f(14f, 20.1f, -16f), 29.9f, 20.1f, 3f, true, getElevatorServerControlled());
        new Elevator(new Vector3f(-14f, 20.1f, 16f), 29.9f, 20.1f, 3f, true, getElevatorServerControlled());
    }
    
    public void makeWallsAtFirstPlatform() {
        String boxTextures = "textures/boxes/", glass = "textures/glass/sunbeam_t2.png", basePilars = boxTextures + "w_darkgray.png";
        GeometryUtils.makeCube(.5f, .5f, .5f, "1st_wall", boxTextures + "w_blue2.png", new Vector3f(15.5f, 10.5f, 7.5f), new Vector2f(.5f, .5f));
        GeometryUtils.makeCube(.5f, 1f, 2f, "1st_wall", boxTextures + "w_blue2.png", new Vector3f(15.5f, 11f, 5f), new Vector2f(2f, 1f));
        GeometryUtils.makeCube(.5f, .5f, 1f, "1st_wall", boxTextures + "w_blue2.png", new Vector3f(15.5f, 12.5f, 6f), new Vector2f(1f, .5f));
        GeometryUtils.makeCube(.5f, .5f, .5f, "1st_wall", boxTextures + "w_blue2.png", new Vector3f(15.5f, 13.5f, 6.5f), new Vector2f(.5f, .5f));
        GeometryUtils.makeCube(.5f, .5f, .5f, "1st_wall", boxTextures + "w_blue2.png", new Vector3f(15.5f, 12.5f, 3.5f), new Vector2f(.5f, .5f));
        GeometryUtils.makeCube(.5f, .5f, .5f, "1st_wall", boxTextures + "w_blue2.png", new Vector3f(15.5f, 10.5f, 2.5f), new Vector2f(-.5f, .5f));
        GeometryUtils.makeCube(.5f, 1.5f, 1.5f, "1st_wall", boxTextures + "w_blue2.png", new Vector3f(15.5f, 11.5f, .5f), new Vector2f(1.5f, 1.5f));
        GeometryUtils.makeCube(.5f, .5f, .5f, "1st_wall", boxTextures + "w_blue2.png", new Vector3f(15.5f, 13.5f, 1.5f), new Vector2f(-.5f, .5f));
        GeometryUtils.makeCube(.5f, .5f, .5f, "1st_wall", boxTextures + "w_blue2.png", new Vector3f(15.5f, 13.5f, -.5f), new Vector2f(-.5f, .5f));
        GeometryUtils.makeCube(.5f, .5f, .5f, "1st_wall", boxTextures + "w_blue2.png", new Vector3f(15.5f, 10.5f, -1.5f), new Vector2f(-.5f, .5f));
        GeometryUtils.makeCube(.5f, .5f, .5f, "1st_wall", boxTextures + "w_blue2.png", new Vector3f(15.5f, 12.5f, -1.5f), new Vector2f(-.5f, .5f));
        GeometryUtils.makeCube(.5f, .5f, .5f, "1st_wall", boxTextures + "w_blue2.png", new Vector3f(15.5f, 11.5f, -3.5f), new Vector2f(.5f, .5f));
        GeometryUtils.makeCube(.5f, 1.5f, 1f, "1st_wall", boxTextures + "w_blue2.png", new Vector3f(15.5f, 11.5f, -5f), new Vector2f(1f, 1.5f));
        GeometryUtils.makeCube(.5f, 1f, .5f, "1st_wall", boxTextures + "w_blue2.png", new Vector3f(15.5f, 11f, -6.5f), new Vector2f(-.5f, 1f));
        GeometryUtils.makeCube(.5f, .5f, .5f, "1st_wall", boxTextures + "w_blue2.png", new Vector3f(15.5f, 13.5f, -4.5f), new Vector2f(.5f, .5f));
        GeometryUtils.makeCube(.5f, .5f, .5f, "1st_wall", boxTextures + "w_blue2.png", new Vector3f(15.5f, 10.5f, -7.5f), new Vector2f(.5f, .5f));
        GeometryUtils.makeCube(.1f, 2f, 1.5f, "1st_wall", glass, new Vector3f(15.525f, 12f, -2.5f), new Vector2f(1.5f, 2f), true);
        GeometryUtils.makeCube(.5f, .25f, 8f, "1st_wall", boxTextures + "w_darkgray.png", new Vector3f(15.5f, 9.75f, 0f), new Vector2f(8f, .5f));
        
        GeometryUtils.makeCube(.5f, .5f, .5f, "2nd_wall", boxTextures + "w_darkblue.png", new Vector3f(15.5f, 10.5f, 10.5f), new Vector2f(.5f, .5f));
        GeometryUtils.makeCube(.5f, 1f, .5f, "2nd_wall", boxTextures + "w_darkblue.png", new Vector3f(15.5f, 11f, 11.5f), new Vector2f(.5f, 1f));
        GeometryUtils.makeCube(.5f, .5f, .5f, "2nd_wall", boxTextures + "w_blue1.png", new Vector3f(15.5f, 10.5f, 12.5f), new Vector2f(.5f, .5f));
        GeometryUtils.makeCube(.5f, .5f, .5f, "2nd_wall", boxTextures + "w_darkblue.png", new Vector3f(15.5f, 12.5f, 12.5f), new Vector2f(.5f, .5f));
        GeometryUtils.makeCube(.1f, .5f, .5f, "2nd_wall", glass, new Vector3f(15.525f, 11.5f, 12.5f), new Vector2f(.5f, .5f), true);
        GeometryUtils.makeCube(.5f, 1.5f, .5f, "2nd_wall", boxTextures + "w_darkblue.png", new Vector3f(15.5f, 11.5f, 13.5f), new Vector2f(.5f, 1.5f));
        GeometryUtils.makeCube(.5f, 1f, .5f, "2nd_wall", boxTextures + "w_darkblue.png", new Vector3f(15.5f, 11f, 14.5f), new Vector2f(.5f, 1f));
        GeometryUtils.makeCube(.5f, .5f, .5f, "2nd_wall", boxTextures + "w_blue1.png", new Vector3f(15.5f, 12.5f, 14.5f), new Vector2f(.5f, .5f));
        GeometryUtils.makeCube(.5f, .5f, .5f, "2nd_wall", boxTextures + "w_darkblue.png", new Vector3f(15.5f, 13.5f, 14.5f), new Vector2f(.5f, .5f));
        GeometryUtils.makeCube(.5f, 3f, .5f, "2nd_wall", boxTextures + "w_darkblue.png", new Vector3f(15.5f, 13f, 15.5f), new Vector2f(.5f, 3f));
        GeometryUtils.makeCube(.5f, 1f, .5f, "2nd_wall", boxTextures + "w_darkblue.png", new Vector3f(14.5f, 11f, 15.5f), new Vector2f(.5f, 1f));
        GeometryUtils.makeCube(.5f, .5f, .5f, "2nd_wall", boxTextures + "w_blue1.png", new Vector3f(14.5f, 14.5f, 15.5f), new Vector2f(.5f, .5f));
        GeometryUtils.makeCube(1f, 1f, .5f, "2nd_wall", boxTextures + "w_darkblue.png", new Vector3f(14f, 13f, 15.5f), new Vector2f(1f, 1f));
        GeometryUtils.makeCube(.5f, .5f, .5f, "2nd_wall", boxTextures + "w_darkblue.png", new Vector3f(13.5f, 10.5f, 15.5f), new Vector2f(.5f, .5f));
        GeometryUtils.makeCube(.5f, .5f, .5f, "2nd_wall", boxTextures + "w_blue1.png", new Vector3f(13.5f, 11.5f, 15.5f), new Vector2f(.5f, .5f));
        GeometryUtils.makeCube(.5f, 1.5f, .5f, "2nd_wall", boxTextures + "w_darkblue.png", new Vector3f(12.5f, 11.5f, 15.5f), new Vector2f(.5f, 1.5f));
        GeometryUtils.makeCube(.5f, .5f, .5f, "2nd_wall", boxTextures + "w_blue1.png", new Vector3f(11.5f, 10.5f, 15.5f), new Vector2f(.5f, .5f));
        GeometryUtils.makeCube(.5f, .5f, .5f, "2nd_wall", boxTextures + "w_darkblue.png", new Vector3f(10.5f, 10.5f, 15.5f), new Vector2f(.5f, .5f));
        GeometryUtils.makeCube(.5f, .5f, .5f, "2nd_wall", boxTextures + "w_blue1.png", new Vector3f(9.5f, 10.5f, 15.5f), new Vector2f(.5f, .5f));
        GeometryUtils.makeCube(.5f, .5f, .5f, "2nd_wall", boxTextures + "w_darkblue.png", new Vector3f(8.5f, 10.5f, 15.5f), new Vector2f(.5f, .5f));
        GeometryUtils.makeCube(1f, .5f, .5f, "2nd_wall", boxTextures + "w_darkblue.png", new Vector3f(10f, 11.5f, 15.5f), new Vector2f(1f, .5f));
        GeometryUtils.makeCube(.5f, .5f, .5f, "2nd_wall", boxTextures + "w_darkblue.png", new Vector3f(9.5f, 12.5f, 15.5f), new Vector2f(.5f, .5f));
        GeometryUtils.makeCube(2f, .1f, 2.5f, "2nd_wall", boxTextures + "f_blue.png", new Vector3f(18f, 13.9f, 17.5f), new Vector2f(2.5f, 2f), true);
        GeometryUtils.makeCube(1f, .1f, 2f, "2nd_wall", boxTextures + "f_blue.png", new Vector3f(15f, 13.9f, 18f), new Vector2f(2f, 1f), true);
        GeometryUtils.makeCube(.5f, .25f, 3f, "2nd_wall", boxTextures + "w_darkgray.png", new Vector3f(15.5f, 9.75f, 13f), new Vector2f(3f, .5f));
        GeometryUtils.makeCube(3.5f, .25f, .5f, "2md_wall", boxTextures + "w_darkgray.png", new Vector3f(11.5f, 9.75f, 15.5f), new Vector2f(3.5f, .5f));
        
        GeometryUtils.makeCube(5f, .25f, .5f, "3rd_wall", boxTextures + "w_darkgray.png", new Vector3f(0f, 9.75f, 15.5f), new Vector2f(5f, .5f));
        GeometryUtils.makeCube(1f, 1f, .5f, "3rd_wall", boxTextures + "w_blue2.png", new Vector3f(3f, 11f, 15.5f), new Vector2f(1f, 1f));
        GeometryUtils.makeCube(1f, 1f, .5f, "3rd_wall", boxTextures + "w_blue2.png", new Vector3f(-3f, 11f, 15.5f), new Vector2f(1f, 1f));
        GeometryUtils.makeCube(.5f, .5f, .5f, "3rd_wall", boxTextures + "w_blue2.png", new Vector3f(4.5f, 10.5f, 15.5f), new Vector2f(-.5f, .5f));
        GeometryUtils.makeCube(.5f, .5f, .5f, "3rd_wall", boxTextures + "w_blue2.png", new Vector3f(-4.5f, 10.5f, 15.5f), new Vector2f(.5f, .5f));
        GeometryUtils.makeCube(.5f, .5f, .5f, "3rd_wall", boxTextures + "w_blue2.png", new Vector3f(2.5f, 12.5f, 15.5f), new Vector2f(-.5f, .5f));
        GeometryUtils.makeCube(.5f, .5f, .5f, "3rd_wall", boxTextures + "w_blue2.png", new Vector3f(-2.5f, 12.5f, 15.5f), new Vector2f(.5f, .5f));
        GeometryUtils.makeCube(.5f, .5f, .5f, "3rd_wall", boxTextures + "w_blue1.png", new Vector3f(1.5f, 13.5f, 15.5f), new Vector2f(.5f, .5f));
        GeometryUtils.makeCube(.5f, .5f, .5f, "3rd_wall", boxTextures + "w_blue1.png", new Vector3f(-1.5f, 13.5f, 15.5f), new Vector2f(.5f, .5f));
        GeometryUtils.makeCube(1f, .5f, .5f, "3rd_wall", boxTextures + "w_blue1.png", new Vector3f(0f, 14.5f, 15.5f), new Vector2f(1f, .5f));
        
        GeometryUtils.makeCube(.5f, 1.5f, .5f, "4th_wall", boxTextures + "w_blue2.png", new Vector3f(-5.5f, 11.5f, -2.5f), new Vector2f(.5f, -1.5f));
        GeometryUtils.makeCube(.5f, 1.5f, .5f, "4th_wall", boxTextures + "w_blue2.png", new Vector3f(-9.5f, 11.5f, -2.5f), new Vector2f(.5f, -1.5f));
        GeometryUtils.makeCube(2.5f, .25f, .5f, "4th_wall", boxTextures + "w_darkgray.png", new Vector3f(-7.5f, 9.75f, -2.5f), new Vector2f(.5f, 2.5f));
        GeometryUtils.makeCube(1.5f, 1.3f, .1f, "4th_wall", glass, new Vector3f(-7.5f, 11.3f, -2.525f), new Vector2f(1.5f, 1.3f), true);
        GeometryUtils.makeCube(2.5f, .25f, .5f, "4th_wall", boxTextures + "w_darkgray.png", new Vector3f(-12.5f, 9.75f, 2.5f), new Vector2f(2.5f, .5f));
        GeometryUtils.makeCube(.5f, 1f, .5f, "4th_wall", boxTextures + "w_blue2.png", new Vector3f(-10.5f, 11f, 2.5f), new Vector2f(.5f, 1f));
        GeometryUtils.makeCube(.25f, 1.5f, .25f, "4th_wall", boxTextures + "w_blue1.png", new Vector3f(-13f, 11.5f, 2.5f), new Vector2f(.5f, 3f));
        GeometryUtils.makeCube(.5f, 2f, .5f, "4th_wall", boxTextures + "w_blue2.png", new Vector3f(-15.5f, 12f, 2.5f), new Vector2f(.5f, -2f));
        GeometryUtils.makeCube(.875f, 1.25f, .1f, "4th_wall", glass, new Vector3f(-11.875f, 11.25f, 2.525f), new Vector2f(1f, 1f), true);
        GeometryUtils.makeCube(.875f, 1.75f, .1f, "4th_wall", glass, new Vector3f(-14.125f, 11.75f, 2.525f), new Vector2f(1f, 1f), true);
        GeometryUtils.makeCube(.5f, .25f, 4.5f, "4th_wall", boxTextures + "w_darkgray.png", new Vector3f(-15.5f, 9.75f, -1.5f), new Vector2f(4.5f, .5f));
        GeometryUtils.makeCube(.5f, 1.5f, .5f, "4th_wall", boxTextures + "w_blue2.png", new Vector3f(-15.5f, 11.5f, -2.5f), new Vector2f(.5f, -1.5f));
        GeometryUtils.makeCube(.5f, 1f, 1f, "4th_wall", boxTextures + "w_blue2.png", new Vector3f(-15.5f, 11f, -4f), new Vector2f(1f, 1f));
        GeometryUtils.makeCube(.5f, .5f, .5f, "4th_wall", boxTextures + "w_blue2.png", new Vector3f(-15.5f, 10.5f, -5.5f), new Vector2f(.5f, .5f));
        
        GeometryUtils.makeCube(3.5f, .25f, .5f, "5th_wall", boxTextures + "w_darkgray.png", new Vector3f(.5f, 9.75f, -15.5f), new Vector2f(3.5f, .5f));
        GeometryUtils.makeCube(1.5f, 1f, .5f, "5th_wall", boxTextures + "w_blue2.png", new Vector3f(-1.5f, 11f, -15.5f), new Vector2f(-1.5f, 1f));
        GeometryUtils.makeCube(.5f, .5f, .5f, "5th_wall", boxTextures + "w_blue2.png", new Vector3f(-1.5f, 12.5f, -15.5f), new Vector2f(.5f, .5f));
        GeometryUtils.makeCube(1f, 1.5f, .5f, "5th_wall", boxTextures + "w_blue2.png", new Vector3f(1f, 11.5f, -15.5f), new Vector2f(1f, 1.5f));
        GeometryUtils.makeCube(.5f, .5f, .5f, "5th_wall", boxTextures + "w_blue2.png", new Vector3f(1.5f, 13.5f, -15.5f), new Vector2f(.5f, .5f));
        GeometryUtils.makeCube(.5f, .5f, .5f, "5th_wall", boxTextures + "w_blue2.png", new Vector3f(2.5f, 10.5f, -15.5f), new Vector2f(.5f, .5f));
        GeometryUtils.makeCube(.5f, .5f, .5f, "5th_wall", boxTextures + "w_blue2.png", new Vector3f(2.5f, 12.5f, -15.5f), new Vector2f(.5f, .5f));
        GeometryUtils.makeCube(.5f, 1f, .5f, "5th_wall", boxTextures + "w_blue2.png", new Vector3f(3.5f, 11f, -15.5f), new Vector2f(-.5f, 1f));
        
        GeometryUtils.makeCube(.5f, .25f, 1.5f, "6th_wall", boxTextures + "w_darkgray.png", new Vector3f(-15.5f, 9.75f, -14.5f), new Vector2f(1.5f, .5f));
        GeometryUtils.makeCube(2f, .25f, .5f, "6th_wall", boxTextures + "w_darkgray.png", new Vector3f(-13f, 9.75f, -15.5f), new Vector2f(2f, .5f));
        GeometryUtils.makeCube(.5f, .5f, .5f, "6th_wall", boxTextures + "w_blue1.png", new Vector3f(-15.5f, 10.5f, -13.5f), new Vector2f(.5f, .5f));
        GeometryUtils.makeCube(.5f, .5f, .5f, "6th_wall", boxTextures + "w_darkblue.png", new Vector3f(-15.5f, 11.5f, -13.5f), new Vector2f(.5f, .5f));
        GeometryUtils.makeCube(.5f, 1.5f, .5f, "6th_wall", boxTextures + "w_darkblue.png", new Vector3f(-15.5f, 11.5f, -14.5f), new Vector2f(.5f, 1.5f));
        GeometryUtils.makeCube(.5f, .5f, .5f, "6th_wall", boxTextures + "w_blue1.png", new Vector3f(-15.5f, 13.5f, -14.5f), new Vector2f(.5f, .5f));
        GeometryUtils.makeCube(.5f, 3f, .5f, "6th_wall", boxTextures + "w_darkblue.png", new Vector3f(-15.5f, 13f, -15.5f), new Vector2f(.5f, 3f));
        GeometryUtils.makeCube(.5f, .5f, .5f, "6th_wall", boxTextures + "w_blue1.png", new Vector3f(-14.5f, 10.5f, -15.5f), new Vector2f(.5f, .5f));
        GeometryUtils.makeCube(.5f, .5f, .5f, "6th_wall", boxTextures + "w_darkblue.png", new Vector3f(-14.5f, 11.5f, -15.5f), new Vector2f(.5f, .5f));
        GeometryUtils.makeCube(.5f, .5f, .5f, "6th_wall", boxTextures + "w_blue1.png", new Vector3f(-14.5f, 12.5f, -15.5f), new Vector2f(.5f, .5f));
        GeometryUtils.makeCube(.5f, .5f, .5f, "6th_wall", boxTextures + "w_darkblue.png", new Vector3f(-14.5f, 13.5f, -15.5f), new Vector2f(.5f, .5f));
        GeometryUtils.makeCube(.5f, .5f, .5f, "6th_wall", boxTextures + "w_blue1.png", new Vector3f(-14.5f, 14.5f, -15.5f), new Vector2f(.5f, .5f));
        GeometryUtils.makeCube(1f, 1f, .5f, "6th_wall", boxTextures + "w_darkblue.png", new Vector3f(-13f, 11f, -15.5f), new Vector2f(1f, 1f));
        GeometryUtils.makeCube(.5f, .5f, .5f, "6th_wall", boxTextures + "w_blue1.png", new Vector3f(-12.5f, 12.5f, -15.5f), new Vector2f(.5f, .5f));
        GeometryUtils.makeCube(.5f, 1f, .5f, "6th_wall", boxTextures + "w_darkblue.png", new Vector3f(-12.5f, 14f, -15.5f), new Vector2f(.5f, 1f));
        GeometryUtils.makeCube(.5f, .5f, .5f, "6th_wall", boxTextures + "w_darkblue.png", new Vector3f(-11.5f, 10.5f, -15.5f), new Vector2f(.5f, .5f));
        GeometryUtils.makeCube(.5f, .5f, .5f, "6th_wall", boxTextures + "w_darkblue.png", new Vector3f(-11.5f, 12.5f, -15.5f), new Vector2f(.5f, .5f));
        
        GeometryUtils.makeCube(.5f, 5f, .5f, "1st_platformPilar", basePilars, new Vector3f(4.5f, 15f, 4.5f), new Vector2f(.5f, 5f));
        GeometryUtils.makeCube(.5f, 5f, .5f, "1st_platformPilar", basePilars, new Vector3f(4.5f, 15f, -4.5f), new Vector2f(.5f, 5f));
        GeometryUtils.makeCube(.5f, 5f, .5f, "1st_platformPilar", basePilars, new Vector3f(-4.5f, 15f, 4.5f), new Vector2f(.5f, 5f));
        GeometryUtils.makeCube(.5f, 5f, .5f, "1st_platformPilar", basePilars, new Vector3f(-4.5f, 15f, -4.5f), new Vector2f(.5f, 5f));
        GeometryUtils.makeCube(4f, .5f, .5f, "1st_platformPilar", basePilars, new Vector3f(0f, 19.5f, 4.5f), new Vector2f(4f, .5f));
        GeometryUtils.makeCube(4f, .5f, .5f, "1st_platformPilar", basePilars, new Vector3f(0f, 19.5f, -4.5f), new Vector2f(4f, .5f));
        GeometryUtils.makeCube(.5f, .5f, 4f, "1st_platformPilar", basePilars, new Vector3f(4.5f, 19.5f, 0f), new Vector2f(4f, .5f));
        GeometryUtils.makeCube(.5f, .5f, 4f, "1st_platformPilar", basePilars, new Vector3f(-4.5f, 19.5f, 0f), new Vector2f(4f, .5f));
        GeometryUtils.makeCube(.5f, 1.5f, .5f, "1st_platformPilar", basePilars, new Vector3f(3.5f, 11.5f, 4.5f), new Vector2f(.5f, 1.5f));
        GeometryUtils.makeCube(.5f, 1.5f, .5f, "1st_platformPilar", basePilars, new Vector3f(3.5f, 11.5f, -4.5f), new Vector2f(.5f, 1.5f));
        GeometryUtils.makeCube(.5f, 1.5f, .5f, "1st_platformPilar", basePilars, new Vector3f(4.5f, 11.5f, 3.5f), new Vector2f(.5f, 1.5f));
        GeometryUtils.makeCube(.5f, 1.5f, .5f, "1st_platformPilar", basePilars, new Vector3f(4.5f, 11.5f, -3.5f), new Vector2f(.5f, 1.5f));
        GeometryUtils.makeCube(.5f, 1.5f, .5f, "1st_platformPilar", basePilars, new Vector3f(-3.5f, 11.5f, 4.5f), new Vector2f(.5f, 1.5f));
        GeometryUtils.makeCube(.5f, 1.5f, .5f, "1st_platformPilar", basePilars, new Vector3f(-3.5f, 11.5f, -4.5f), new Vector2f(.5f, 1.5f));
        GeometryUtils.makeCube(.5f, 1.5f, .5f, "1st_platformPilar", basePilars, new Vector3f(-4.5f, 11.5f, 3.5f), new Vector2f(.5f, 1.5f));
        GeometryUtils.makeCube(.5f, 1.5f, .5f, "1st_platformPilar", basePilars, new Vector3f(-4.5f, 11.5f, -3.5f), new Vector2f(.5f, 1.5f));
        GeometryUtils.makeCube(.5f, .5f, .5f, "1st_platformPilar", basePilars, new Vector3f(2.5f, 10.5f, 4.5f), new Vector2f(.5f, .5f));
        GeometryUtils.makeCube(.5f, .5f, .5f, "1st_platformPilar", basePilars, new Vector3f(2.5f, 10.5f, -4.5f), new Vector2f(.5f, .5f));
        GeometryUtils.makeCube(.5f, .5f, .5f, "1st_platformPilar", basePilars, new Vector3f(-2.5f, 10.5f, 4.5f), new Vector2f(.5f, .5f));
        GeometryUtils.makeCube(.5f, .5f, .5f, "1st_platformPilar", basePilars, new Vector3f(-2.5f, 10.5f, -4.5f), new Vector2f(.5f, .5f));
        GeometryUtils.makeCube(.5f, .5f, .5f, "1st_platformPilar", basePilars, new Vector3f(4.5f, 10.5f, 2.5f), new Vector2f(.5f, .5f));
        GeometryUtils.makeCube(.5f, .5f, .5f, "1st_platformPilar", basePilars, new Vector3f(4.5f, 10.5f, -2.5f), new Vector2f(.5f, .5f));
        GeometryUtils.makeCube(.5f, .5f, .5f, "1st_platformPilar", basePilars, new Vector3f(-4.5f, 10.5f, 2.5f), new Vector2f(.5f, .5f));
        GeometryUtils.makeCube(.5f, .5f, .5f, "1st_platformPilar", basePilars, new Vector3f(-4.5f, 10.5f, -2.5f), new Vector2f(.5f, .5f));       
        GeometryUtils.makeCube(.5f, 1.5f, .5f, "1st_platformPilar", basePilars, new Vector3f(3.5f, 17.5f, 4.5f), new Vector2f(.5f, 1.5f));
        GeometryUtils.makeCube(.5f, 1.5f, .5f, "1st_platformPilar", basePilars, new Vector3f(3.5f, 17.5f, -4.5f), new Vector2f(.5f, 1.5f));
        GeometryUtils.makeCube(.5f, 1.5f, .5f, "1st_platformPilar", basePilars, new Vector3f(4.5f, 17.5f, 3.5f), new Vector2f(.5f, 1.5f));
        GeometryUtils.makeCube(.5f, 1.5f, .5f, "1st_platformPilar", basePilars, new Vector3f(4.5f, 17.5f, -3.5f), new Vector2f(.5f, 1.5f));
        GeometryUtils.makeCube(.5f, 1.5f, .5f, "1st_platformPilar", basePilars, new Vector3f(-3.5f, 17.5f, 4.5f), new Vector2f(.5f, 1.5f));
        GeometryUtils.makeCube(.5f, 1.5f, .5f, "1st_platformPilar", basePilars, new Vector3f(-3.5f, 17.5f, -4.5f), new Vector2f(.5f, 1.5f));
        GeometryUtils.makeCube(.5f, 1.5f, .5f, "1st_platformPilar", basePilars, new Vector3f(-4.5f, 17.5f, 3.5f), new Vector2f(.5f, 1.5f));
        GeometryUtils.makeCube(.5f, 1.5f, .5f, "1st_platformPilar", basePilars, new Vector3f(-4.5f, 17.5f, -3.5f), new Vector2f(.5f, 1.5f));
        GeometryUtils.makeCube(.5f, .5f, .5f, "1st_platformPilar", basePilars, new Vector3f(2.5f, 18.5f, 4.5f), new Vector2f(.5f, .5f));
        GeometryUtils.makeCube(.5f, .5f, .5f, "1st_platformPilar", basePilars, new Vector3f(2.5f, 18.5f, -4.5f), new Vector2f(.5f, .5f));
        GeometryUtils.makeCube(.5f, .5f, .5f, "1st_platformPilar", basePilars, new Vector3f(-2.5f, 18.5f, 4.5f), new Vector2f(.5f, .5f));
        GeometryUtils.makeCube(.5f, .5f, .5f, "1st_platformPilar", basePilars, new Vector3f(-2.5f, 18.5f, -4.5f), new Vector2f(.5f, .5f));
        GeometryUtils.makeCube(.5f, .5f, .5f, "1st_platformPilar", basePilars, new Vector3f(4.5f, 18.5f, 2.5f), new Vector2f(.5f, .5f));
        GeometryUtils.makeCube(.5f, .5f, .5f, "1st_platformPilar", basePilars, new Vector3f(4.5f, 18.5f, -2.5f), new Vector2f(.5f, .5f));
        GeometryUtils.makeCube(.5f, .5f, .5f, "1st_platformPilar", basePilars, new Vector3f(-4.5f, 18.5f, 2.5f), new Vector2f(.5f, .5f));
        GeometryUtils.makeCube(.5f, .5f, .5f, "1st_platformPilar", basePilars, new Vector3f(-4.5f, 18.5f, -2.5f), new Vector2f(.5f, .5f));
        GeometryUtils.makeCube(.5f, 1.5f, .1f, "1st_plateformPilar", glass, new Vector3f(3.5f, 14.5f, 4.525f), new Vector2f(.5f, 1.5f), true);
        GeometryUtils.makeCube(.5f, 1.5f, .1f, "1st_plateformPilar", glass, new Vector3f(3.5f, 14.5f, -4.525f), new Vector2f(.5f, 1.5f), true);
        GeometryUtils.makeCube(.5f, 1.5f, .1f, "1st_plateformPilar", glass, new Vector3f(-3.5f, 14.5f, 4.525f), new Vector2f(.5f, 1.5f), true);
        GeometryUtils.makeCube(.5f, 1.5f, .1f, "1st_plateformPilar", glass, new Vector3f(-3.5f, 14.5f, -4.525f), new Vector2f(.5f, 1.5f), true);
        GeometryUtils.makeCube(.1f, 1.5f, .5f, "1st_plateformPilar", glass, new Vector3f(4.525f, 14.5f, 3.5f), new Vector2f(.5f, 1.5f), true);
        GeometryUtils.makeCube(.1f, 1.5f, .5f, "1st_plateformPilar", glass, new Vector3f(4.525f, 14.5f, -3.5f), new Vector2f(.5f, 1.5f), true);
        GeometryUtils.makeCube(.1f, 1.5f, .5f, "1st_plateformPilar", glass, new Vector3f(-4.525f, 14.5f, 3.5f), new Vector2f(.5f, 1.5f), true);
        GeometryUtils.makeCube(.1f, 1.5f, .5f, "1st_plateformPilar", glass, new Vector3f(-4.525f, 14.5f, -3.5f), new Vector2f(.5f, 1.5f), true);
    }
    
    public void makeWallsAtBase() {
        String basePilars = "textures/boxes/w_darkgray.png";
        //pilares
        GeometryUtils.makeCube(.5f, 4.5f, .5f, "basePilar", basePilars, new Vector3f(4.5f, 5.5f, 4.5f), new Vector2f(-.5f, 4.5f));
        GeometryUtils.makeCube(.5f, 4.5f, .5f, "basePilar", basePilars, new Vector3f(4.5f, 5.5f, -4.5f), new Vector2f(-.5f, 4.5f));
        GeometryUtils.makeCube(.5f, 4.5f, .5f, "basePilar", basePilars, new Vector3f(-4.5f, 5.5f, 4.5f), new Vector2f(-.5f, 4.5f));
        GeometryUtils.makeCube(.5f, 4.5f, .5f, "basePilar", basePilars, new Vector3f(-4.5f, 5.5f, -4.5f), new Vector2f(-.5f, 4.5f));
        //base pilares
        GeometryUtils.makeCube(1f, .5f, 1f, "basePilar", basePilars, new Vector3f(4.5f, .5f, 4.5f), new Vector2f(1f, .5f));
        GeometryUtils.makeCube(1f, .5f, 1f, "basePilar", basePilars, new Vector3f(4.5f, .5f, -4.5f), new Vector2f(1f, .5f));
        GeometryUtils.makeCube(1f, .5f, 1f, "basePilar", basePilars, new Vector3f(-4.5f, .5f, 4.5f), new Vector2f(1f, .5f));
        GeometryUtils.makeCube(1f, .5f, 1f, "basePilar", basePilars, new Vector3f(-4.5f, .5f, -4.5f), new Vector2f(1f, .5f));
        //soportes superiores
        GeometryUtils.makeCube(4f, .5f, .5f, "basePilar", basePilars, new Vector3f(0f, 9.5f, 4.5f), new Vector2f(4f, .5f));
        GeometryUtils.makeCube(4f, .5f, .5f, "basePilar", basePilars, new Vector3f(0f, 9.5f, -4.5f), new Vector2f(4f, .5f));
        GeometryUtils.makeCube(.5f, .5f, 4f, "basePilar", basePilars, new Vector3f(4.5f, 9.5f, 0f), new Vector2f(4f, .5f));
        GeometryUtils.makeCube(.5f, .5f, 4f, "basePilar", basePilars, new Vector3f(-4.5f, 9.5f, 0f), new Vector2f(4f, .5f));
        //arco 1
        GeometryUtils.makeCube(1f, 1f, .5f, "basePilar", basePilars, new Vector3f(3f, 8f, 4.5f), new Vector2f(1f, 1f));
        GeometryUtils.makeCube(1f, 1f, .5f, "basePilar", basePilars, new Vector3f(-3f, 8f, 4.5f), new Vector2f(1f, 1f));
        GeometryUtils.makeCube(.5f, .5f, .5f, "basePilar", basePilars, new Vector3f(3.5f, 6.5f, 4.5f), new Vector2f(.5f, .5f));
        GeometryUtils.makeCube(.5f, .5f, .5f, "basePilar", basePilars, new Vector3f(1.5f, 8.5f, 4.5f), new Vector2f(.5f, .5f));
        GeometryUtils.makeCube(.5f, .5f, .5f, "basePilar", basePilars, new Vector3f(-3.5f, 6.5f, 4.5f), new Vector2f(.5f, .5f));
        GeometryUtils.makeCube(.5f, .5f, .5f, "basePilar", basePilars, new Vector3f(-1.5f, 8.5f, 4.5f), new Vector2f(.5f, .5f));
        //arco paralelo 1
        GeometryUtils.makeCube(1f, 1f, .5f, "basePilar", basePilars, new Vector3f(3f, 8f, -4.5f), new Vector2f(1f, 1f));
        GeometryUtils.makeCube(1f, 1f, .5f, "basePilar", basePilars, new Vector3f(-3f, 8f, -4.5f), new Vector2f(1f, 1f));
        GeometryUtils.makeCube(.5f, .5f, .5f, "basePilar", basePilars, new Vector3f(3.5f, 6.5f, -4.5f), new Vector2f(.5f, .5f));
        GeometryUtils.makeCube(.5f, .5f, .5f, "basePilar", basePilars, new Vector3f(1.5f, 8.5f, -4.5f), new Vector2f(.5f, .5f));
        GeometryUtils.makeCube(.5f, .5f, .5f, "basePilar", basePilars, new Vector3f(-3.5f, 6.5f, -4.5f), new Vector2f(.5f, .5f));
        GeometryUtils.makeCube(.5f, .5f, .5f, "basePilar", basePilars, new Vector3f(-1.5f, 8.5f, -4.5f), new Vector2f(.5f, .5f));
        //arco 2
        GeometryUtils.makeCube(.5f, 1f, 1f, "basePilar", basePilars, new Vector3f(4.5f, 8f, 3f), new Vector2f(1f, 1f));
        GeometryUtils.makeCube(.5f, 1f, 1f, "basePilar", basePilars, new Vector3f(-4.5f, 8f, 3f), new Vector2f(1f, 1f));
        GeometryUtils.makeCube(.5f, .5f, .5f, "basePilar", basePilars, new Vector3f(4.5f, 6.5f, 3.5f), new Vector2f(.5f, .5f));
        GeometryUtils.makeCube(.5f, .5f, .5f, "basePilar", basePilars, new Vector3f(4.5f, 8.5f, 1.5f), new Vector2f(.5f, .5f));
        GeometryUtils.makeCube(.5f, .5f, .5f, "basePilar", basePilars, new Vector3f(-4.5f, 6.5f, 3.5f), new Vector2f(.5f, .5f));
        GeometryUtils.makeCube(.5f, .5f, .5f, "basePilar", basePilars, new Vector3f(-4.5f, 8.5f, 1.5f), new Vector2f(.5f, .5f));
        //arco paralelo 2
        GeometryUtils.makeCube(.5f, 1f, 1f, "basePilar", basePilars, new Vector3f(4.5f, 8f, -3f), new Vector2f(1f, 1f));
        GeometryUtils.makeCube(.5f, 1f, 1f, "basePilar", basePilars, new Vector3f(-4.5f, 8f, -3f), new Vector2f(1f, 1f));
        GeometryUtils.makeCube(.5f, .5f, .5f, "basePilar", basePilars, new Vector3f(4.5f, 6.5f, -3.5f), new Vector2f(.5f, .5f));
        GeometryUtils.makeCube(.5f, .5f, .5f, "basePilar", basePilars, new Vector3f(4.5f, 8.5f, -1.5f), new Vector2f(.5f, .5f));
        GeometryUtils.makeCube(.5f, .5f, .5f, "basePilar", basePilars, new Vector3f(-4.5f, 6.5f, -3.5f), new Vector2f(.5f, .5f));
        GeometryUtils.makeCube(.5f, .5f, .5f, "basePilar", basePilars, new Vector3f(-4.5f, 8.5f, -1.5f), new Vector2f(.5f, .5f));
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
                4f, 0.1f, 4f, name, texture, new Vector3f(0f, height, 0f), new Vector2f(4f, 4f), true);
        GeometryUtils.makeCube(
                2f, 0.1f, 5f, name, texture, new Vector3f(0f, height, 10f), new Vector2f(5f, 2f), true);
        GeometryUtils.makeCube(
                14f, 0.1f, 2f, name, texture, new Vector3f(6f, height, 18f), new Vector2f(2f, 14f), true);
        GeometryUtils.makeCube(
                2f, 0.1f, 12f, name, texture, new Vector3f(18f, height, 4f), new Vector2f(12f, 2f), true);
        GeometryUtils.makeCube(
                5f, 0.1f, 2f, name, texture, new Vector3f(-10f, height, 0f), new Vector2f(2f, 5f), true);
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
                2f, 0.1f, 3f, name, texture, new Vector3f(0f, height, 8f), new Vector2f(3f, 2f), true);
        GeometryUtils.makeCube(
                2f, 0.1f, 3f, name, texture, new Vector3f(0f, height, -8f), new Vector2f(3f, 2f), true);
        GeometryUtils.makeCube(
                3f, 0.1f, 2f, name, texture, new Vector3f(8f, height, 0f), new Vector2f(2f, 3f), true);
        GeometryUtils.makeCube(
                3f, 0.1f, 2f, name, texture, new Vector3f(-8f, height, 0f), new Vector2f(2f, 3f), true);
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
        Serializer.registerClass(CoordinateBombMessage.class);
        Serializer.registerClass(ExploitBombMessage.class);
        Serializer.registerClass(ThrowBombMessage.class);
        Serializer.registerClass(ElevatorMovesMessage.class);
        Serializer.registerClass(DamageMessage.class);
    }

    protected abstract AbstractManager<?> createManager();

    private void initContext() {
        JBombContext.ASSET_MANAGER = assetManager;
        JBombContext.ROOT_NODE = rootNode;
        JBombContext.PHYSICS_SPACE = bulletAppState.getPhysicsSpace();
        JBombContext.BASE_GAME = this;
        JBombContext.MANAGER = getManager();
    }

    protected AbstractManager<?> getManager() {
        return manager;
    }
    
    public abstract void loadLog4jConfig();

    protected boolean getElevatorServerControlled() {
        return false;
    }

    public Control createElevatorControl(float upY, float downY, float freezedSeconds, boolean up) {
        return null;
    }
}
