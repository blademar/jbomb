package jbomb.core.game;

import com.jme3.app.SimpleApplication;
import com.jme3.system.AppSettings;

public class BaseGame extends SimpleApplication {
    
    private AppSettings appSettings = new AppSettings(true);
    
    public BaseGame() {
        initAppSettings();
    }
    
    private void initAppSettings() {
        appSettings.setResolution(1024, 768);
        setSettings(appSettings);
        setShowSettings(false);
    }
    
    protected void initConfigurations() {
//        flyCam.setMoveSpeed(40);
    }

    @Override
    public void simpleInitApp() {
        initConfigurations();
    }
}
