package com.example.wecker;

import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class StatsFragment extends Fragment {

    DatabaseHelper mDatabaseHelper;
    ArrayList<Integer> lucidData;
    ArrayList<String> dateData;
    ArrayList<String> textArray;
    ArrayList<Integer> idData;
    ArrayList<Integer> onlyLucidData;
    ArrayList<Date> dates;
    ArrayList<Date> datesForCalc;
    ArrayList<String> finalDates;

    Date currentDate;
    Date pastDate;
    Date changingDate;

    Button button1;
    Button button2;
    Button button3;

    ArrayList<BarEntry> entries;
    BarChart barChart;
    ArrayList<Integer> colors;
    int lucidColor;
    int notLucidColor;
    int numberOfDays;

    int totalDreams;
    int lucidDreams;
    int dreamDays;
    int percentage;
    int numberOfCharsNormal;
    int numberOfCharsLucid;
    int whileLoop;




    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_stats, container,false);

        mDatabaseHelper = new DatabaseHelper(getActivity());
        barChart = view.findViewById(R.id.barChart);
        colors = new ArrayList<>();
        lucidData = new ArrayList<>();
        dateData = new ArrayList<>();
        dates = new ArrayList<>();
        textArray = new ArrayList<>();
        onlyLucidData = new ArrayList<>();
        idData = new ArrayList<>();
        entries = new ArrayList<BarEntry>();
        button1 = view.findViewById(R.id.button1);
        button2 = view.findViewById(R.id.button2);
        button3 = view.findViewById(R.id.button3);
        currentDate = new Date();
        changingDate = new Date();
        pastDate = new Date();

        numberOfDays = 30;
        entries.clear();
        getData();
        updateNumberStats();
        createDiagram();




        final Spinner dropdown = view.findViewById(R.id.dropDownMenu);
        String[] items = new String[]{"Last 7 days", "Last 14 days", "Last 30 days", "Last 100 days"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(view.getContext(), R.layout.spinner_item, items);
        dropdown.setAdapter(adapter);

        dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                // TODO Auto-generated method stub
                String selected=dropdown.getSelectedItem().toString();



                if (selected == "Last 7 days"){
                    numberOfDays = 7;
                } else if (selected == "Last 14 days"){
                    numberOfDays = 14;
                } else if (selected == "Last 30 days") {
                    numberOfDays = 30;
                }else if (selected == "Last 100 days") {
                    numberOfDays = 100;
                }else{
                    numberOfDays = 7;
                }

                entries.clear();
                getData();
                updateNumberStats();
                createDiagram();


            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });


        return view;
    }


    public void createDiagram(){

        SimpleDateFormat fmt = new SimpleDateFormat("EEEE, dd.MM.yyyy");


        for (int j = 1; j < numberOfDays+1; j++) {
            Calendar cal2 = Calendar.getInstance();
            cal2.setTime(currentDate);
            cal2.add(Calendar.DATE, -numberOfDays+j);
            changingDate = cal2.getTime();
            //Log.i("Tag", fmt.format(changingDate));
            for (int i = 0; i < dateData.size(); i++) {
                numberOfCharsLucid = 0;
                numberOfCharsNormal = 0;
                Log.i("changingDate", fmt.format(changingDate));
                Log.i("DateData", dateData.get(i));

                if (fmt.format(changingDate).equals(dateData.get(i))) {
                    String textString = textArray.get(i);
                    Log.i("String", textString);
                    if (lucidData.get(i) == 1) {
                        numberOfCharsLucid = numberOfCharsLucid + textString.length();
                    } else {
                        numberOfCharsNormal = numberOfCharsNormal + textString.length();
                    }
                }
                int totalChars = numberOfCharsLucid+numberOfCharsNormal;
                entries.add(new BarEntry(j, new float[]{numberOfCharsLucid, totalChars}));
            }


        }


        BarDataSet set = new BarDataSet(entries, "BarDataSet");
        BarData data = new BarData(set);

        notLucidColor = Color.parseColor("#6200EE");
        lucidColor = Color.parseColor("#03DAC5");
        colors.add(lucidColor);
        colors.add(notLucidColor);

        set.setColors(colors);

        data.setBarWidth(0.9f); // set custom bar width
        barChart.setData(data);
        barChart.setFitBars(true); // make the x-axis fit exactly all bars
        barChart.invalidate(); // refresh




    }

    public void getData(){
        idData.clear();
        lucidData.clear();
        dateData.clear();
        textArray.clear();
        Cursor data = mDatabaseHelper.getData();
        while (data.moveToNext()) {
            textArray.add(data.getString(2));
            idData.add(data.getInt(0));
            lucidData.add(data.getInt(4));
            dateData.add(data.getString(3));
        }

    }

    public void updateNumberStats(){
        onlyLucidData.clear();
        dates.clear();
        for (int i = 0; i < lucidData.size(); i++) {
            if(lucidData.get(i)==1)
            onlyLucidData.add(lucidData.get(i));
        }
        for (int i = 0; i < dateData.size(); i++) {
            String sDate1= dateData.get(i);
            try {
                Date date1=new SimpleDateFormat("EEEE, dd.MM.yyyy").parse(sDate1);
                dates.add(date1);
            } catch (ParseException e) {
                e.printStackTrace();
            }

        }
        currentDate = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(currentDate);
        cal.add(Calendar.DATE, -numberOfDays);
        pastDate= cal.getTime();




        datesForCalc = new ArrayList<>();
        finalDates = new ArrayList<>();
        finalDates.clear();
        datesForCalc.clear();
        for (int i = 0; i < dates.size(); i++){
            if (dates.get(i).after(pastDate) && dates.get(i).before(currentDate)){
                datesForCalc.add(dates.get(i));
            }
        }


        for (int i = 0; i < datesForCalc.size(); i++) {
            SimpleDateFormat fmt = new SimpleDateFormat("dd.MM.yyyy");
            String date = fmt.format(datesForCalc.get(i));
            finalDates.add(date);
        }

        Set<String> set = new HashSet<>(finalDates);
        finalDates.clear();
        finalDates.addAll(set);


        for (int i = 0; i < finalDates.size(); i++) {
            Log.i("Datum mit Traum", finalDates.get(i));
        }

        dreamDays = finalDates.size();

        Log.i("geträumte Tage", Integer.toString(dreamDays));
        percentage = (100*dreamDays) / numberOfDays;

        totalDreams = idData.size();
        lucidDreams = onlyLucidData.size();
        button1.setText(Integer.toString(totalDreams));
        button2.setText(Integer.toString(lucidDreams));
        button3.setText(Integer.toString(percentage)+"%");

    }

}
