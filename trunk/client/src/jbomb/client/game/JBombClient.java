package jbomb.client.game;

import com.jme3.font.BitmapText;
import com.jme3.input.KeyInput;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.network.Client;
import com.jme3.network.Network;
import com.jme3.renderer.Camera;
import com.jme3.system.AppSettings;
import com.jme3.ui.Picture;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import jbomb.client.appstates.ClientManager;
import jbomb.client.appstates.RunningClientAppState;
import jbomb.client.listeners.BombSecondsListener;
import jbomb.client.listeners.CharacterActionListener;
import jbomb.client.listeners.ServerConnectionListener;
import jbomb.client.listeners.ShootsActionListener;
import jbomb.client.listeners.messages.*;
import jbomb.common.appstates.AbstractManager;
import jbomb.common.game.BaseGame;
import jbomb.common.game.JBombContext;
import jbomb.common.game.Player;
import jbomb.common.messages.*;
import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

public class JBombClient extends BaseGame {

    private static final Logger LOGGER = Logger.getLogger(JBombClient.class);
    private String ip;
    private Client client;
    private Picture bombsPictures = new Picture("bombsPictures");
    private Picture BombsSecondsPictures = new Picture("bombsSecondsPictures");
    private Picture counterPicture = new Picture("counterPicture");
    private Map<Integer, BitmapText> health;
    private boolean left = false;
    private boolean right = false;
    private boolean front = false;
    private boolean back = false;
    private AppSettings appSettings = new AppSettings(true);
    private ShootsActionListener shootsActionListener = new ShootsActionListener();
    private CharacterActionListener characterActionListener = new CharacterActionListener();
    private BombSecondsListener bombSecondsListener = new BombSecondsListener();
    private ServerConnectionListener connectionListener = new ServerConnectionListener();
    private CreatePlayerListener createPlayerListener = new CreatePlayerListener();
    private NewPlayerListener newPlayerListener = new NewPlayerListener();
    private RemovePlayerListener removePlayerListener = new RemovePlayerListener();
    private StartGameListener startGameListener = new StartGameListener();
    private ThrowBombListener throwBombListener = new ThrowBombListener();
    private ExploitBombListener exploitBombListener = new ExploitBombListener();
    private DamageMessageListener damageMessageListener = new DamageMessageListener();
    private StartingNewGameListener startingNewGameListener = new StartingNewGameListener();
    private CounterListener counterListener = new CounterListener();
    
    private RunningClientAppState runningClientAppState;

    public JBombClient(String ip) {
        this.ip = ip;
        initAppSettings();
    }
    
    public void startGame() {
        if (runningClientAppState != null && stateManager.hasState(runningClientAppState))
            runningClientAppState.setEnabled(true);
        else {
            runningClientAppState = new RunningClientAppState();
            stateManager.attach(runningClientAppState);
        }
    }
    
    public void resetGame() {
        stateManager.getState(RunningClientAppState.class).setEnabled(false);
    }

    @Override
    public void simpleInitApp() {
        super.simpleInitApp();
        try {
            if (ip == null)
                ip = "localhost";
            client = Network.connectToServer(ip, 6789);
            addMessageListeners();
            client.addClientStateListener(connectionListener);
            client.start();
        } catch (IOException ex) {
            LOGGER.error("Error al conectar con el servidor", ex);
        }
        guiNode.detachAllChildren();
        ClientContext.APP = this;
        ClientContext.CLIENT = client;
    }

    private void initAppSettings() {
        appSettings.setResolution(640, 480);
        appSettings.setTitle("jBomb");
        setSettings(appSettings);
        setShowSettings(false);
    }
    
    public void initGuiCounter() {
        counterPicture.setImage(assetManager, "interfaces/pictures/ready3.png", true);
        counterPicture.setWidth(128f);
        counterPicture.setHeight(128f);
        counterPicture.setLocalTranslation(settings.getWidth() / 2 - 64f, settings.getHeight() / 2 - 64f, 0f);
        guiNode.attachChild(counterPicture);
    }
    
    public void changeCounter(byte num) {
        counterPicture.setImage(assetManager, "interfaces/pictures/ready" + num + ".png", true);
    }

    public void initInterfaces() {
        BitmapText ch = new BitmapText(guiFont, false);
        ch.setSize(guiFont.getCharSet().getRenderedSize() * 2);
        ch.setText("+");
        ch.setLocalTranslation(
                settings.getWidth() / 2 - guiFont.getCharSet().getRenderedSize() / 3 * 2,
                settings.getHeight() / 2 + ch.getLineHeight() / 2, 0);
        guiNode.attachChild(ch);

        getBombsPictures().setImage(assetManager, "interfaces/pictures/bomb1.png", true);
        getBombsPictures().setWidth(64f);
        getBombsPictures().setHeight(51f);
        getBombsPictures().setLocalTranslation(settings.getWidth() - 64f - 5f, 0f, 0f);
        guiNode.attachChild(getBombsPictures());

        getBombsSecondsPictures().setImage(assetManager, "interfaces/pictures/glass_numbers_1.png", true);
        getBombsSecondsPictures().setWidth(45f);
        getBombsSecondsPictures().setHeight(45f);
        getBombsSecondsPictures().setLocalTranslation(settings.getWidth() - 45 - 5f, 55f, 0f);
        guiNode.attachChild(getBombsSecondsPictures());
    }

    public void initHealthMarker() {
        enqueue(new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                health = new HashMap<Integer, BitmapText>();
                Object o = null;
                Player pl = null;
                BitmapText bmt = null;
                health.put(ClientContext.PLAYER.getIdUserData(), new BitmapText(guiFont, false));
                for (long l : JBombContext.MANAGER.keySet()) {
                    o = JBombContext.MANAGER.getPhysicObject(l);
                    if (o instanceof Player) {
                        pl = (Player) o;
                        health.put(pl.getIdUserData(), new BitmapText(guiFont, false));
                    }
                }
                float up = 0f;
                Picture picture = null;
                for (Integer i : health.keySet()) {
                    picture = new Picture("HealthPlayer" + i);
                    picture.setImage(assetManager, "interfaces/pictures/" + (i + 1) + ".png", true);
                    picture.setWidth(32f);
                    picture.setHeight(32f);
                    picture.setLocalTranslation(settings.getWidth() - 32f - 5f, 130f + up, 0f);
                    guiNode.attachChild(picture);
                    
                    bmt = health.get(i);
                    bmt.setText("100%");
                    bmt.setLocalTranslation(settings.getWidth() - (32f + 10f) * 2, 157f + up, 0f);
                    guiNode.attachChild(bmt);
                    up += 50f;
                }
                return null;
            }
        });
    }

    public void initMappings() {
        inputManager.addMapping("shoot", new MouseButtonTrigger(MouseInput.BUTTON_LEFT));
        inputManager.addMapping("Left", new KeyTrigger(KeyInput.KEY_A));
        inputManager.addMapping("Right", new KeyTrigger(KeyInput.KEY_D));
        inputManager.addMapping("Front", new KeyTrigger(KeyInput.KEY_W));
        inputManager.addMapping("Back", new KeyTrigger(KeyInput.KEY_S));
        inputManager.addMapping("Jump", new KeyTrigger(KeyInput.KEY_SPACE));
        inputManager.addMapping("one", new KeyTrigger(KeyInput.KEY_1), new KeyTrigger(KeyInput.KEY_NUMPAD1));
        inputManager.addMapping("two", new KeyTrigger(KeyInput.KEY_2), new KeyTrigger(KeyInput.KEY_NUMPAD2));
        inputManager.addMapping("three", new KeyTrigger(KeyInput.KEY_3), new KeyTrigger(KeyInput.KEY_NUMPAD3));
    }
    
    public void addListeners() {
        inputManager.addListener(shootsActionListener, "shoot");
        inputManager.addListener(characterActionListener, "Left");
        inputManager.addListener(characterActionListener, "Right");
        inputManager.addListener(characterActionListener, "Front");
        inputManager.addListener(characterActionListener, "Back");
        inputManager.addListener(characterActionListener, "Jump");
        inputManager.addListener(bombSecondsListener, "one");
        inputManager.addListener(bombSecondsListener, "two");
        inputManager.addListener(bombSecondsListener, "three");
    }
    
    public void removeListeners() {
        inputManager.removeListener(shootsActionListener);
        inputManager.removeListener(characterActionListener);
        inputManager.removeListener(bombSecondsListener);
    }

    public Camera getCam() {
        return cam;
    }

    public void setLeft(boolean left) {
        this.left = left;
    }

    public void setRight(boolean right) {
        this.right = right;
    }

    public void setFront(boolean front) {
        this.front = front;
    }

    public void setBack(boolean back) {
        this.back = back;
    }

    public boolean isLeft() {
        return left;
    }

    public boolean isRight() {
        return right;
    }

    public boolean isFront() {
        return front;
    }

    public boolean isBack() {
        return back;
    }

    public Picture getBombsPictures() {
        return bombsPictures;
    }

    public Picture getBombsSecondsPictures() {
        return BombsSecondsPictures;
    }

    @Override
    public void addMessageListeners() {
        client.addMessageListener(createPlayerListener, CreatePlayerMessage.class);
        client.addMessageListener(newPlayerListener, NewPlayerMessage.class);
        client.addMessageListener(removePlayerListener, RemovePlayerMessage.class);
        client.addMessageListener(startGameListener, StartGameMessage.class);
        client.addMessageListener(throwBombListener, ThrowBombMessage.class);
        client.addMessageListener(exploitBombListener, ExploitBombMessage.class);
        client.addMessageListener(damageMessageListener, DamageMessage.class);
        client.addMessageListener(startingNewGameListener, StartingNewGameMessage.class);
        client.addMessageListener(counterListener, CounterMessage.class);
        AbstractManager<Client> m = (AbstractManager<Client>) getManager();
        client.addMessageListener(m, CharacterMovesMessage.class);
        client.addMessageListener(m, CoordinateBombMessage.class);
        client.addMessageListener(m, ElevatorMovesMessage.class);
    }

    @Override
    public void destroy() {
        if (client != null && client.isConnected()) {
            client.close();
        }
        super.destroy();
    }

    @Override
    protected AbstractManager<?> createManager() {
        return new ClientManager();
    }

    @Override
    public void loadLog4jConfig() {
        URL urlConfig = BaseGame.class.getResource("/jbomb/client/config/log4j.xml");
        DOMConfigurator.configure(urlConfig);
    }

    public BitmapText getHealtWithId(int id) {
        return health.get(id);
    }
    
    public void cleanScreen() {
        guiNode.detachAllChildren();
    }
}
