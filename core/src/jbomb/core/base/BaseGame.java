package jbomb.core.base;

import com.jme3.app.SimpleApplication;
import com.jme3.system.AppSettings;

public abstract class BaseGame extends SimpleApplication {
    
    private AppSettings appSettings = new AppSettings(true);
    
    public BaseGame() {
        initAppSettings();
    }
    
    private void initAppSettings() {
        appSettings.setResolution(640, 480);
        setSettings(appSettings);
        setShowSettings(false);
    }
}
