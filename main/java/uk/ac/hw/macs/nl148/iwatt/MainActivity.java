package uk.ac.hw.macs.nl148.iwatt;

import android.app.FragmentManager;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.stmt.UpdateBuilder;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener , NavigationView.OnNavigationItemSelectedListener {


    DBHelper dbHelper;
    Button timetable_button;
    Button knowgo_button;
    Button settings_button;
    Button programme_button;
    Button campus_guide_button;
    Button lec_rate;
    Button enquires;
    Button online_library;
    RuntimeExceptionDao<Student, Object> studentDao = null;
    RuntimeExceptionDao<LocalProgramme, Object> localProgrammesDao = null;
    RuntimeExceptionDao<LocalCourse, Object> courseDao = null;
    TextView programeName;
    TextView studentName;
    String name = "";
    String programme = "";
    String surname = "";
    ArrayList<String> holdlist = new ArrayList<>();
    ArrayList<Course> corlist = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);

        TextView toolbar_title = (TextView) toolbar.findViewById(R.id.toolbar_title);
        Typeface tf = Typeface.createFromAsset(getAssets(), "Simple tfb.ttf");
        toolbar.setTitle("");
        toolbar_title.setTypeface(tf);


        setSupportActionBar(toolbar);

        Firebase.setAndroidContext(this);

        Firebase prog = new Firebase("https://glowing-heat-7374.firebaseio.com/");
        Firebase cor = new Firebase("https://testering.firebaseio.com/");

        prog.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot progshot : dataSnapshot.getChildren()) {
                    OnlineProgramme prog = progshot.getValue(OnlineProgramme.class);
                    holdlist.add(prog.getProgDesc());
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        cor.addValueEventListener(new ValueEventListener() {
            DBHelper dbHelper;
            RuntimeExceptionDao<LocalProgramme, Object> localDao;
            RuntimeExceptionDao<LocalCourse, Object> courseDao;

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                dbHelper = OpenHelperManager.getHelper(getApplicationContext(), DBHelper.class);
                localDao = dbHelper.getProgrammeExceptionDao();
                courseDao = dbHelper.getCourseExceptionDao();
                List<LocalProgramme> l = localDao.queryForAll();


                    List<LocalCourse> lc = courseDao.queryForAll();



                    Log.d("year :" ,l.get(0).getYear()+" size :"+ lc.size()+"");
                    Log.d("year :" , l.get(0).getYear() +" content in main" + lc.toString());


                    for (DataSnapshot progshot : dataSnapshot.getChildren()) {
                        Course c = progshot.getValue(Course.class);
                        corlist.add(c);
                    }

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


       try {
           dbHelper = OpenHelperManager.getHelper(this, DBHelper.class);

           settings_button = (Button) findViewById(R.id.welcome);


           studentDao = dbHelper.getStudentExceptionDao();



           List<Student> students = studentDao.queryForAll();




            name = students.get(0).getName();
            surname = students.get(0).getSurname();


           //Toast.makeText(this,"list size" + student.size(),Toast.LENGTH_SHORT).show();
           //studentDao.delete(student);
           settings_button.setText("Hi " + name);
           Typeface name_tf = Typeface.createFromAsset(getAssets(),"Simple tfb.ttf");
           settings_button.setTypeface(name_tf);
          // OpenHelperManager.releaseHelper();
       }catch (Exception e)
       {
           e.printStackTrace();
       }



        timetable_button = (Button) findViewById(R.id.timetable);
        timetable_button.setOnClickListener(this);
        knowgo_button = (Button) findViewById(R.id.knowgo);
        knowgo_button.setOnClickListener(this);
        programme_button = (Button) findViewById(R.id.programme);
        programme_button.setOnClickListener(this);
        campus_guide_button = (Button) findViewById(R.id.guide);
        campus_guide_button.setOnClickListener(this);
        lec_rate = (Button) findViewById(R.id.rate);
        lec_rate.setOnClickListener(this);
        enquires = (Button) findViewById(R.id.enquiry);
        enquires.setOnClickListener(this);
        online_library = (Button) findViewById(R.id.library);
        online_library.setOnClickListener(this);




    }
    public ArrayList<String> getProgrames()
    {

            return holdlist;
    }

    public ArrayList<Course> getCourses()
    {
         return corlist ;
    }
    @Override
    public void onClick(View v) {

        if(knowgo_button == v)
        {
            Intent knowgo = new Intent(this , KnowGo.class);
            startActivity(knowgo);

        }
        if(timetable_button == v)
        {
            Intent timetableinit = new Intent(this, TimeTableInit.class);
            startActivity(timetableinit);
        }

        if(programme_button == v)
        {
            Intent prog = new Intent(this , Programme.class);
            startActivity(prog);
        }

        if(campus_guide_button == v)
        {
            Intent c_guide = new Intent(this,CampusGuide.class);
            startActivity(c_guide);
        }

        if(lec_rate == v)
        {

           Intent lec_rate = new Intent(this,LectureRating.class);
            startActivity(lec_rate);
        }

        if(enquires == v)
        {
            Toast.makeText(this,"This feature is unavailable", Toast.LENGTH_SHORT).show();
        }

        if(online_library == v)
        {
            Toast.makeText(this,"This feature is unavailable", Toast.LENGTH_SHORT).show();
        }


    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
                studentName = (TextView) findViewById(R.id.student_name_nav);
        programeName = (TextView) findViewById(R.id.student_programme_nav);

        DBHelper dbHelper_prog = OpenHelperManager.getHelper(this, DBHelper.class);
        localProgrammesDao = dbHelper_prog.getProgrammeExceptionDao();
        List<LocalProgramme> programmes = localProgrammesDao.queryForAll();
        Typeface name_tf = Typeface.createFromAsset(getAssets(), "Simple tfb.ttf");
        programme = programmes.get(0).getProgDesc();

        studentName.setText(name + " " + surname );
        programeName.setText(programme);
        studentName.setTypeface(name_tf);
        programeName.setTypeface(name_tf);

        //commented out to remove settings icon
        //getMenuInflater().inflate(R.menu.nav, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Preferences/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        FragmentManager fragmentManager = getFragmentManager();
        int id = item.getItemId();

        if (id == R.id.nav_first_layout) {
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame, new FirstFragment())
                    .commit();


            // Handle the camera action
        } else if (id == R.id.nav_second_layout) {
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame, new SecondFragment())
                    .commit();


    } else if (id == R.id.nav_about) {
        fragmentManager.beginTransaction()
                .replace(R.id.content_frame, new About())
                .commit();

    }
        else if (id == R.id.nav_home) {

            Intent i = new Intent(this, MainActivity.class);
            finish();
            startActivity(i);

        }



    DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
