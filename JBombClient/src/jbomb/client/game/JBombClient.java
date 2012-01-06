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
import jbomb.client.appstates.ClientManager;
import jbomb.client.appstates.RunningClientAppState;
import jbomb.client.controls.ClientElevatorControl;
import jbomb.client.listeners.BombSecondsListener;
import jbomb.client.listeners.CharacterActionListener;
import jbomb.common.appstates.BaseManager;
import jbomb.common.appstates.RunningAppState;
import jbomb.client.listeners.ServerConnectionListener;
import jbomb.client.listeners.ShootsActionListener;
import jbomb.client.listeners.messages.CreatePlayerListener;
import jbomb.client.listeners.messages.ElevatorMovesListener;
import jbomb.client.listeners.messages.ExploitBombListener;
import jbomb.client.listeners.messages.NewPlayerListener;
import jbomb.client.listeners.messages.RemovePlayerListener;
import jbomb.client.listeners.messages.StartGameListener;
import jbomb.client.listeners.messages.ThrowBombListener;
import jbomb.common.appstates.Manager;
import jbomb.common.controls.AbstractElevatorControl;
import jbomb.common.game.BaseGame;
import jbomb.common.messages.CharacterMovesMessage;
import jbomb.common.messages.CoordinateBombMessage;
import jbomb.common.messages.CreatePlayerMessage;
import jbomb.common.messages.ElevatorMovesMessage;
import jbomb.common.messages.ExploitBombMessage;
import jbomb.common.messages.NewPlayerMessage;
import jbomb.common.messages.RemovePlayerMessage;
import jbomb.common.messages.StartGameMessage;
import jbomb.common.messages.ThrowBombMessage;

public class JBombClient extends BaseGame {
    
    private String ip;
    private Client client;
    private Picture bombsPictures = new Picture("bombsPictures");
    private Picture BombsSecondsPictures = new Picture("bombsSecondsPictures");
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
    private ElevatorMovesListener elevatorMovesListener = new ElevatorMovesListener();
    
    public JBombClient(String ip) {
        this.ip = ip;
        initAppSettings();
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
            System.out.println("Error al conectar con el servidor: " + ex);
        }
        ClientContext.APP = this;
        ClientContext.CLIENT = client;
        initInterfaces();
        initMappings();
    }
    
    private void initAppSettings() {
        appSettings.setResolution(640, 480);
        setSettings(appSettings);
        setShowSettings(false);
    }
    
    private void initInterfaces() {
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
        getBombsPictures().setLocalTranslation(settings.getWidth() - 64f, 0f, 0f);
        guiNode.attachChild(getBombsPictures());
        
        getBombsSecondsPictures().setImage(assetManager, "interfaces/pictures/glass_numbers_1.png", true);
        getBombsSecondsPictures().setWidth(45f);
        getBombsSecondsPictures().setHeight(45f);
        getBombsSecondsPictures().setLocalTranslation(settings.getWidth() - 45f, 55f, 0f);
        guiNode.attachChild(getBombsSecondsPictures());
    }
    
    private void initMappings() {
        inputManager.addMapping("shoot", new MouseButtonTrigger(MouseInput.BUTTON_LEFT));
        inputManager.addMapping("Left", new KeyTrigger(KeyInput.KEY_A));
        inputManager.addMapping("Right", new KeyTrigger(KeyInput.KEY_D));
        inputManager.addMapping("Front", new KeyTrigger(KeyInput.KEY_W));
        inputManager.addMapping("Back", new KeyTrigger(KeyInput.KEY_S));
        inputManager.addMapping("Jump", new KeyTrigger(KeyInput.KEY_SPACE));
        inputManager.addMapping("one", new KeyTrigger(KeyInput.KEY_1), new KeyTrigger(KeyInput.KEY_NUMPAD1));
        inputManager.addMapping("two", new KeyTrigger(KeyInput.KEY_2), new KeyTrigger(KeyInput.KEY_NUMPAD2));
        inputManager.addMapping("three", new KeyTrigger(KeyInput.KEY_3), new KeyTrigger(KeyInput.KEY_NUMPAD3));
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
    public RunningAppState createRunningAppState() {
        return new RunningClientAppState();
    }

    @Override
    public void addMessageListeners() {
        client.addMessageListener(createPlayerListener, CreatePlayerMessage.class);
        client.addMessageListener(newPlayerListener, NewPlayerMessage.class);
        client.addMessageListener(removePlayerListener, RemovePlayerMessage.class);
        client.addMessageListener(startGameListener, StartGameMessage.class);
        client.addMessageListener(throwBombListener, ThrowBombMessage.class);
        client.addMessageListener(exploitBombListener, ExploitBombMessage.class);
        client.addMessageListener(elevatorMovesListener, ElevatorMovesMessage.class);
        Manager<Client> m = (Manager<Client>) getManager();
        client.addMessageListener(m, CharacterMovesMessage.class);
        client.addMessageListener(m, CoordinateBombMessage.class);
    }

    @Override
    public void destroy() {
        if (client != null && client.isConnected())
            client.close();
        super.destroy();
    }

    @Override
    protected BaseManager<?> createManager() {
        return new ClientManager();
    }

    @Override
    public AbstractElevatorControl createElevatorControl(float maxY, float minY, float seconds, boolean up) {
        return new ClientElevatorControl(maxY, minY, up);
    }
}
