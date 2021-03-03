package com.example.wecker;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import java.util.ArrayList;

public class DreamsFragment extends Fragment {
    DatabaseHelper mDatabaseHelper;
    ListView listView;
    static ArrayList<String> texts = new ArrayList<>();
    static ArrayAdapter arrayAdapter;
    ArrayList<String> titleData;
    ArrayList<String> textData;
    ArrayList<String> dateData;
    ArrayList<Integer> lucidData;
    ArrayList<Integer> idData;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dreams, container,false);

        mDatabaseHelper = new DatabaseHelper(getActivity());
        listView = view.findViewById(R.id.listView);
        populateListView();

        //What happens when you click on a listview item
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ((MainActivity)getActivity()).openNote(view, position);
            }
        });

        //What happens when you LONGclick on a listview item
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                new AlertDialog.Builder(getActivity()).setIcon(android.R.drawable.ic_menu_delete). setTitle("Delete")
                        .setMessage("Are you sure that you want to delete this note?").setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mDatabaseHelper.deleteData(idData.get(position));
                        populateListView();
                    }
                }).setNegativeButton("No",null).show();

                return true;
            }
        });

        return view;
    }

    //takes the data and feeds it into the listview
    private void populateListView(){
        Cursor data = mDatabaseHelper.getData();
        lucidData = new ArrayList<>();
        titleData = new ArrayList<>();
        textData = new ArrayList<>();
        dateData = new ArrayList<>();
        idData = new ArrayList<>();
        while (data.moveToNext()) {
            idData.add(data.getInt(0));
            titleData.add(data.getString(1));
            textData.add(data.getString(2));
            dateData.add(data.getString(3));
            lucidData.add(data.getInt(4));
            Log.i("Daten", data.getString(1));
        }

        CustomListAdapter whatever = new CustomListAdapter(getActivity(), titleData, textData, dateData, lucidData);
        listView.setAdapter(whatever);
        }

}
