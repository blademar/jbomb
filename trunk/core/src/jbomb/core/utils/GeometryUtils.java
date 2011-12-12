package jbomb.core.utils;

import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.material.Material;
import com.jme3.material.RenderState;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Box;
import com.jme3.scene.shape.Quad;
import com.jme3.texture.Texture;
import jbomb.core.controls.ElevatorControl;
import jbomb.core.game.JBombContext;

public class GeometryUtils {

    public static Geometry makeCube(float x, float y, float z, String name, String texturePath, Vector3f localTranslation, Vector2f scaleTexture, boolean transparent) {
        Box box = new Box(Vector3f.ZERO, x, y, z);
        Geometry geometry = new Geometry(name, box);
        Material material = new Material(JBombContext.ASSET_MANAGER, MatDefs.UNSHADED);
        Texture texture = JBombContext.ASSET_MANAGER.loadTexture(texturePath);
        material.setTexture("ColorMap", texture);
        geometry.setMaterial(material);
        geometry.setLocalTranslation(localTranslation);
        if (scaleTexture != null) {
            box.scaleTextureCoordinates(scaleTexture);
            texture.setWrap(Texture.WrapMode.Repeat);
        }
        if (transparent) {
            geometry.getMaterial().getAdditionalRenderState().setBlendMode(RenderState.BlendMode.Alpha);
            geometry.setQueueBucket(RenderQueue.Bucket.Transparent);
        }
        RigidBodyControl physics = new RigidBodyControl(0f);
        geometry.addControl(physics); 
        JBombContext.JBOMB.getBulletAppState().getPhysicsSpace().add(physics);
        JBombContext.ROOT_NODE.attachChild(geometry);
        return geometry;
    }
    
    public static Geometry makeCube(float x, float y, float z, String name, String texturePath, Vector3f localTranslation, Vector2f scaleTexture) {
        return makeCube(x, y, z, name, texturePath, localTranslation, scaleTexture, false);
    }
    
    public static Geometry makeCube(float x, float y, float z, String name, String texturePath, Vector3f localTranslation, boolean transparent) {
        return makeCube(x, y, z, name, texturePath, localTranslation, null, transparent);
    }

    public static Geometry makeCube(float x, float y, float z, String name, String texturePath, Vector3f localTranslation) {
        return makeCube(x, y, z, name, texturePath, localTranslation, null, false);
    }    
    
    public static Geometry makeElevator(String name, String texture, Vector3f dimension, Vector3f localTraslation, Vector2f scaletexture, ElevatorControl control, boolean transparency) {
        Geometry elevator = GeometryUtils.makeCube(
                dimension.x, dimension.y, dimension.z, 
                name, texture, 
                localTraslation, scaletexture, transparency);
        control.cloneForSpatial(elevator);
        return elevator;
    }
    
    public static Geometry makeElevator(String name, String texture, Vector3f dimension, Vector3f localTraslation, ElevatorControl control, boolean transparency) {
        return makeElevator(name, texture, dimension, localTraslation, new Vector2f(dimension.z, dimension.x), control, transparency);
    }
    
    public static Geometry makeElevator(String name, String texture, Vector3f dimension, Vector3f localTraslation, ElevatorControl control) {
        return makeElevator(name, texture, dimension, localTraslation, control, false);
    }
    
    public static Geometry makeElevator(String texture, Vector3f dimension, Vector3f localTraslation, ElevatorControl control) {
        return makeElevator("elevator", texture, dimension, localTraslation, control);
    }
    
    public static Geometry makeElevator(Vector3f dimension, Vector3f localTraslation, ElevatorControl control) {
        return makeElevator("textures/boxes/w_darkgray.png", dimension, localTraslation, control);
    }
    
    public static Geometry makePlane(float x, float y, String name, String texturePath, Vector3f localTranslation, Quaternion quaternion, Vector2f scaleTexture, boolean transparent) {
        Quad quad = new Quad(x, y);
        Geometry geometry = new Geometry(name, quad);
        Material material = new Material(JBombContext.ASSET_MANAGER, MatDefs.UNSHADED);
        Texture texture = JBombContext.ASSET_MANAGER.loadTexture(texturePath);
        material.setTexture("ColorMap", texture);
        geometry.setMaterial(material);
        geometry.setLocalTranslation(localTranslation);
        if (quaternion != null)
            geometry.setLocalRotation(quaternion);
        if (scaleTexture != null) {
            quad.scaleTextureCoordinates(scaleTexture);
            texture.setWrap(Texture.WrapMode.Repeat);
        }
        if (transparent) {
            geometry.getMaterial().getAdditionalRenderState().setBlendMode(RenderState.BlendMode.Alpha);
            geometry.setQueueBucket(RenderQueue.Bucket.Transparent);
        }
        RigidBodyControl physics = new RigidBodyControl(0f);
        geometry.addControl(physics); 
        JBombContext.JBOMB.getBulletAppState().getPhysicsSpace().add(physics);
        JBombContext.ROOT_NODE.attachChild(geometry);
        return geometry;
    }
    
    public static Geometry makePlane(float x, float y, String name, String texturePath, Vector3f localTranslation, Quaternion quaternion, Vector2f scaleTexture) {
        return makePlane(x, y, name, texturePath, localTranslation, quaternion, scaleTexture, false);
    }
    
    public static Geometry makePlane(float x, float y, String name, String texturePath, Vector3f localTranslation, Quaternion quaternion, boolean transparent) {
        return makePlane(x, y, name, texturePath, localTranslation, quaternion, null, transparent);
    }

    public static Geometry makePlane(float x, float y, String name, String texturePath, Quaternion quaternion, Vector3f localTranslation) {
        return makePlane(x, y, name, texturePath, localTranslation, quaternion, null, false);
    }  
    
    public static Geometry makePlane(float x, float y, String name, String texturePath, Vector3f localTranslation) {
        return makePlane(x, y, name, texturePath, localTranslation, null, null, false);
    } 
    
    public static Geometry makePlane(float x, float y, String name, String texturePath, Vector3f localTranslation, Vector2f scaleTexture) {
        return makePlane(x, y, name, texturePath, localTranslation, null, scaleTexture, false);
    }
    
    public static Geometry makePlane(float x, float y, String name, String texturePath, Vector3f localTranslation, boolean transparent) {
        return makePlane(x, y, name, texturePath, localTranslation, null, null, transparent);
    }
    
    public static Geometry makePlane(float x, float y, String name, String texturePath, Vector3f localTranslation, Vector2f scaleTexture, boolean transparent) {
        return makePlane(x, y, name, texturePath, localTranslation, null, scaleTexture, transparent);
    }
}
