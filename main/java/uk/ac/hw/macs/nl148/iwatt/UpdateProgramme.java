package uk.ac.hw.macs.nl148.iwatt;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.stmt.UpdateBuilder;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Author: Neio Lucas
 * File : UpdateProgramme.java
 * Platform : Android Operating System
 * Date:  17/03/2016.
 * Description: This activity allows the user to update their programme details
 */

public class UpdateProgramme extends AppCompatActivity implements View.OnClickListener {

    View view;
    AutoCompleteTextView myCompleteTextView;
    Button update;
    Button exit;
    NumberPicker year;
    TextView heading;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.e);
        Firebase.setAndroidContext(this);


        update = (Button) findViewById(R.id.update_button);
        exit = (Button) findViewById(R.id.exit_programme_button);
        myCompleteTextView = (AutoCompleteTextView) findViewById(R.id.auto_update);
        year = (NumberPicker) findViewById(R.id.year_update);
        heading = (TextView) findViewById(R.id.up_heading_2);

        update.setOnClickListener(this);
        exit.setOnClickListener(this);

        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar_prog_setting);
        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //close this activity when toolbar is pressed
                UpdateProgramme.this.finish();

            }
        });

        TextView toolbar_title = (TextView) toolbar.findViewById(R.id.toolbar_title6);
        Typeface tf = Typeface.createFromAsset(getAssets(), "Simple tfb.ttf");
        toolbar.setTitle("");
        toolbar_title.setTypeface(tf);
        setSupportActionBar(toolbar);


        ArrayList<String> list = getIntent().getStringArrayListExtra("programmes");
        ArrayAdapter arrayAdapter = new ArrayAdapter(getApplicationContext(), android.R.layout.select_dialog_item, list);
        // tx.setTypeface(tf);

        DBHelper dbHelper = OpenHelperManager.getHelper(getApplicationContext(), DBHelper.class);
        RuntimeExceptionDao<LocalProgramme, Object> programmeDao = dbHelper.getProgrammeExceptionDao();
        List<LocalProgramme> programme = programmeDao.queryForAll();


        year.setMinValue(1);
        year.setMaxValue(5);
        year.setValue(programme.get(0).getYear());

        myCompleteTextView.setText(programme.get(0).getProgDesc());

        //closing database connection
        OpenHelperManager.releaseHelper();



        myCompleteTextView.setDropDownBackgroundResource(R.drawable.backg);
        myCompleteTextView.setThreshold(1);
        myCompleteTextView.setTypeface(tf);
        myCompleteTextView.setAdapter(arrayAdapter);
        heading.setTypeface(tf);
        update.setTypeface(tf);
        exit.setTypeface(tf);


    }

    @Override
    public void onClick(View v) {

        if(update == v) {
            try {

                //opening database connection
                DBHelper  dbHelper = OpenHelperManager.getHelper(getApplicationContext(), DBHelper.class);
                RuntimeExceptionDao<LocalProgramme, Object>  programmeDao = dbHelper.getProgrammeExceptionDao();

                //storing programme name into a list
                List<LocalProgramme> programmes = programmeDao.queryForAll();
                List<LocalProgramme> locall = programmeDao.queryForAll();

                //creating a builder to update LocalProgramme table
                UpdateBuilder<LocalProgramme,Object> updateBuilder = programmeDao.updateBuilder();
                for ( LocalProgramme lcc : locall) {

                    try {
                        //update table where progCode equals the value ( lcc.getProgCode())
                        updateBuilder.where().eq("progCode", lcc.getProgCode());
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }

                    try {
                        // update the value of of  year and progDesc where progCode equals the value ( lcc.getProgCode())
                        updateBuilder.updateColumnValue("progDesc" /* column */, myCompleteTextView.getText().toString() /* value */);
                        updateBuilder.updateColumnValue("year" /* column */, year.getValue() + "" /* value */);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }



                        //check if user changed data. if not then display a message
                    if (programmes.get(0).getProgDesc().contentEquals(myCompleteTextView.getText().toString()) && (programmes.get(0).getYear() + "").contentEquals(year.getValue() + "")) {
                        Toast.makeText(this, "No changes to update", Toast.LENGTH_SHORT).show();
                    } else if (Integer.parseInt(year.getValue() + "".toString()) > programmes.get(0).getLength()) {
                        Toast.makeText(this, "Year entered exceeds course length. please enter a year smaller or equal " + programmes.get(0).getLength(), Toast.LENGTH_SHORT).show();
                    } else {

                        try {
                            // complete update transaction
                            updateBuilder.update();
                            Log.d("update course  status", lcc.toString());
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }

                        //establish a connection to Firebase database
                        Firebase cor = new Firebase("https://testering.firebaseio.com/");
                        cor.addValueEventListener(new ValueEventListener() {

                            DBHelper dbHelper2 = OpenHelperManager.getHelper(getApplicationContext(), DBHelper.class);
                            RuntimeExceptionDao<LocalProgramme, Object> programmeDao = dbHelper2.getProgrammeExceptionDao();
                            RuntimeExceptionDao<LocalCourse, Object> courseDao = dbHelper2.getCourseExceptionDao();

                            List<LocalProgramme> locall = programmeDao.queryForAll();

                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {


                                List<LocalCourse> log_delete = courseDao.queryForAll();

                                //delete courses from old year
                                courseDao.delete(log_delete);
                                for (DataSnapshot progshot : dataSnapshot.getChildren()) {
                                    Course c = progshot.getValue(Course.class);


                                    //check if courses correspond to programme year
                                    if (locall.get(0).getYear() == c.getYear()) {

                                        //if not mandatory , course is added automatically as a subject
                                        if (c.getMandatory().equals("yes")) {


                                            courseDao.createIfNotExists(new LocalCourse(c.getCode(), c.getYear(), c.getCoursename(), c.getCoordinator(), c.getMandatory()));
                                        }
                                    }

                                }
                                //List<LocalCourse> log_course = courseDao.queryForAll();
                                //Log.d("mandatory courses", log_course.toString());
                            }

                            @Override
                            public void onCancelled(FirebaseError firebaseError) {

                            }

                        });

                        Intent i = new Intent(this, MainActivity.class);
                        finish();
                        startActivity(i);
                        OpenHelperManager.releaseHelper();

                        Toast.makeText(this, "Programme Information updated!", Toast.LENGTH_SHORT).show();
                    }
                }

            } catch (Exception e)
            {

            }

        }

        if(exit == v)
        {
            Intent close = new Intent(this, MainActivity.class);
            finish();
            startActivity(close);

        }

    }
}
