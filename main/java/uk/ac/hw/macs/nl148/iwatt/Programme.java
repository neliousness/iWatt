package uk.ac.hw.macs.nl148.iwatt;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.firebase.client.utilities.Utilities;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.RuntimeExceptionDao;

import java.util.ArrayList;
import java.util.List;

public class Programme extends AppCompatActivity  implements View.OnClickListener {

    TextView coursename;
    TextView coordinator;
    TextView aims;
    TextView syllabus;
    TextView year;
    Button back;
    final ArrayList<Course> c = new ArrayList<>();
   // AutoCompleteTextView autotx = null;








    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.b);
        Firebase.setAndroidContext(this);


       back = (Button) findViewById(R.id.programme_back);
         /* coursename = (TextView) findViewById(R.id.course_name);
        coordinator  = (TextView) findViewById(R.id.coordinator);
        aims  = (TextView) findViewById(R.id.aims_content);
        syllabus  = (TextView) findViewById(R.id.syllabus_content);
        year =  (TextView) findViewById(R.id.stage_year);
        autotx = (AutoCompleteTextView) findViewById(R.id.course_search);*/
        final ListView listView = (ListView) findViewById(R.id.programe_list);

        back.setOnClickListener(this);

        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar_programme);
        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Programme.this.finish();

            }
        });

        TextView toolbar_title = (TextView) toolbar.findViewById(R.id.toolbar_title3);
        Typeface tf = Typeface.createFromAsset(getAssets(), "Simple tfb.ttf");
        toolbar.setTitle("");
        toolbar_title.setTypeface(tf);
        setSupportActionBar(toolbar);

        Firebase course = new Firebase("https://testering.firebaseio.com/");

        course.addValueEventListener(new ValueEventListener() {
            final ArrayList<String> list = new ArrayList<String>();
            ProgressDialog progressBar;

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                DBHelper dbHelper = OpenHelperManager.getHelper(getApplicationContext(), DBHelper.class);
                RuntimeExceptionDao<LocalProgramme, Object> localDao = dbHelper.getProgrammeExceptionDao();
                RuntimeExceptionDao<LocalCourse, Object> courseDao = dbHelper.getCourseExceptionDao();
                List<LocalProgramme> programme = localDao.queryForAll();
                List<LocalCourse> localCourses = courseDao.queryForAll();

                //studentDao.delete(student);
                // OpenHelperManager.releaseHelper();

                for (DataSnapshot progshot : dataSnapshot.getChildren()) {
                    Course course = progshot.getValue(Course.class);

                    //filtering by: year of study
                    if (course.getYear() == programme.get(0).getYear()) {

                        for(LocalCourse l : localCourses) {

                            //filtering by courses within programme
                            if(course.getCode().contentEquals(l.getCode())) {
                                c.add(course);
                                list.add(course.getCoursename());
                                Log.d("show", course.toString());
                            }
                        }

                    }
                }


                ProcessCourses p = new ProcessCourses();
                p.execute();


                OpenHelperManager.releaseHelper();
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }

            private void showProgressDialog() {
                if (progressBar == null) {
                    progressBar = new ProgressDialog(Programme.this);
                    progressBar.setIndeterminate(false);
                    progressBar.setCancelable(false);
                    progressBar.setTitle("Course Programme");
                    progressBar.setMax(list.size());
                    progressBar.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                    progressBar.setMessage("Loading available courses for your programme..");
                }
                progressBar.show();
            }

            class ProcessCourses extends AsyncTask<String, String, String> {

                /**
                 * Before starting background thread Show Progress Dialog
                 */
                @Override
                protected void onPreExecute() {
                    showProgressDialog();
                }

                /**
                 * getting All products from url
                 */
                protected String doInBackground(String... args) {

                    for (int x = 0; x <= list.size(); x++) {
                        try {
                            Thread.sleep(50);
                            progressBar.setProgress(x);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                    }

                    return "done";
                }

                /**
                 * After completing background task Dismiss the progress dialog
                 **/
                protected void onPostExecute(String final_result) {

                    if (final_result.equals("done")) {
                        Typeface tf = Typeface.createFromAsset(getApplication().getAssets(), "Simple tfb.ttf");
                       /* coursename.setTypeface(tf);
                        coursename.setText(c.get(0).getCoursename());
                        coordinator.setText("Co-ordinator :" + c.get(0).getCoordinator());
                        aims.setText(c.get(0).getAims());
                        syllabus.setText(c.get(0).getSyllabus());
                        year.setText("Year :" + c.get(0).getYear() + "");*/
                        ArrayAdapter arrayAdapter = new ArrayAdapter(Programme.this, android.R.layout.select_dialog_item, list);

                        back.setTypeface(tf);
                        listView.setAdapter(arrayAdapter);


                       /* autotx.setDropDownBackgroundResource(R.drawable.backg);
                        autotx.setThreshold(1);
                        autotx.setTypeface(tf);
                        autotx.setAdapter(arrayAdapter);*/
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

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String item = (String)listView.getAdapter().getItem(position);

                Intent i = new Intent(getApplication() , ProgrammeInfo.class);
                i.putExtra("course",item);
                i.putExtra("courses",c);
                startActivity(i);
            }
        });


    }



/*
    private void dismissProgressDialog() {
        if (progressBar != null && progressBar.isShowing()) {
            progressBar.dismiss();
        }
    }*/

    @Override
    protected void onDestroy() {
      //  dismissProgressDialog();
        super.onDestroy();
    }


    @Override
    public void onClick(View v) {
        if(back == v)
        {
            finish();
        }
    }
}
