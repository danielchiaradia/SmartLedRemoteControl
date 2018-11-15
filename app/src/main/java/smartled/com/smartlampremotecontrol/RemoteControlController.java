package smartled.com.smartlampremotecontrol;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.SeekBar;

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
}
