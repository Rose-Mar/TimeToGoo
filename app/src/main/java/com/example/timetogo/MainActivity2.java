package com.example.timetogo;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.timetogo.adapter.CustomAdapter;
import com.example.timetogo.adapter.OnItemClickListener;
import com.example.timetogo.db.DatabaseHelper;
import com.example.timetogo.db.entity.ListItem;

import java.util.ArrayList;

public class MainActivity2 extends AppCompatActivity implements OnItemClickListener {


    ListView listView;


    private static CustomAdapter adapter;



    private DatabaseHelper db ;



    public void showDeleteEditDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_edit_delete_task, null);
        builder.setView(dialogView);
    }




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

                ListItem newItem = new ListItem(addedName, addedTime, 90);
                // Dodaj nowy element do bazy danych
                long id = db.insertContact(addedName, addedTime);
                // Ustaw id nowego elementu
                newItem.setId(90);



                // Dodaj nowy element do listy
//                dataModels.add(newItem);
                // Powiadom adapter o zmianach w danych
 //               adapter.notifyDataSetChanged();



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


        db = new DatabaseHelper(this);

        ArrayList<ListItem> dataModels = db.getAkkItems();

        //AdapterView a list view
        listView = findViewById(R.id.listview);


        adapter = new CustomAdapter(dataModels,getApplicationContext());

        adapter.setOnItemClickListener(this);

        listView.setAdapter(adapter);




        Button btnAddToDo = findViewById(R.id.addBtn);
        btnAddToDo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAddDialog();
                adapter.notifyDataSetChanged();
                listView.invalidate();

            }
        });






    }

    @Override
    public void onItemClick(ListItem listItem){

        String clickedActivityName = listItem.getNameActivity();
        String clickedActivityTime = listItem.getTimeActivity();

        Toast.makeText(this, "Kliknięto: " + clickedActivityName, Toast.LENGTH_SHORT).show();



    }





}