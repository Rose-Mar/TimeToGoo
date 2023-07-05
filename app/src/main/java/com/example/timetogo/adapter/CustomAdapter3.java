package com.example.timetogo.adapter;

import android.content.Context;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.timetogo.MainActivity3;
import com.example.timetogo.R;
import com.example.timetogo.db.entity.ListItem;

import org.jetbrains.annotations.Nullable;

import java.util.List;

public class CustomAdapter3 extends ArrayAdapter<ListItem> {

    private Context context;
    private int resource;
    private List<ListItem> items;
    private MainActivity3 mainActivity3;
    private CountDownTimer countDownTimer;
    private int selectedItemIndex = -1;


    @Override
    public ListItem getItem(int position) {return items.get(position);}


    public CustomAdapter3(Context context, int resource, List<ListItem> items, MainActivity3 mainActivity3){
        super(context,resource,items);
        this.context = context;
        this.resource = resource;
        this.items = items;
        this.mainActivity3 = mainActivity3;


    }

    private static class ViewHolder {
        TextView nameTextView;
        TextView timeTextView;
        Button startNowButton;
        CountDownTimer countDownTimer;
        long timeInMilliseconds;

    }

    public CountDownTimer getCountDownTimer() {
        return countDownTimer;
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

        if (listItem != null) {


            viewHolder.nameTextView.setText(listItem.getNameActivity());
            viewHolder.timeTextView.setText(String.valueOf(listItem.getTimeActivity()));

            if(position ==selectedItemIndex){
                viewHolder.startNowButton.setVisibility(View.GONE);
            } else {

                viewHolder.startNowButton.setVisibility(View.VISIBLE);
                viewHolder.startNowButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        selectedItemIndex = position;
                        notifyDataSetChanged();

                        for(int i =0; i<items.size();i++){
                            if(i !=selectedItemIndex){
                                items.get(i).setButtonVisible(false);
                            }
                        }


                        if (viewHolder.countDownTimer != null) {

                            mainActivity3.startCountdown();
                            viewHolder.countDownTimer.cancel();
                            viewHolder.countDownTimer = null;
                            viewHolder.startNowButton.setText("Start");
                            for (ListItem item :items){
                                item.setButtonVisible(true);
                            }


                        } else {


                            mainActivity3.stopCountdown();
                            viewHolder.timeInMilliseconds = ((long) listItem.getTimeActivity()) * 1000 * 60;
                            viewHolder.countDownTimer = new CountDownTimer(viewHolder.timeInMilliseconds, 1000) {
                                @Override
                                public void onTick(long millisUntilFinished) {
                                    viewHolder.timeInMilliseconds = millisUntilFinished;
                                    updateCountdownText(viewHolder);
                                    if (mainActivity3 != null) {
                                        mainActivity3.stopCountdown();
                                    }
                                }

                                @Override
                                public void onFinish() {

                                    viewHolder.startNowButton.setText("Ended");

                                }

                            }.start();

//                        mainActivity3.startCountdown();
                            viewHolder.startNowButton.setText("Stop");
                            updateCountdownText(viewHolder);
//


                        }
                    }
                });

            }
            if(listItem.isButtonVisible()){
                viewHolder.startNowButton.setVisibility(View.VISIBLE);
            }else {
                viewHolder.startNowButton.setVisibility(View.GONE);
            }



        }

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