package smartled.com.smartlampremotecontrol;

/**
 * Created by dchiarad on 02.12.2016.
 */

public class ColorItem {
    private String name;
    private int color;


    public ColorItem(String name) {
        this.name = name;
    }

    public String getName() {
        return  this.name;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }
}
