package com.example.timetogo;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.timetogo.db.entity.ListItem;

import java.util.ArrayList;

public class MainActivity2 extends AppCompatActivity {


    ListView listView;


    private static CustomAdapter adapter;

    ArrayList<ListItem> dataModels;





    public void showAddDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Dodaj czynność");
        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_add_task, null);
        builder.setView(dialogView);

        EditText addName = dialogView.findViewById(R.id.etName);
        EditText addTime = dialogView.findViewById(R.id.etTime);

        builder.setPositiveButton("Dodaj", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String addedName = addName.getText().toString();
                String addedTime = addTime.getText().toString();

                dataModels.add(new ListItem(addedName, addedTime, false));

                //TODO
                //dodać dodawanie do listy
            }
        });

        builder.setNegativeButton("Anuluj", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);



        //AdapterView a list view
        listView = findViewById(R.id.listview);

        //Data sourse
        dataModels = new ArrayList<>();

        dataModels.add(new ListItem("pranie", "15", false));
        dataModels.add(new ListItem("sprzątanie", "12", false));
        dataModels.add(new ListItem("prysznic", "10", true));
        dataModels.add(new ListItem("makijaz", "4", false));
        dataModels.add(new ListItem("dojscie", "12", true));



        //Adapter
        adapter = new CustomAdapter(dataModels,getApplicationContext());


        listView.setAdapter(adapter);




        Button btnAddToDo = findViewById(R.id.addBtn);
        btnAddToDo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAddDialog();

            }
        });




    }
}