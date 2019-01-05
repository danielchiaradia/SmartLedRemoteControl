package smartled.com.smartlampremotecontrol;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SeekBar;

import com.flask.colorpicker.ColorPickerView;
import com.flask.colorpicker.OnColorSelectedListener;
import com.flask.colorpicker.builder.ColorPickerClickListener;
import com.flask.colorpicker.builder.ColorPickerDialogBuilder;

import smartled.com.smartlampremotecontrol.commands.ButtonCommand;

public class RemoteControlController extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.remote_control);


        SeekBar brightnessSeekBar = (SeekBar) findViewById(R.id.brightnessSeekBar);
        brightnessSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                LedBroker.getInstance(getApplicationContext()).sendBrightness(seekBar.getProgress());
            }
        });
    }

    public void clickButton(View view) {
        LedBroker.getInstance(getApplicationContext()).executeCommand(new ButtonCommand(view.getTag().toString()));
    }

    public void selectColor(View view) {
        final FloatingActionButton colorSelectionButton = findViewById(R.id.colorSelectionButton);
        ColorPickerDialogBuilder
                .with(view.getContext())
                .setTitle("Choose color")
                //.initialColor(colorSelectionButton.getSolidColor())
                .wheelType(ColorPickerView.WHEEL_TYPE.FLOWER)
                .density(12)
                .noSliders()
                .setPositiveButton("Ok", new ColorPickerClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int selectedColor, Integer[] allColors) {
                        colorSelectionButton.setBackgroundTintList(ColorStateList.valueOf(selectedColor));
                        LedBroker.getInstance(getApplicationContext()).sendColor(selectedColor, selectedColor, selectedColor);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //noop
                    }
                })
                .build()
                .show();
    }

    public void showSettings(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Enter Lamp IP");

        SharedPreferences pref = getApplicationContext().getSharedPreferences(LedBroker.PREFERENCE_NAME, Context.MODE_PRIVATE);

        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        input.setText(pref.getString(LedBroker.LAMP_IP_PREFERENCE_KEY, "0.0.0.0"));
        input.selectAll();
        builder.setView(input);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                SharedPreferences pref = getApplicationContext().getSharedPreferences(LedBroker.PREFERENCE_NAME, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();
                editor.putString(LedBroker.LAMP_IP_PREFERENCE_KEY, input.getText().toString());
                editor.commit();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }
}
