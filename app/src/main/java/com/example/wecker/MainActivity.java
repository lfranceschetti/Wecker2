package com.example.wecker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.app.FragmentManager;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    DatabaseHelper mDatabaseHelper;
    EditText title;
    EditText editText;
    private Integer listPosition;
    Boolean noteIsNew;
    CheckBox checkbox;
    Integer lucidityCheck;


    public void saveNote(View view){
        title = findViewById(R.id.titleText);
        editText = findViewById(R.id.editText);
        checkbox = findViewById(R.id.lucidCheckBox);
        String titleText = title.getText().toString();
        String text = editText.getText().toString();
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE, dd.MM.yyyy");
        Date d = new Date();
        String currentDate = dateFormat.format(d);
        if (checkbox.isChecked()){
            lucidityCheck = 1;
        }else{
            lucidityCheck = 0;
        }
        if(noteIsNew) {
            mDatabaseHelper.addData(titleText,text,currentDate, lucidityCheck);
        }else{
            mDatabaseHelper.changeData(titleText,text,listPosition, lucidityCheck);
        }

        Fragment listFragment = new DreamsFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        transaction.replace(R.id.fragment_container, listFragment);
        transaction.addToBackStack(null);

        transaction.commit();



    }

    public void addNote(View view){

        noteIsNew = true;
        Fragment noteFragment = new SingleNoteFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        transaction.replace(R.id.fragment_container, noteFragment);
        transaction.addToBackStack(null);

        transaction.commit();
        Toast.makeText(this, "New Note Added", Toast.LENGTH_LONG).show();

    }

    public Integer getListPosition(){
        return listPosition;
    }

    public Boolean getInfoNewNote(){
        return noteIsNew;
    }

    public void openNote(View view, Integer position){

        noteIsNew = false;
        listPosition = position;

        Fragment noteFragment = new SingleNoteFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();


        transaction.replace(R.id.fragment_container, noteFragment);
        transaction.addToBackStack(null);

        transaction.commit();
        Toast.makeText(this, "Old Note opened", Toast.LENGTH_LONG).show();

    }


    public void showTimePickerDialog(View v){

        DialogFragment newFragment = new TimePickerFragment();
        newFragment.show(getSupportFragmentManager(), "timePicker");

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDatabaseHelper = new DatabaseHelper(this);
        Toast.makeText(this,"i love Luga", Toast.LENGTH_LONG).show();
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        Fragment fragment = new AlarmFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                fragment, "alarm").commit();


    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;

                    switch (item.getItemId()){
                        case R.id.nav_home:
                            selectedFragment = new AlarmFragment();
                            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                                    selectedFragment, "alarm").commit();
                            break;
                        case R.id.nav_stats:
                            selectedFragment = new StatsFragment();
                            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                                    selectedFragment,"stats").commit();
                            break;
                        case R.id.nav_dreams:
                            selectedFragment = new DreamsFragment();
                            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                                    selectedFragment,"dreams").commit();
                            break;

                    }
                    return true;
                }
            };





}
