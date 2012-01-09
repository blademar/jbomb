package jbomb.common.appstates;

import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.network.Message;
import com.jme3.renderer.RenderManager;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import jbomb.common.game.IDRepository;
import jbomb.common.game.JBombContext;
import jbomb.common.messages.BasePhysicMessage;
import jbomb.common.messages.CharacterMovesMessage;
import org.apache.log4j.Logger;

public abstract class BaseManager<T> implements Manager<T> {

    private static final Logger LOGGER = Logger.getLogger(BaseManager.class);
    private float maxTime = 1f / JBombContext.MESSAGES_PER_SECOND;
    private float time;
    protected  Map<Long, Object> physicsObjects = new HashMap<Long, Object>();
    protected BlockingQueue<BasePhysicMessage> messageQueue = new ArrayBlockingQueue<BasePhysicMessage>(100000);
    private AbstractAppState appState = new AbstractAppState();
    private IDRepository repository = new IDRepository();

    @Override
    public void add(BasePhysicMessage message) {
        messageQueue.add(message);
    }

    @Override
    public void addPhysicObject(long l, Object o) {
        physicsObjects.put(l, o);
//        showPhysicsObject();
    }

    @Override
    public Object removePhysicObject(long l) {
        getRepository().releaseIn(l);
        Object o = physicsObjects.remove(l);
//        showPhysicsObject();
        return o;
    }

    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        appState.initialize(stateManager, app);
    }

    @Override
    public boolean isInitialized() {
        return appState.isInitialized();
    }

    @Override
    public void setEnabled(boolean active) {
        appState.setEnabled(active);
    }

    @Override
    public boolean isEnabled() {
        return appState.isEnabled();
    }

    @Override
    public void stateAttached(AppStateManager stateManager) {
        appState.stateAttached(stateManager);
    }

    @Override
    public void stateDetached(AppStateManager stateManager) {
        appState.stateDetached(stateManager);
    }

    @Override
    public void update(float tpf) {
        time += tpf;
        Iterator<BasePhysicMessage> it = messageQueue.iterator();
        BasePhysicMessage message = null;
        if (time >= maxTime) {
            time = 0;
            while (it.hasNext()) {
                message = it.next();
                doOnUpdate(tpf, message);
                it.remove();
            }
        } else {
            while (it.hasNext()) {
                message = it.next();
                if (message instanceof CharacterMovesMessage) {
                    message.interpolate(time / maxTime);
                }
            }
        }
    }

    @Override
    public void render(RenderManager rm) {
        appState.render(rm);
    }

    @Override
    public void postRender() {
        appState.postRender();
    }

    @Override
    public void cleanup() {
        appState.cleanup();
    }

    @Override
    public void messageReceived(T source, Message m) {
        messageQueue.add((BasePhysicMessage) m);
    }

    @Override
    public Collection<Object> values() {
        return physicsObjects.values();
    }

    @Override
    public Set<Long> keySet() {
        return physicsObjects.keySet();
    }

    @Override
    public Object getPhysicObject(long l) {
        return physicsObjects.get(l);
    }

    @Override
    public int getPhycicObjectSize() {
        return physicsObjects.size();
    }

    @Override
    public IDRepository getRepository() {
        return repository;
    }

    private void showPhysicsObject() {
        for (Long id : physicsObjects.keySet())
            LOGGER.debug("id: " + id + ": " + physicsObjects.get(id));
        LOGGER.debug("Count: " + repository.getCount());
    }
}
