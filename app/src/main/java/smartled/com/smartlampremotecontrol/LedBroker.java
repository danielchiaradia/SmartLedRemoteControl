package smartled.com.smartlampremotecontrol;

import android.content.Context;
import android.content.SharedPreferences;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import smartled.com.smartlampremotecontrol.commands.BrightnessCommand;
import smartled.com.smartlampremotecontrol.commands.ColorCommand;
import smartled.com.smartlampremotecontrol.commands.Command;

/**
 * Created by dchiarad on 03.12.2016.
 */

public class LedBroker {
    public static final String PREFERENCE_NAME = "ledBrokerPrefernces";
    public static final String LAMP_IP_PREFERENCE_KEY = "lampIpPreferenceKey";

    private static LedBroker instance;
    private static RequestQueue queue;
    private static String url = "http://%s/action/?%s=%s";
    private Context context;

    private LedBroker(Context context) {
        this.context = context;
    }

    public static synchronized LedBroker getInstance(Context context) {
        if (instance == null) {
            instance = new LedBroker(context);
            queue = Volley.newRequestQueue(context);
        }

        return instance;
    }

    public void sendColor(int color1, int color2, int color3) {

        executeCommand(new ColorCommand(color1, color2, color3));
    }

    public void sendBrightness(int brightness) {
        executeCommand(new BrightnessCommand(brightness));
    }

    public void executeCommand(Command command) {
        SharedPreferences pref = context.getSharedPreferences(LedBroker.PREFERENCE_NAME, Context.MODE_PRIVATE);
        String stringCommand = String.format(url, pref.getString(LedBroker.LAMP_IP_PREFERENCE_KEY, null), command.getCommandName(), command.getParameters());

        System.out.println("Sending: " + stringCommand);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, stringCommand,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                System.out.println(error);
            }

        });

        queue.add(stringRequest);
    }
}