package uk.ac.hw.macs.nl148.iwatt;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;

/**
 * Author: Neio Lucas
 * File : TimeTableCourse.java
 * Platform : Android Operating System
 * Date:  19/02/2016.
 * Description: This activty displays the course details when selected from the Alert dialogue after
 * a lecture is pressed (in the time table feature)
 */


public class TimeTableCourse extends AppCompatActivity {

    TextView aims;
    TextView syllabus;
    TextView c_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_table_course);
        Firebase.setAndroidContext(this);


        aims = (TextView) findViewById(R.id.timetable_aims_content);
        syllabus = (TextView) findViewById(R.id.timetable_syllabus_content);
        c_title = (TextView) findViewById(R.id.timetable_course_name);

        final String code = getIntent().getStringExtra("code");
        final String title = getIntent().getStringExtra("title");
        final ArrayList<Course> c = new ArrayList<>();


        Firebase course = new Firebase("https://testering.firebaseio.com/");
        course.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //studentDao.delete(student);
                // OpenHelperManager.releaseHelper();

                for (DataSnapshot progshot : dataSnapshot.getChildren()) {
                    Course course = progshot.getValue(Course.class);

                    if (course.getCode().equals(code)) {
                        c.add(course);
                        Log.d("show", course.toString());
                        Log.d("This is the list size", c.size()+"");

                    }
                }

                Typeface tf = Typeface.createFromAsset(getApplication().getAssets(), "Simple tfb.ttf");
                aims.setText(c.get(0).getAims());
                syllabus.setText(c.get(0).getSyllabus());
                c_title.setText(c.get(0).getCoursename());
                c_title.setTypeface(tf);







            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
