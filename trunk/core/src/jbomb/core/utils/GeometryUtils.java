package jbomb.core.utils;

import com.jme3.asset.AssetManager;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.material.Material;
import com.jme3.material.RenderState;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Box;
import com.jme3.texture.Texture;

public class GeometryUtils {
    
    private AssetManager assetManager;
    private Node rootNode;
    private BulletAppState bulletAppState;
    
    public GeometryUtils(AssetManager assetManager, Node roNode, BulletAppState bulletAppState) {
        this.assetManager = assetManager;
        this.rootNode = roNode;
        this.bulletAppState = bulletAppState;
    }

    public Geometry makeCube(float x, float y, float z, String name, String texturePath, Vector3f localTranslation, Vector2f scaleTexture, boolean transparent) {
        Box box = new Box(Vector3f.ZERO, x, y, z);
        Geometry geometry = new Geometry(name, box);
        Material material = new Material(assetManager, MatDefs.UNSHADED);
        Texture texture = assetManager.loadTexture(texturePath);
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
        bulletAppState.getPhysicsSpace().add(physics);
        rootNode.attachChild(geometry);
        return geometry;
    }
    
    public Geometry makeCube(float x, float y, float z, String name, String texturePath, Vector3f localTranslation, Vector2f scaleTexture) {
        return makeCube(x, y, z, name, texturePath, localTranslation, scaleTexture, false);
    }
    
    public Geometry makeCube(float x, float y, float z, String name, String texturePath, Vector3f localTranslation, boolean transparent) {
        return makeCube(x, y, z, name, texturePath, localTranslation, null, transparent);
    }

    public Geometry makeCube(float x, float y, float z, String name, String texturePath, Vector3f localTranslation) {
        return makeCube(x, y, z, name, texturePath, localTranslation, null, false);
    }
    
    public Geometry makePlaneXY(float x, float y, String name, String texturePath, Vector3f localTranslation, Vector2f scaleTexture, boolean transparent) {
        return makeCube(x, y, 0f, name, texturePath, localTranslation, scaleTexture, transparent);
    }
    
    public Geometry makePlaneXY(float x, float y, String name, String texturePath, Vector3f localTranslation, Vector2f scaleTexture) {
        return makeCube(x, y, 0f, name, texturePath, localTranslation, scaleTexture, false);
    }
    
    public Geometry makePlaneXY(float x, float y, String name, String texturePath, Vector3f localTranslation, boolean transparent) {
        return makeCube(x, y, 0f, name, texturePath, localTranslation, null, transparent);
    }
    
    public Geometry makePlaneXY(float x, float y, String name, String texturePath, Vector3f localTranslation) {
        return makeCube(x, y, 0f, name, texturePath, localTranslation, null, false);
    }
    
    public Geometry makePlaneXZ(float x, float z, String name, String texturePath, Vector3f localTranslation, Vector2f scaleTexture, boolean transparent) {
        return makeCube(x, 0f, z, name, texturePath, localTranslation, scaleTexture, transparent);
    }
    
    public Geometry makePlaneXZ(float x, float z, String name, String texturePath, Vector3f localTranslation, Vector2f scaleTexture) {
        return makeCube(x, 0f, z, name, texturePath, localTranslation, scaleTexture, false);
    }
    
    public Geometry makePlaneXZ(float x, float z, String name, String texturePath, Vector3f localTranslation, boolean transparent) {
        return makeCube(x, 0f, z, name, texturePath, localTranslation, null, transparent);
    }
    
    public Geometry makePlaneXZ(float x, float z, String name, String texturePath, Vector3f localTranslation) {
        return makeCube(x, 0f, z, name, texturePath, localTranslation, null, false);
    }
    
    public Geometry makePlaneYZ(float y, float z, String name, String texturePath, Vector3f localTranslation, Vector2f scaleTexture, boolean transparent) {
        return makeCube(0f, y, z, name, texturePath, localTranslation, scaleTexture, transparent);
    }
    
    public Geometry makePlaneYZ(float y, float z, String name, String texturePath, Vector3f localTranslation, Vector2f scaleTexture) {
        return makeCube(0f, y, z, name, texturePath, localTranslation, scaleTexture, false);
    }
    
    public Geometry makePlaneYZ(float y, float z, String name, String texturePath, Vector3f localTranslation, boolean transparent) {
        return makeCube(0f, y, z, name, texturePath, localTranslation, null, transparent);
    }
    
    public Geometry makePlaneYZ(float y, float z, String name, String texturePath, Vector3f localTranslation) {
        return makeCube(0f, y, z, name, texturePath, localTranslation, null, false);
    }
}
