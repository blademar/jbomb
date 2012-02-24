package jbomb.common.utils;

import com.jme3.math.Vector3f;
import java.util.ArrayList;
import java.util.List;

public class MeteoritePoints {
    
    private List<Vector3f[]> points;
    
    public MeteoritePoints() {
        points = new ArrayList<Vector3f[]>();
    }
    
    public void addPoints(Vector3f point1, Vector3f point2) {
        points.add(new Vector3f[] {point1, point2});
    }

    public int getTotalPoints() {
        return points.size();
    }
    
    public void removePoints(int index) {
        points.remove(index);
    }
    
    public Vector3f[] getPoints(int index) {
        return points.get(index);
    }
}
