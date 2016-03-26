package uk.ac.hw.macs.nl148.iwatt;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class TimeTableInit extends AppCompatActivity implements View.OnClickListener{

    Button monday;
    Button tuesday;
    Button wednsday;
    Button thursday;
    Button friday;
    TextView heading;
    ArrayList<TimeTableData> tlist = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.h);



        monday = (Button) findViewById(R.id.monday);
        tuesday = (Button) findViewById(R.id.tuesday);
        wednsday = (Button) findViewById(R.id.wednesday);
        thursday = (Button) findViewById(R.id.thursday);
        friday = (Button) findViewById(R.id.friday);
        heading = (TextView) findViewById(R.id.up_heading_3);


        monday.setOnClickListener(this);
        tuesday.setOnClickListener(this);
        wednsday.setOnClickListener(this);
        thursday.setOnClickListener(this);
        friday.setOnClickListener(this);

        Typeface tf = Typeface.createFromAsset(getAssets(), "Simple tfb.ttf");

        heading.setTypeface(tf);
        monday.setTypeface(tf);
        tuesday.setTypeface(tf);
        wednsday.setTypeface(tf);
        thursday.setTypeface(tf);
        friday.setTypeface(tf);

        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar_timetable_init);
        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TimeTableInit.this.finish();
                Intent i = new Intent(TimeTableInit.this,MainActivity.class);
                TimeTableInit.this.startActivity(i);
            }
        });

        TextView toolbar_title = (TextView) toolbar.findViewById(R.id.toolbar_title9);
        toolbar.setTitle("");
        toolbar_title.setTypeface(tf);

        setSupportActionBar(toolbar);

    }

    @Override
    public void onClick(View v) {

        if(monday == v)
        {
            Intent timetable = new Intent(this , TimeTable.class);

            timetable.putExtra("timetabledata", tlist);
            timetable.putExtra("dow", "mon");
            startActivity(timetable);

        }

        if(tuesday == v)
        {
            Intent timetable = new Intent(this , TimeTable.class);
            timetable.putExtra("timetabledata", tlist);
            timetable.putExtra("dow", "tue");
            finish();
            startActivity(timetable);
        }

        if(wednsday == v)
        {
            Intent timetable = new Intent(this , TimeTable.class);

            timetable.putExtra("timetabledata",tlist);

            timetable.putExtra("dow" , "wed");
            startActivity(timetable);
        }

        if(thursday == v)
        {
            Intent timetable = new Intent(this , TimeTable.class);
            timetable.putExtra("timetabledata",tlist);
            timetable.putExtra("dow" , "thurs");
            startActivity(timetable);

        }

        if(friday == v)
        {
            Intent timetable = new Intent(this , TimeTable.class);
            timetable.putExtra("timetabledata",tlist);
            timetable.putExtra("dow" ,"fri");
            startActivity(timetable);
        }

    }
}
