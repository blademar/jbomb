
package jbomb.server.controls;

import com.jme3.cinematic.MotionPath;
import com.jme3.cinematic.MotionPathListener;
import com.jme3.cinematic.events.MotionTrack;
import com.jme3.math.Spline.SplineType;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.control.Control;
import jbomb.common.controls.JBombAbstractControl;
import jbomb.common.effects.api.Explosion;
import jbomb.common.effects.impl.MeteoriteExplosion;
import jbomb.common.game.JBombContext;
import jbomb.common.utils.MeteoritePoints;

public class MeteoriteControl extends JBombAbstractControl {    
    private MotionPath path;
    private MotionTrack track;
    private float speed, nextMeteorite, time;
    private State state = State.WAITING;
    private boolean isTrackSet = false;
    private Explosion explosion;
    private MeteoritePoints points;
    
    private enum State {
        REACHED_POINT, MOVING, WAITING;
    }
    
    //Puntos nulos.
    public MeteoriteControl() {
        this(new MeteoritePoints());
    }
    
    public MeteoriteControl(MeteoritePoints points) {
        this(points, 3f);
    }
    
    public MeteoriteControl(MeteoritePoints points, float speed) {
        this(points, speed, 10f, new MeteoriteExplosion());
    }
    
    public MeteoriteControl(final MeteoritePoints points, float speed, float nextMeteorite, final Explosion explosion) {
        setEnabled(true);
        this.speed = speed;
        this.nextMeteorite = nextMeteorite;
        this.explosion = explosion;
        this.points = points;
        
        int position = (int) (Math.random() * points.getTotalPoints());
        
        path = new MotionPath();
        path.addWayPoint(points.getPoints(position)[0]);
        path.addWayPoint(points.getPoints(position)[1]);
        path.setPathSplineType(SplineType.Linear);
        path.addListener(new MotionPathListener() {

            @Override
            public void onWayPointReach(MotionTrack mt, int i) {
                if(path.getNbWayPoints() == i + 1) {
                    int position = (int) (Math.random() * points.getTotalPoints());

                    explosion.config();
                    explosion.setLocation(path.getWayPoint(i));
                    explosion.start();
                    
                    path.clearWayPoints();
                    path.addWayPoint(points.getPoints(position)[0]);
                    path.addWayPoint(points.getPoints(position)[1]);
                    
                    path.disableDebugShape();
                    path.enableDebugShape(JBombContext.ASSET_MANAGER, JBombContext.ROOT_NODE);
           
                    state = State.REACHED_POINT;
                    spatial.setLocalTranslation(path.getWayPoint(0));
                }
            }
        });
        
        path.enableDebugShape(JBombContext.ASSET_MANAGER, JBombContext.ROOT_NODE);
    }

    @Override
    protected Control newInstanceOfMe() {
        return new MeteoriteControl();
    }

    @Override
    protected void controlUpdate(float tpf) {        
        if(state == State.MOVING) {
            if(!isTrackSet) {
                setTrack();
                isTrackSet = true;
            }
            
            track.play();
            state = null;
        } else if(state == State.REACHED_POINT) {
            time += tpf;
            if(time >= nextMeteorite) {
                state = State.MOVING;
                time = 0f;
            }
        } else if(state == State.WAITING) {
            time += tpf;
            if(time >= 10f) {
                state = State.MOVING;
                time = 0f;
            }
        }
    }

    private void setTrack() {
        track = new MotionTrack(spatial, path);
        track.setDirectionType(MotionTrack.Direction.None);
        track.setSpeed(speed);
    }

    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {}
}
