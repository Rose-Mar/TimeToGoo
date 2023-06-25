package com.example.timetogo;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.timetogo.adapter.CustomAdapter;
import com.example.timetogo.adapter.OnItemLongClickListener;
import com.example.timetogo.adapter.OnCheckedChangeListener;
import com.example.timetogo.db.DatabaseHelper;
import com.example.timetogo.db.entity.ListItem;

import java.util.ArrayList;

public class MainActivity2 extends AppCompatActivity implements OnItemLongClickListener, OnCheckedChangeListener {


    ListView listView;


    private static CustomAdapter adapter;



    private DatabaseHelper db ;

    int totalTime = 0;



    public void showDeleteEditDialog(ListItem listItem){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_edit_delete_task, null);
        builder.setView(dialogView);

        AlertDialog dialog = builder.create();
        dialog.show();

        Button deleteBtn = dialogView.findViewById(R.id.deleteBtn);
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                db.deleteItem(listItem);
                refreshListView();
                dialog.dismiss();


            }
        });


        Button editBtn = dialogView.findViewById(R.id.editBtn);
        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.updateListItem(listItem);
            }
        });

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
                try {
                    String addedName = addName.getText().toString();
                    int addedTime = Integer.parseInt(addTime.getText().toString());

                    // Dodaj nowy element do bazy danych
                    long id = db.insertContact(addedName, addedTime);

                    ListItem newItem = new ListItem(addedName, addedTime, 90);

                    adapter.notifyDataSetChanged();

                    refreshListView();
                }
                catch (Exception e) {
                    Toast.makeText(MainActivity2.this, "Wprowadź ponownie aktywność", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();

                }



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

        adapter.setOnItemLongClickListener(this);
//        adapter.setOnCheckedChangeListener(this);

        listView.setAdapter(adapter);




        Button btnAddToDo = findViewById(R.id.addBtn);
        btnAddToDo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAddDialog();



            }
        });


        Button nextBtn = findViewById(R.id.nextCountingBtn);
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                totalTime = 0;

                SparseBooleanArray selectedItems = adapter.getCheckedItems();
                ArrayList<Integer> idList = new ArrayList<>();
                for (int i = 0; i<selectedItems.size();i++){
                    int item = selectedItems.keyAt(i);
                    idList.add(item);
                }

                if(selectedItems.size()==0){
                    Toast.makeText(MainActivity2.this, "Nie zaznaczono żadnych elementów.", Toast.LENGTH_SHORT).show();
                }else{

                    Toast.makeText(MainActivity2.this, "Zaznaczone: " + idList, Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(MainActivity2.this, MainActivity3.class);
                    intent.putExtra("idList", idList);
                    startActivity(intent);


                }

            }
        });
    }




    @Override
    public void onItemLongClick(ListItem listItem){

        showDeleteEditDialog(listItem);
    }




    private void refreshListView() {
        ArrayList<ListItem> dataModels = db.getAkkItems();
        adapter.clear();
        adapter.addAll(dataModels);
        adapter.notifyDataSetChanged();
    }


    @Override
    public void onItemCheckedChange(int position, boolean isChecked) {


        if(isChecked) {
            ListItem listItem = adapter.getItem(position);
            int time = listItem.getTimeActivity();
            totalTime += time;
        }else{

            ListItem listItem = adapter.getItem(position);
            int time = listItem.getTimeActivity();
            totalTime -= time;
        }


    }
}