package com.example.crudsample;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.example.crudsample.Helper.DatabaseHelper;
import com.example.crudsample.adapters.PersonAdapter;
import com.example.crudsample.data.AppData;
import com.example.crudsample.data.Person;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Button addPersonButton;
    DatabaseHelper dbHelper;
    ListView mListView;

    PersonAdapter adapter;
    ArrayList<Person> personList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addPersonButton = findViewById(R.id.add_personButton);
        dbHelper = new DatabaseHelper(this);
        mListView = findViewById(R.id.list_personView);

        // При нажатии на айтем меняем данные
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final Person selectPerson = (Person) mListView.getItemAtPosition(position);
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Update");
                builder.setMessage("A u want update?");
                builder.setCancelable(false);
                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(MainActivity.this, PersonAddActivity.class);
                        intent.putExtra(Person.class.getSimpleName(),selectPerson);
                        startActivity(intent);
                    }
                });
                builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.create().show();
            }
        });



        // Удаление записи при долгом удержании
        mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                final Person selectPerson = (Person) mListView.getItemAtPosition(position);
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Delete");
                builder.setMessage("A u want delete?");
                builder.setCancelable(false);
                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dbHelper.deletePerson(selectPerson.getId());
                        showRecord();
                    }
                });
                builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.create().show();
                return false;
            }
        });


        addPersonButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, PersonAddActivity.class));
            }

        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        showRecord();
    }

    private void showRecord() {

        personList = dbHelper.getAllData(AppData.PERSON_ADD_TIMESTAMP + " DESC");
        adapter = new PersonAdapter(MainActivity.this,personList);
        mListView.setAdapter(adapter);
    }

}