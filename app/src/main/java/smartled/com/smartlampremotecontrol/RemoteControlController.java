package smartled.com.smartlampremotecontrol;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.SeekBar;

import com.flask.colorpicker.ColorPickerView;
import com.flask.colorpicker.builder.ColorPickerClickListener;
import com.flask.colorpicker.builder.ColorPickerDialogBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import smartled.com.smartlampremotecontrol.commands.ButtonCommand;
import smartled.com.smartlampremotecontrol.commands.ColorCommand;
import smartled.com.smartlampremotecontrol.commands.Command;

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

        Integer selectedColor = PreferenceUtil.getSelectedColor();

        if (selectedColor != null) {
            final FloatingActionButton colorSelectionButton = findViewById(R.id.colorSelectionButton);
            colorSelectionButton.setBackgroundTintList(ColorStateList.valueOf(selectedColor));
        }
    }

    public void clickButton(View view) {
        Integer color = PreferenceUtil.getSelectedColor();

        List<Command> commands = new ArrayList<>();
        commands.add(new ButtonCommand(view.getTag().toString()));

        if (color != null) {
            commands.add(new ColorCommand(color, color, color));
        }

        LedBroker.getInstance(getApplicationContext()).executeCommand(commands);
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
                        PreferenceUtil.setSelectedColor(selectedColor);
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

        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        input.setText(PreferenceUtil.getLampIP());
        input.selectAll();
        builder.setView(input);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                PreferenceUtil.setLampIP(input.getText().toString());
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
