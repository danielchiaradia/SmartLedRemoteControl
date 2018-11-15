package smartled.com.smartlampremotecontrol.commands;

public class ButtonCommand implements Command {

    private String button;

    public ButtonCommand(String button) {
        this.button = button;
    }

    @Override
    public String getParameters() {
        return button;
    }

    @Override
    public String getCommandName() {
        return "btn";
    }
}