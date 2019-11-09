package smartled.com.smartlampremotecontrol;

import android.app.Application;
import android.content.Context;

public class SmartLedApp extends Application {
    public static Context context;

    @Override public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }

    public static Context getContext() {
        return context;
    }
}
