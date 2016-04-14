package uk.ac.hw.macs.nl148.iwatt;

import android.app.FragmentManager;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.RuntimeExceptionDao;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: Neio Lucas
 * File : ProgrammeInfo.java
 * Platform : Android Operating System
 * Date:  04/03/2016.
 * Description: This activity displays the details for each programme that is clicked from the list displayed
 * in the Programmes feature.
 */

public class ProgrammeInfo extends AppCompatActivity  {

    TextView coursename;
    TextView coordinator;
    TextView aims;
    TextView syllabus;
    TextView year;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.g);

        ArrayList<Course> courses = (ArrayList<Course>) getIntent().getSerializableExtra("courses");
        String course = getIntent().getStringExtra("course");

        coursename = (TextView) findViewById(R.id.course_name);
        coordinator  = (TextView) findViewById(R.id.coordinator);
        aims  = (TextView) findViewById(R.id.aims_content);
        syllabus  = (TextView) findViewById(R.id.syllabus_content);
        year =  (TextView) findViewById(R.id.stage_year);

        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar_prog_info);
        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ProgrammeInfo.this.finish();

            }
        });
        TextView toolbar_title = (TextView) toolbar.findViewById(R.id.toolbar_title8);
        Typeface tf = Typeface.createFromAsset(getAssets(), "Simple tfb.ttf");
        toolbar.setTitle("");
        toolbar_title.setTypeface(tf);


        setSupportActionBar(toolbar);


        for(Course c : courses)
        {
            if(course.contentEquals(c.getCoursename()))
            {

                        coursename.setTypeface(tf);
                        coursename.setText(c.getCoursename());
                        coordinator.setText("Co-ordinator :" + c.getCoordinator());
                        aims.setText(c.getAims());
                        syllabus.setText(c.getSyllabus());
                        year.setText("Mandatory :" + c.getMandatory() + "");
            }
        }

    }



}
