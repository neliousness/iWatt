package uk.ac.hw.macs.nl148.iwatt;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

public class ProgrammeInfo extends AppCompatActivity {

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
