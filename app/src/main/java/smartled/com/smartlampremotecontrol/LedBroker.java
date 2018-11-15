package smartled.com.smartlampremotecontrol;

import android.content.Context;

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

    private static LedBroker instance;
    private static RequestQueue queue;
    private static String url ="http://192.168.0.241/action/?%s=%s";

    private LedBroker() {
    }

    public static synchronized LedBroker getInstance(Context context) {
        if (instance == null) {
            instance = new LedBroker();
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
        System.out.println("Sending: " + String.format(url, command.getCommandName(), command.getParameters()));
        StringRequest stringRequest = new StringRequest(Request.Method.GET, String.format(url, command.getCommandName(), command.getParameters()),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //TODO
            }

        });

        queue.add(stringRequest);
    }
}