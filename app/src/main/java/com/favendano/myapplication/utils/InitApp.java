package com.favendano.myapplication.utils;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.favendano.myapplication.MainActivity;
import com.pos.device.SDKManager;
import com.pos.device.sys.SystemManager;

/**
 * Provee la inicialización necesaria
 * para utilizar los servicios e interfaces provistas por el SDK propietario.
 *
 * @see #init()
 */
public class InitApp extends Application {

    private static Context appContext;
    private static boolean sdkInitialized;

    @Override
    public void onCreate() {
        super.onCreate();
        init();
    }

    public static boolean sdkInitialized() {
        return sdkInitialized;
    }

    /**
     * Primer método que debe ser llamado para obtener disponibilidad de los recursos propietarios.
     */
    private void init() {
        appContext = getApplicationContext();
        SDKManager.init(appContext, () -> {
            sdkInitialized = true;
            
            Log.d("SDKManager", "init SDK success(thread name:" + Thread.currentThread().getName() + ")");

            int setDefaultLauncher = SystemManager.setDefaultLauncher(this.getPackageName(), MainActivity.class.getName());
            Log.d("SystemManager", "setDefaultLauncher(" + setDefaultLauncher + ")");

            boolean cancelLockScreen = SystemManager.cancelLockScreen();
            Log.d("SystemManager", "cancelLockScreen(" + cancelLockScreen + ")");

            SystemManager.setHomeRecentAppKeyEnable(false, false);

            Log.d("SystemManager", "end of configuration");
        });
    }
}
