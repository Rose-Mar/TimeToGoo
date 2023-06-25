package com.example.timetogo.adapter;

import android.content.Context;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.timetogo.R;
import com.example.timetogo.db.entity.ListItem;

import org.jetbrains.annotations.Nullable;

import java.util.List;

public class CustomAdapter3 extends ArrayAdapter<ListItem> {

    private Context context;
    private int resource;
    private List<ListItem> items;








    @Override
    public ListItem getItem(int position) {return items.get(position);}


    public CustomAdapter3(Context context, int resource, List<ListItem> items){
        super(context,resource,items);
        this.context = context;
        this.resource = resource;
        this.items = items;


    }

    private static class ViewHolder {
        TextView nameTextView;
        TextView timeTextView;
        Button startNowButton;
        CountDownTimer countDownTimer;
        long timeInMilliseconds;

    }

    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            convertView = layoutInflater.inflate(resource, parent, false);


            viewHolder.nameTextView = convertView.findViewById(R.id.activityNameTextView);
            viewHolder.timeTextView = convertView.findViewById(R.id.activityTimeTextView);
            viewHolder.startNowButton = convertView.findViewById(R.id.startNowButton);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        ListItem listItem = getItem(position);

//
//        TextView nameTextView = convertView.findViewById(R.id.activityNameTextView);
//        timeTextView = convertView.findViewById(R.id.activityTimeTextView);
//        Button startNowButton = convertView.findViewById(R.id.startNowButton);

        if (listItem != null) {


            viewHolder.nameTextView.setText(listItem.getNameActivity());
            viewHolder.timeTextView.setText(String.valueOf(listItem.getTimeActivity()));
            viewHolder.startNowButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (viewHolder.countDownTimer != null) {
                        viewHolder.countDownTimer.cancel();
                        viewHolder.countDownTimer = null;
                        viewHolder.startNowButton.setText("Start");
                    } else {
                        viewHolder.timeInMilliseconds = ((long) listItem.getTimeActivity()) * 1000 * 60;
                        viewHolder.countDownTimer = new CountDownTimer(viewHolder.timeInMilliseconds, 1000) {
                            @Override
                            public void onTick(long millisUntilFinished) {
                                viewHolder.timeInMilliseconds = millisUntilFinished;
                                updateCountdownText(viewHolder);

                            }

                            @Override
                            public void onFinish() {

                            }
                        }.start();
                        viewHolder.startNowButton.setText("Stop");
                        updateCountdownText(viewHolder);
//                int updatedTime = listItem.getTimeActivity() - 1;

                    }
                }
            });




        }

//        viewHolder.timeTextView = convertView.findViewById(R.id.activityTimeTextView);
        return convertView;
    }

    private void updateCountdownText(ViewHolder viewHolder) {
        int hours = (int) ((viewHolder.timeInMilliseconds / 1000) / 3600);
        int minutes = (int) ((viewHolder.timeInMilliseconds / 1000) % 3600) / 60;
        int seconds = (int) ((viewHolder.timeInMilliseconds / 1000) % 60);

        String timeLeftFormatted = String.format("%02d:%02d:%02d", hours, minutes, seconds);
        viewHolder.timeTextView.setText(timeLeftFormatted);
    }


}
