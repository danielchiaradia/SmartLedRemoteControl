package smartled.com.smartlampremotecontrol.commands;

public class BrightnessCommand implements Command {

    private static String BRIGHTNESS_PARAMETERS = "%s";
    private int brightness;

    public BrightnessCommand(int brightness) {
        this.brightness = brightness;
    }

    @Override
    public String getParameters() {
        return String.format(BRIGHTNESS_PARAMETERS, brightness);
    }


    @Override
    public String getCommandName() {
        return "brightness";
    }
}