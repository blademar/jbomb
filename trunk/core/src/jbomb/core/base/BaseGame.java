package jbomb.core.base;

import com.jme3.app.SimpleApplication;
import com.jme3.system.AppSettings;

public class BaseGame extends SimpleApplication {
    
    private AppSettings appSettings = new AppSettings(true);
    
    public BaseGame() {
        initAppSettings();
    }
    
    public static void main(String[] args) {
        new BaseGame().start();
    }

    @Override
    public void simpleInitApp() {}
    
    private void initAppSettings() {
        appSettings.setResolution(640, 480);
        setSettings(appSettings);
        setShowSettings(false);
    }
    
}
