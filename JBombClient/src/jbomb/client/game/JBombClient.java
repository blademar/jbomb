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
import jbomb.client.appstates.RunningClientAppState;
import jbomb.client.listeners.BombSecondsListener;
import jbomb.client.listeners.CharacterActionListener;
import jbomb.client.listeners.ShootsActionListener;
import jbomb.common.appstates.RunningAppState;
import jbomb.client.listeners.ServerConnectionListener;
import jbomb.client.listeners.messages.CreatePlayerListener;
import jbomb.client.listeners.messages.NewPlayerListener;
import jbomb.client.listeners.messages.RemovePlayerListener;
import jbomb.common.game.BaseGame;
import jbomb.common.messages.CreatePlayerMessage;
import jbomb.common.messages.NewPlayerMessage;
import jbomb.common.messages.RemovePlayerMessage;

public class JBombClient extends BaseGame {
    
    private Client client;
    private Picture bombsPictures = new Picture("bombsPictures");
    private Picture BombsSecondsPictures = new Picture("bombsSecondsPictures");
    private boolean left = false;
    private boolean right = false;
    private boolean front = false;
    private boolean back = false;
    private AppSettings appSettings = new AppSettings(true);
    private RunningAppState runningAppState = new RunningAppState();
    private ShootsActionListener shootsActionListener = new ShootsActionListener();
    private CharacterActionListener characterActionListener = new CharacterActionListener();
    private BombSecondsListener bombSecondsListener = new BombSecondsListener();
    private ServerConnectionListener connectionListener = new ServerConnectionListener();
    private CreatePlayerListener createPlayerListener = new CreatePlayerListener();
    private NewPlayerListener newPlayerListener = new NewPlayerListener();
    private RemovePlayerListener removePlayerListener = new RemovePlayerListener();
    private ClientPlayer player;
    
    public JBombClient() {
        initAppSettings();
    }

    @Override
    public void simpleInitApp() {
        super.simpleInitApp();
        try {
            client = Network.connectToServer("localhost", 6789);
            addMessageListeners();
            client.addClientStateListener(connectionListener);
            client.start();
        } catch (IOException ex) {
            System.out.println("Error al conectar con el servidor: " + ex);
        }
        ClientContext.APP = this;
//        initInterfaces();
//        initMappings();
    }

    @Override
    protected void initStateManager() {
        super.initStateManager();
        stateManager.attach(runningAppState);
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
        
        getBombsPictures().setImage(assetManager, "interfaces/pictures/glass_numbers_1.png", true);
        getBombsPictures().setWidth(45f);
        getBombsPictures().setHeight(45f);
        getBombsPictures().setLocalTranslation(settings.getWidth() - 45f, 55f, 0f);
        guiNode.attachChild(getBombsSecondsPictures());
    }
    
    private void initMappings() {
        inputManager.addMapping("shoot", new MouseButtonTrigger(MouseInput.BUTTON_LEFT));
        inputManager.addListener(shootsActionListener, "shoot");
        inputManager.addMapping("Left", new KeyTrigger(KeyInput.KEY_A));
        inputManager.addMapping("Right", new KeyTrigger(KeyInput.KEY_D));
        inputManager.addMapping("Front", new KeyTrigger(KeyInput.KEY_W));
        inputManager.addMapping("Back", new KeyTrigger(KeyInput.KEY_S));
        inputManager.addMapping("Jump", new KeyTrigger(KeyInput.KEY_SPACE));
        inputManager.addMapping("one", new KeyTrigger(KeyInput.KEY_1), new KeyTrigger(KeyInput.KEY_NUMPAD1));
        inputManager.addMapping("two", new KeyTrigger(KeyInput.KEY_2), new KeyTrigger(KeyInput.KEY_NUMPAD2));
        inputManager.addMapping("three", new KeyTrigger(KeyInput.KEY_3), new KeyTrigger(KeyInput.KEY_NUMPAD3));
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
    }

    public void setPlayer(ClientPlayer player) {
        this.player = player;
    }

    @Override
    public void destroy() {
        if (client != null && client.isConnected())
            client.close();
        super.destroy();
    }
}
