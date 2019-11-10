package smartled.com.smartlampremotecontrol;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;
import java.util.stream.Collectors;

import smartled.com.smartlampremotecontrol.commands.BrightnessCommand;
import smartled.com.smartlampremotecontrol.commands.ColorCommand;
import smartled.com.smartlampremotecontrol.commands.Command;

/**
 * Created by dchiarad on 03.12.2016.
 */

public class LedBroker {


    private static LedBroker instance;
    private static RequestQueue queue;
    private static String url = "http://%s/action/?%s";
    private static String PARAM = "%s=%s";
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

    public void executeCommand(Command... command) {
        String parameter = Arrays.asList(command).stream().map(cmd -> String.format(PARAM, cmd.getCommandName(), cmd.getParameters())).collect(Collectors.joining("&"));
        String stringCommand = String.format(url, PreferenceUtil.getLampIP(), parameter);

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