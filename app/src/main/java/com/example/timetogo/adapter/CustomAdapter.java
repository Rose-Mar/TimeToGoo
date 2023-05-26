package com.example.timetogo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.timetogo.R;
import com.example.timetogo.db.entity.ListItem;
import com.example.timetogo.db.DatabaseHelper;

import java.util.ArrayList;

public class CustomAdapter extends ArrayAdapter<ListItem> {

    private ArrayList<ListItem> itemArrayList;
    private Context context;
    private DatabaseHelper databaseHelper;
    private OnItemClickListener onItemClickListener;


    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    public CustomAdapter(ArrayList<ListItem> data, Context context) {
        super(context, R.layout.item_list_layout, data);
        this.itemArrayList = data;
        this.context = context;
        databaseHelper = new DatabaseHelper(context);
    }

    private static class ViewHolder {
        TextView txtName;
        TextView txtTime;
//        CheckBox checkBox;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.item_list_layout, parent, false);

            viewHolder.txtName = convertView.findViewById(R.id.activityName);
            viewHolder.txtTime = convertView.findViewById(R.id.activityTime);
//            viewHolder.checkBox = convertView.findViewById(R.id.checkBox);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        ListItem listItem = getItem(position);

        if (listItem != null) {
            viewHolder.txtName.setText(listItem.getNameActivity());
            viewHolder.txtTime.setText(listItem.getTimeActivity());

            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(onItemClickListener  != null){
                        onItemClickListener.onItemClick(listItem);
                    }

                }
            });





//            viewHolder.checkBox.setChecked(listItem.isCheck());
        }

//        viewHolder.checkBox.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                CheckBox checkBox = (CheckBox) v;
//                ListItem listItem = getItem(position);
//                if (listItem != null) {
//                    listItem.setCheck(checkBox.isChecked());
//                    databaseHelper.updateListItem(listItem);
//                }
//            }
//        });

        return convertView;
    }
}
