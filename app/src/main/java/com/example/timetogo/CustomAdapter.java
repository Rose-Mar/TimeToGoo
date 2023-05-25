package com.example.timetogo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.timetogo.db.entity.ListItem;

import java.util.ArrayList;

public class CustomAdapter extends ArrayAdapter<ListItem> {



    private ArrayList<ListItem> itemArrayList;
    Context context;

    public CustomAdapter (ArrayList<ListItem> data, Context context) {
        super(context, R.layout.item_list_layout, data);
        this.itemArrayList = data;
        this.context = context;

    }

    //View Lookup Casche

    private static class ViewHolder{
        TextView txtName;
        TextView txtTime;
        CheckBox checkBox;

    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {



        // Get the data item for this position


        ListItem dataModel = getItem(position);

        //Check if en existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder;

        final View result;
        if(convertView == null){
            viewHolder = new ViewHolder();

            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.item_list_layout,parent, false);

            viewHolder.txtName = (TextView) convertView.findViewById(R.id.activityName);
            viewHolder.txtTime = (TextView) convertView.findViewById(R.id.activityTime);
            viewHolder.checkBox = (CheckBox) convertView.findViewById(R.id.checkBox);

            result = convertView;
            convertView.setTag(viewHolder);


        } else{
            viewHolder = (ViewHolder) convertView.getTag();
            result = convertView;
        }


        //Get data from the model class

        viewHolder.txtName.setText(dataModel.getNameActivity());
        viewHolder.txtTime.setText(dataModel.getTimeActivity());
        viewHolder.checkBox.setChecked(dataModel.isCheck());


        return convertView;




    }
}
