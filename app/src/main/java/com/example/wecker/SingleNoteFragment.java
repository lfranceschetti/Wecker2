package com.example.wecker;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

public class SingleNoteFragment extends Fragment {
    /*This Class only takes the data from the mDatabaseHelper class and inserts it into the Edittexts and Checkbox*/

    DatabaseHelper mDatabaseHelper;
    ArrayList<String> titleData;
    ArrayList<String> textData;
    ArrayList<Integer> lucidParam;

    CheckBox checkBox;
    EditText title;
    EditText editText;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_note, container,false);



        mDatabaseHelper = new DatabaseHelper(getActivity());
        title = view.findViewById(R.id.titleText);
        editText = view.findViewById(R.id.editText);
        checkBox = view.findViewById(R.id.lucidCheckBox);

        Cursor data = mDatabaseHelper.getData();

        titleData = new ArrayList<>();
        textData = new ArrayList<>();
        lucidParam = new ArrayList<>();

        while (data.moveToNext()) {

                titleData.add(data.getString(1));
                textData.add(data.getString(2));
                lucidParam.add(data.getInt(4));
        }


        MainActivity activity = (MainActivity) getActivity();

        if(!activity.getInfoNewNote()) {
            int position = activity.getListPosition();
            editText.setText(textData.get(position));
            title.setText(titleData.get(position));
            if (lucidParam.get(position)==1){
                checkBox.setChecked(true);
            }else{
                checkBox.setChecked(false);
            }

        }else {
            editText.setText("");
            title.setText("");
        }
        return view;
    }
}
