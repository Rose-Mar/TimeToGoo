package com.example.timetogo.adapter;

import android.content.Context;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
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
    private OnItemLongClickListener onItemLongClickListener;
    private OnCheckedChangeListener onCheckedChangeListener;
    private SparseBooleanArray checkedItems;

    public CustomAdapter(ArrayList<ListItem> data, Context context) {
        super(context, R.layout.item_list_layout, data);
        this.itemArrayList = data;
        this.context = context;
        databaseHelper = new DatabaseHelper(context);
        checkedItems = new SparseBooleanArray();
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
            viewHolder.checkBox = convertView.findViewById(R.id.checkBox);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        ListItem listItem = getItem(position);

        if (listItem != null) {
            viewHolder.txtName.setText(listItem.getNameActivity());
            viewHolder.txtTime.setText(String.valueOf(listItem.getTimeActivity()));
            viewHolder.checkBox.setChecked(listItem.isCheck());

            convertView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (onItemLongClickListener != null) {
                        onItemLongClickListener.onItemLongClick(listItem);
                    }
                    return true;
                }
            });
        }

        viewHolder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                listItem.setCheck(isChecked);

                if (onCheckedChangeListener != null) {
                    onCheckedChangeListener.onItemCheckedChange(position, isChecked);
                }
            }
        });

        return convertView;
    }

    public SparseBooleanArray getCheckedItems() {
        SparseBooleanArray selectedItems = new SparseBooleanArray();

        for (int i = 0; i < itemArrayList.size(); i++) {
            if (itemArrayList.get(i).isCheck()) {
                int id = itemArrayList.get(i).getId();
                selectedItems.put(id, true);
            }
        }

        return selectedItems;
    }

    public void setOnCheckedChangeListener(OnCheckedChangeListener listener) {
        this.onCheckedChangeListener = listener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener listener) {
        this.onItemLongClickListener = listener;
    }

    private static class ViewHolder {
        TextView txtName;
        TextView txtTime;
        CheckBox checkBox;
    }

    public interface OnItemLongClickListener {
        void onItemLongClick(ListItem listItem);
    }

    public interface OnCheckedChangeListener {
        void onItemCheckedChange(int position, boolean isChecked);
    }
}
