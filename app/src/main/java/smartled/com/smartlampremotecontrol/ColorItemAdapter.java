package smartled.com.smartlampremotecontrol;

import android.content.Context;
import android.graphics.PorterDuff;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by dchiarad on 02.12.2016.
 */
public class ColorItemAdapter extends ArrayAdapter<ColorItem> {
    public ColorItemAdapter(Context context, ArrayList<ColorItem> users) {
        super(context, 0, users);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        ColorItem colorItem = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.color_item, parent, false);
        }
        // Lookup view for data population
        TextView tvName = (TextView) convertView.findViewById(R.id.tvName);

        Button colorSelection = (Button) convertView.findViewById(R.id.color);
        colorSelection.setTag(position);
        colorSelection.getBackground().setColorFilter(colorItem.getColor(), PorterDuff.Mode.MULTIPLY);
        // Populate the data into the template view using the data object


        tvName.setText(colorItem.getName());


        // Return the completed view to render on screen
        return convertView;
    }
}