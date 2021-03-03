package com.example.wecker;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class CustomListAdapter extends ArrayAdapter{

    private final Activity context;
    private final String[] dateArray;
    private final String[] titleArray;
    private final String[] textArray;
    private final String[] cutTextArray;
    ArrayList<Integer> lucidParam2;

    public CustomListAdapter(Activity context, ArrayList<String> titleArrayParam, ArrayList<String> textArrayParam, ArrayList<String> dateArrayParam, ArrayList<Integer> lucidParam){

        /* This class takes the data from Dreamsfragment and helps to put it into the layout file listview_row*/

        super(context,R.layout.listview_row , titleArrayParam);

        this.context=context;
        this.titleArray = titleArrayParam.toArray(new String[0]);
        this.textArray = textArrayParam.toArray(new String[0]);
        this.dateArray = dateArrayParam.toArray(new String[0]);
        this.lucidParam2 = lucidParam;
        this.cutTextArray = textArrayParam.toArray(new String[0]);

    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.listview_row, null, true);

        //this code gets references to objects in the listview_row.xml file
        TextView titleTextField = (TextView) rowView.findViewById(R.id.titleOfDream);
        TextView textTextField = (TextView) rowView.findViewById(R.id.textOfDream);
        TextView dateTextField = (TextView) rowView.findViewById(R.id.dateTextField);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.lucidityImage);

        //cut the text data to only have a limited amount of characters
        for (int i = 0; i < cutTextArray.length; i++)
             if (cutTextArray[i].length() >= 70){
                 cutTextArray[i] = cutTextArray[i].substring(0,70) + "...";
             }

        //this code sets the values of the objects to values from the arrays
        titleTextField.setText(titleArray[position]);
        textTextField.setText(cutTextArray[position]);
        dateTextField.setText(dateArray[position]);
        if (lucidParam2.get(position) == 1){
            imageView.setImageResource(R.drawable.eye);
         }
        return rowView;

    };
}
