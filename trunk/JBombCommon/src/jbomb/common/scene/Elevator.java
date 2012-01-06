package jbomb.common.scene;

import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import jbomb.common.controls.AbstractElevatorControl;
import jbomb.common.game.JBombContext;
import jbomb.common.utils.GeometryUtils;

public class Elevator {
    
    private Geometry geometry;
    private AbstractElevatorControl control;
    
    public Elevator(String name, String texture, Vector3f localTraslation, float upY, float downY, float freezedSeconds, boolean up, boolean transparency) {
       geometry = GeometryUtils.makeCube(1f, .1f, 1f, name, texture, localTraslation, new Vector2f(1f, 1f), transparency);
       long id = JBombContext.MANAGER.getRepository().nextFree();
       System.out.println("Creating elevator: " + id);
       JBombContext.MANAGER.addPhysicObject(id, geometry);
       geometry.setUserData("id", id);
       geometry.setUserData("move", true);
       geometry.getControl(RigidBodyControl.class).setMass(1f);
       geometry.getControl(RigidBodyControl.class).setKinematic(true);
       control = JBombContext.BASE_GAME.createElevatorControl(upY, downY, freezedSeconds, up);
       geometry.addControl(control);
    }
    
    public Elevator(String name, String texture, Vector3f localTraslation, float upY, float downY, float freezedSeconds, boolean up) {
        this(name, texture, localTraslation, upY, downY, freezedSeconds, up, false);
    }
    
    public Elevator(Vector3f localTraslation, float upY, float downY, float freezedSeconds, boolean up) {
        this("elevator", "textures/boxes/w_darkgray.png", localTraslation, upY, downY, freezedSeconds, up, false);
    }
    
    public Elevator(Vector3f localTraslation, float upY, float downY, boolean up) {
        this("elevator", "textures/boxes/w_darkgray.png", localTraslation, upY, downY, 0.5f, up, false);
    }
}