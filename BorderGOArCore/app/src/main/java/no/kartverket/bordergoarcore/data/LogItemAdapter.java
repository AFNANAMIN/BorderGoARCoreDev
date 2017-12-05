package no.kartverket.bordergoarcore.data;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

import no.kartverket.bordergoarcore.MainActivity;
import no.kartverket.bordergoarcore.R;
import no.kartverket.data.DataLogger;
import no.kartverket.data.DataLogger.LogInfoItem;

/**
 * Created by janvin on 16.06.2017.
 */

public class LogItemAdapter extends ArrayAdapter<LogInfoItem> {
    private DataLogger logger;
    private Context context;
    private MainActivity.ShowDataGraphCallback showDataGraphCallback;
    public LogItemAdapter(@NonNull Context context,  DataLogger logger, MainActivity.ShowDataGraphCallback showDataGraphCallback) {
        super(context, 0, logger.getLogInfoItems());
        this.showDataGraphCallback = showDataGraphCallback;
        this.logger = logger;
        this.context =context;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        LogInfoItem item = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.log_data_item, parent, false);
        }
        // Lookup view for data population
        TextView nameView = (TextView) convertView.findViewById(R.id.logName);
        TextView typeView = (TextView) convertView.findViewById(R.id.logType);
        TextView sizeView = (TextView) convertView.findViewById(R.id.logSize);

        ImageView saveButton = (ImageView) convertView.findViewById(R.id.saveLog);
        final String name = item.name;
        saveButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                File file = logger.makeTextFile(name);
                if(file != null){
                    Toast.makeText(context, "Saved file to "+file.toString() , Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "Unable to save file" , Toast.LENGTH_SHORT).show();
                }

            }
        });

        ImageView graphButton = (ImageView) convertView.findViewById(R.id.viewGraph);

        graphButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showDataGraphCallback.dataToGraph(name);
            }
        });


        // Populate the data into the template view using the data object
        nameView.setText(item.name);
        typeView.setText(item.logType.toString());
        sizeView.setText("size: " + item.size);
        // Return the completed view to render on screen
        return convertView;
    }

}
