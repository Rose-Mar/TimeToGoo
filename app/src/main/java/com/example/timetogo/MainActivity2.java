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
                String addedName = addName.getText().toString();
                int addedTime = Integer.parseInt(addTime.getText().toString());

                // Dodaj nowy element do bazy danych
                long id = db.insertContact(addedName, addedTime);

                ListItem newItem = new ListItem(addedName, addedTime, 90);

                adapter.notifyDataSetChanged();

                refreshListView();



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
        adapter.setOnCheckedChangeListener(this);

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

//                Toast.makeText(MainActivity2.this, "czas: " + totalTime, Toast.LENGTH_SHORT).show();



                if(totalTime == 0){
                    Toast.makeText(MainActivity2.this, "Nie zaznaczono żadnych elementów.", Toast.LENGTH_SHORT).show();
                                }
                else {
                    Intent intent = new Intent(MainActivity2.this, MainActivity3.class);
                    intent.putExtra("totalTime", totalTime);
                    startActivity(intent);
                }


//                if (listView.getCheckedItemCount() > 0) {
//                    SparseBooleanArray checkedItems = listView.getCheckedItemPositions();
//                    ArrayList<ListItem> selectedActivities = new ArrayList<>();
//
//                    for (int i = 0; i < checkedItems.size(); i++) {
//                        int position = checkedItems.keyAt(i);
//                        if (checkedItems.valueAt(i)) {
//                            ListItem listItem = adapter.getItem(position);
//                            int time = Integer.parseInt(listItem.getTimeActivity());
//                            totalTime += time;
//                            selectedActivities.add(adapter.getItem(position));
//                        }
//                    }
//
//                    Toast.makeText(MainActivity2.this, "Suma czasów: " + totalTime, Toast.LENGTH_SHORT).show();
//
//                    // Przekazanie danych do następnej aktywności
//                    Intent intent = new Intent(MainActivity2.this, MainActivity3.class);
//                    intent.putExtra("totalTime", totalTime);
//                    // Możesz również przekazać listę wybranych czynności za pomocą Parcelable lub Serializable
//                    startActivity(intent);
//                } else {
//                    Toast.makeText(MainActivity2.this, "Nie zaznaczono żadnych elementów.", Toast.LENGTH_SHORT).show();
//                }
            }
        });
    }

//
//
////                if (!dataModels.isEmpty()) {
////                    ListItem firstItem = dataModels.get(0);
////                    boolean isCheckboxChecked = firstItem.isCheck();
////                    Toast.makeText(MainActivity2.this, "Nie zaznaczono żadnych elementów." + isCheckboxChecked, Toast.LENGTH_SHORT).show();
////
////                    // Tutaj możesz użyć wartości isCheckboxChecked do odpowiednich działań
////                    // np. sprawdzenia czy jest zaznaczony lub nie
////                } else {
////                    // Lista jest pusta, nie ma pierwszej pozycji
////                }
//
//
//                if(listView.getCheckedItemCount()>0){
//                    SparseBooleanArray checkedItems = listView.getCheckedItemPositions();
//
//                }else{
//                    Toast.makeText(MainActivity2.this, "Nie zaznaczono żadnych elementów.", Toast.LENGTH_SHORT).show();
//
//                }
//
////                SparseBooleanArray checkedItems = listView.getCheckedItemPositions();
////                int selectedCount = checkedItems.size();
////                Toast.makeText(MainActivity2.this, "Nie wybrano żadnych elementów." + selectedCount, Toast.LENGTH_SHORT).show();
////                ArrayList<ListItem> selectedActivities = new ArrayList<>();
////                for(int i = 0; i<checkedItems.size();i++) {
////                    int position = checkedItems.keyAt(i);
////                    if(checkedItems.valueAt(i)){
////                        ListItem listItem = adapter.getItem(position);
////                        int time = Integer.parseInt(listItem.getTimeActivity());
////                        totalTime +=time;
////                        selectedActivities.add(adapter.getItem(position));
////                    }
////                }
////
////                Toast.makeText(MainActivity2.this, "Suma czasów: " + totalTime, Toast.LENGTH_SHORT).show();
////
//
////                Intent intent = new Intent(MainActivity2.this, MainActivity3.class);
////                intent.putExtra("totalTime", totalTime);
////                startActivity(intent);
//            }
//        });
//
//    }

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






//        Toast.makeText(MainActivity2.this, "Suma czasów: " + totalTime, Toast.LENGTH_SHORT).show();



    }
}