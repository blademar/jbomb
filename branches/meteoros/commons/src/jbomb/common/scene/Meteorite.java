package jbomb.common.scene;

import com.jme3.material.Material;
import com.jme3.scene.Geometry;
import com.jme3.scene.control.Control;
import com.jme3.scene.shape.Sphere;
import com.jme3.texture.Texture;
import jbomb.common.game.JBombContext;
import jbomb.common.utils.MatDefs;
import jbomb.common.utils.MeteoritePoints;

public class Meteorite {
    
    private long id;
    private Geometry geometry;
    
    public Meteorite(float radius, MeteoritePoints points, float speed) {
        Sphere sphere = new Sphere(32, 32, radius);
        geometry = new Geometry("sphere", sphere);
        Material material = new Material(JBombContext.ASSET_MANAGER, MatDefs.UNSHADED); 
        Texture texture = JBombContext.ASSET_MANAGER.loadTexture("textures/rocks/rock.png");
        material.setTexture("ColorMap", texture);
        geometry.setMaterial(material);
        geometry.setLocalTranslation(points.getPoints(0)[0]);
        JBombContext.ROOT_NODE.attachChild(geometry);
        Control control = JBombContext.BASE_GAME.createMeteoriteControl(points, speed);
        if(control != null) {
            geometry.addControl(control);
        }
        
        id = JBombContext.MANAGER.getRepository().nextFree();
    }
    
    public long getId() {
        return id;
    }

    public Geometry getGeometry() {
        return geometry;
    }
}
