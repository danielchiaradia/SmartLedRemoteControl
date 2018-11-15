package smartled.com.smartlampremotecontrol.commands;

import android.support.v4.graphics.ColorUtils;

public class ColorCommand implements Command {

        private static String COLOR_PARAMETERS = "%s,%s,%s";

        private final int hsvColorOne;
        private final int hsvColorTwo;
        private final int hsvColorThree;

        public ColorCommand(int hsvColorOne, int hsvColorTwo, int hsvColorThree) {
            this.hsvColorOne = hsvColorOne;
            this.hsvColorTwo = hsvColorTwo;
            this.hsvColorThree = hsvColorThree;
        }

        @Override
        public String getParameters() {
            return  String.format(COLOR_PARAMETERS, getHue(hsvColorOne), getHue(hsvColorTwo), getHue(hsvColorThree));
        }

        @Override
        public String getCommandName() {
            return "color";
        }

        private int getHue(int color) {
            float[] hsv = new float[3];
            ColorUtils.colorToHSL(color, hsv);

            return (int) hsv[0];
        }
    }