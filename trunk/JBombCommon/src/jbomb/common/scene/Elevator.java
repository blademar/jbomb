package jbomb.common.scene;

import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import jbomb.common.controls.ElevatorControl;
import jbomb.common.utils.GeometryUtils;

public class Elevator {
    
    private Geometry geometry;
    private ElevatorControl control;
    
    public Elevator(String name, String texture, Vector3f localTraslation, float upY, float downY, float freezedSeconds, boolean up, boolean transparency) {
       geometry = GeometryUtils.makeCube(1f, .1f, 1f, name, texture, localTraslation, new Vector2f(1f, 1f), transparency);
//       geometry.getControl(RigidBodyControl.class).setMass(1f);
//       geometry.getControl(RigidBodyControl.class).setKinematic(true);
//       control = new ElevatorControl(upY, downY, freezedSeconds, up);
//       geometry.addControl(control);
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
    
    public void setControl(ElevatorControl control) {
        geometry.removeControl(control);
        geometry.addControl(control);
        this.control = control;
    }
}