package uk.ac.hw.macs.nl148.iwatt;


import android.app.Activity;
import android.app.Dialog;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.shaded.fasterxml.jackson.databind.deser.impl.CreatorCollector;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

public class TimeTable extends AppCompatActivity implements View.OnClickListener ,  AdapterView.OnItemClickListener{

    TextView location;
    final Context context = this;
    ArrayList<TimeTableData> data = new ArrayList<TimeTableData>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.c);
        Firebase.setAndroidContext(this);
        Firebase timetable = new Firebase("https://timetablehw.firebaseio.com/");
        final ListView listView = (ListView) findViewById(R.id.timetable_list);
        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar_timetable);
        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TimeTable.this.finish();

            }
        });

        TextView toolbar_title = (TextView) toolbar.findViewById(R.id.toolbar_title4);
        Typeface tf = Typeface.createFromAsset(getAssets(), "Simple tfb.ttf");
        toolbar.setTitle("");
        toolbar_title.setTypeface(tf);

        setSupportActionBar(toolbar);



        timetable.addValueEventListener(new ValueEventListener() {

            AlertDialog.Builder builder;

            TimeTableAdapter timeTableAdapter;
            ProgressDialog progressBar;

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                String dow = getIntent().getStringExtra("dow");


                DBHelper dbHelper = OpenHelperManager.getHelper(getApplicationContext(), DBHelper.class);
                RuntimeExceptionDao<LocalCourse, Object> courseDao = dbHelper.getCourseExceptionDao();
                List<LocalCourse> local = courseDao.queryForAll();
                Log.d("size in timetable" , local.size()+"" );
                //studentDao.delete(student);


                for (DataSnapshot progshot : dataSnapshot.getChildren()) {
                    TimeTableData timetable = progshot.getValue(TimeTableData.class);

                    System.out.print("THIS IS AN ENTRY" + timetable.toString());
                    //filtering : by day of the week
                    if (timetable.getDow().contentEquals(dow)) {

                        for (LocalCourse z : local) {
                            //filtering : by course code
                            if (timetable.getCode().equals(z.getCode())) {
                                data.add(timetable);
                                Log.d("size in timetable", data.size() + "");
                            }

                        }

                    }

                    // OpenHelperManager.releaseHelper();
                }

                if (data.size() == 0) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(TimeTable.this);
                    TextView tx = new TextView(TimeTable.this);
                    Button info = new Button(TimeTable.this);
                    builder.setTitle("TimeTable");
                    builder.setMessage("\n" +
                            "\nYou have no lectures/labs/tutorials on this day.\n" +
                            "\n");
                    builder.setView(tx);
                    builder.setPositiveButton("OKAY", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {


                            Intent i = new Intent(getBaseContext(), TimeTableInit.class);
                            finish();
                            startActivity(i);

                        }
                    });

                    AlertDialog build = builder.create();
                    build.setCanceledOnTouchOutside(false);
                    build.show();
                }
                timeTableAdapter = new TimeTableAdapter(getApplicationContext(), data);

                ProcessCourses p = new ProcessCourses();
                p.execute();


                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        showOptions(parent, position);
                    }
                });

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }

            //pop up menu
            public void showOptions(final AdapterView<?> listv, final int position) {
                final TimeTableData obj = (TimeTableData) listv.getAdapter().getItem(position);
                builder = new AlertDialog.Builder(TimeTable.this);
                TextView tx = new TextView(TimeTable.this);
                Button directions = new Button(TimeTable.this);
                Button info = new Button(TimeTable.this);
                builder.setTitle(obj.getTitle());
                String rm2 = "/" + obj.getRoom2();
                if (rm2.equals("/N/A")) {
                    rm2 = "";
                }

                builder.setMessage("Course co-ordinator :" + obj.getStaff() + " \n\nLocation : " + obj.getRoom1() + rm2 + "\n\n\n          What would you like to do?");
                builder.setView(tx);
                builder.setPositiveButton("Get Directions", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {


                        String room1 = obj.getRoom1();
                        String room2 = obj.getRoom2();

                        Intent classMap = new Intent(getBaseContext(), ClassMap.class);
                        classMap.putExtra("room1", room1);
                        classMap.putExtra("room2", room2);
                        startActivity(classMap);

                    }
                });
                builder.setNegativeButton("View Course Details", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        //TimeTableData obj = (TimeTableData) listv.getAdapter().getItem(position);
                        String title = obj.getTitle();
                        String code = obj.getCode();

                        Intent course = new Intent(getBaseContext(), TimeTableCourse.class);
                        course.putExtra("title", title);
                        course.putExtra("code", code);
                        startActivity(course);

                    }
                });


                AlertDialog build = builder.create();
                build.show();
            }

            private void showProgressDialog() {
                if (progressBar == null) {
                    progressBar = new ProgressDialog(TimeTable.this);
                    progressBar.setIndeterminate(false);
                    progressBar.setCancelable(false);
                    progressBar.setTitle("TimeTable");
                    progressBar.setMax(data.size());
                    progressBar.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                    progressBar.setMessage("Loading timetable..");
                }

                if(!((TimeTable) context).isFinishing())
                {
                    progressBar.show();
                }

            }

            class ProcessCourses extends AsyncTask<String, String, String> {

                /**
                 * Before starting background thread Show Progress Dialog
                 * */
                @Override
                protected void onPreExecute() {
                    showProgressDialog();
                }

                /**
                 * getting All products from url
                 * */
                protected String doInBackground(String... args) {

                    for (int x = 0; x <= data.size(); x++) {
                        try {
                            Thread.sleep(100);
                            progressBar.setProgress(x);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                    }

                    return "done";
                }

                /**
                 * After completing background task Dismiss the progress dialog
                 * **/
                protected void onPostExecute(String final_result) {

                    if (final_result.equals("done")) {

                        listView.setAdapter(timeTableAdapter);
                        dismissProgressDialog();
                    }


                }
            }

            private void dismissProgressDialog() {
                if (progressBar != null && progressBar.isShowing()) {
                    progressBar.dismiss();
                }
            }

        });

        timetable.onDisconnect();




    }

    @Override
    public void onClick(View v) {
        if(v == location)
        {
            Toast.makeText(this, "omg it works!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}