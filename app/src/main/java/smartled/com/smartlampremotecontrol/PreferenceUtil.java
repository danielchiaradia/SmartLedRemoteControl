package smartled.com.smartlampremotecontrol;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferenceUtil {
    public static final String PREFERENCE_NAME = "ledBrokerPrefernces";
    public static final String LAMP_IP_PREFERENCE_KEY = "lampIpPreferenceKey";
    public static final String SELECTED_COLOR_PREFERENCE_KEY = "selectedColorKey";

    public static String getLampIP() {
        return getSharedPreferences().getString(LAMP_IP_PREFERENCE_KEY, "0.0.0.0");
    }

    public static void setLampIP(String lampIp) {
        storePreference(LAMP_IP_PREFERENCE_KEY, lampIp);
    }

    private static SharedPreferences getSharedPreferences() {
        return SmartLedApp.getContext().getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
    }

    private static void storePreference(String key, String value) {
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static void setSelectedColor(int selectedColor) {
        storePreference(SELECTED_COLOR_PREFERENCE_KEY, String.valueOf(selectedColor));
    }

    public static Integer getSelectedColor() {
        String color = getSharedPreferences().getString(SELECTED_COLOR_PREFERENCE_KEY, "");

        if (color.length() > 0) {
            return Integer.valueOf(color);
        }

        return null;
    }

}
