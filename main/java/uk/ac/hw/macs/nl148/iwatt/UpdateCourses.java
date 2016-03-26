package uk.ac.hw.macs.nl148.iwatt;

import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.ListView;
import android.widget.TextView;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.stmt.DeleteBuilder;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class UpdateCourses extends AppCompatActivity implements View.OnClickListener {

    AlertDialog.Builder builder;

    ListView listView;
    CourseAdapter courseAdapter;
    ArrayList<Course> corlist = new ArrayList<>();
    ArrayList<String> corlist2 = new ArrayList<>();
    Button save;
    Button back;
    DBHelper dbHelper;
    RuntimeExceptionDao<LocalProgramme, Object> localDao;

    List<LocalProgramme> l;

    SharedPreferences sharedpreferences;
    public static final String MyPREFERENCES = "MyUserChoice";
    ArrayList<String> selectedItems = new ArrayList<String>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.f);

        save = (Button) findViewById(R.id.course_save);
        back = (Button) findViewById(R.id.course_back);


        save.setOnClickListener(this);
        back.setOnClickListener(this);
        corlist = (ArrayList<Course>) getIntent().getSerializableExtra("courses");


        // corlist = new ArrayList<>();

        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar_electives);
        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                UpdateCourses.this.finish();
                Intent i = new Intent(UpdateCourses.this, MainActivity.class);
                UpdateCourses.this.startActivity(i);

            }
        });

        TextView toolbar_title = (TextView) toolbar.findViewById(R.id.toolbar_title7);
        Typeface tf = Typeface.createFromAsset(getAssets(), "Simple tfb.ttf");
        toolbar.setTitle("");
        toolbar_title.setTypeface(tf);
        setSupportActionBar(toolbar);



        dbHelper = OpenHelperManager.getHelper(getApplicationContext(), DBHelper.class);
        localDao = dbHelper.getProgrammeExceptionDao();

        //studentDao.delete(student);
        l = localDao.queryForAll();


        for (Course c : corlist) {

            //checks if course year is the same is the programme year

            if (c.getYear() == l.get(0).getYear()) {

                //checks if course is not mandatory
                if (c.getMandatory().equals("no")) {

                    //add none mandatory courses to be displayed in list view
                    corlist2.add(c.getCoursename());

                }


            }

        }

        if (corlist2.size() == 0) {
            AlertDialog.Builder builder = new AlertDialog.Builder(UpdateCourses.this);
            TextView tx = new TextView(UpdateCourses.this);
            Button info = new Button(UpdateCourses.this);
            builder.setTitle("Electives");
            builder.setMessage("\n" +
                    "\nAll of your courses are mandatory for this year.\n" +
                    "\n               No electives to manage");
            builder.setView(tx);
            builder.setPositiveButton("OKAY", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {


                    Intent i = new Intent(getBaseContext(), MainActivity.class);
                    finish();
                    startActivity(i);

                }
            });

            AlertDialog build = builder.create();
            build.show();
        }


        //getListAdapter().get
        //  CheckedTextView item =(CheckedTextView) getListView();
        //CourseAdapter courseAdapter = new CourseAdapter(getApplicationContext(), corlist2);

        //Initializing list view
        //getListView().getAdapter().
        listView = (ListView) findViewById(android.R.id.list);
        listView.setChoiceMode(listView.CHOICE_MODE_MULTIPLE);
        listView.setTextFilterEnabled(true);


        listView.setAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_checked, corlist2));

        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                CheckedTextView item = (CheckedTextView) view;
                if (item.isChecked() == true) {
                    String c = (String) parent.getAdapter().getItem(position);
                    selectedItems.add(c);
                } else if (item.isChecked() == false) {
                    String c = (String) parent.getAdapter().getItem(position);

                    DBHelper dbHelper = OpenHelperManager.getHelper(getApplicationContext(), DBHelper.class);
                    RuntimeExceptionDao<LocalCourse,Object> courseDao = dbHelper.getCourseExceptionDao();
                    DeleteBuilder<LocalCourse , Object> deleteBuilder = courseDao.deleteBuilder();

                    try {
                        deleteBuilder.where().eq("coursename", c);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }

                    try {
                        deleteBuilder.delete();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    selectedItems.remove(c);
                }
                Log.d("here", selectedItems.toString());

            }
        });


        if (sharedpreferences.contains(MyPREFERENCES)) {
            LoadSelections();
            selectedItems.clear();
        }

        //listView.setAdapter(courseAdapter);


    }


    ArrayList<String> corlist3 = new ArrayList<>();

    public void onListItemClick(ListView l, View v, int position, long id) {

        CheckedTextView item = (CheckedTextView) v;

        if (item.isChecked() == true) {
            String c = (String) l.getAdapter().getItem(position);
            selectedItems.add(c);
        } else if (item.isChecked() == false) {
            String c = (String) l.getAdapter().getItem(position);
            selectedItems.remove(c);
        }

        Log.d("here", selectedItems.toString());

    }

    private void SaveSelections() {
// save the selections in the shared preference in private mode for the user

        SharedPreferences.Editor prefEditor = sharedpreferences.edit();
        String savedItems = getSavedItems();
        prefEditor.putString(MyPREFERENCES.toString(), savedItems);

        prefEditor.commit();
    }

    private String getSavedItems() {
        String savedItems = "";
        int count = this.listView.getAdapter().getCount();
        for (int i = 0; i < count; i++) {
            if (this.listView.isItemChecked(i)) {
                if (savedItems.length() > 0) {
                    savedItems += "," + this.listView.getItemAtPosition(i);
                } else {
                    savedItems += this.listView.getItemAtPosition(i);
                }
            }
        }
        return savedItems;
    }

    private void LoadSelections() {
// if the selections were previously saved load them



        if (sharedpreferences.contains(MyPREFERENCES.toString())) {

            String savedItems = sharedpreferences.getString(MyPREFERENCES.toString(), "");
            selectedItems.addAll(Arrays.asList(savedItems.split(",")));

            int count = this.listView.getAdapter().getCount();

            for (int i = 0; i < count; i++) {
                String currentItem = (String) listView.getAdapter()
                        .getItem(i);
                if (selectedItems.contains(currentItem)) {
                    listView.setItemChecked(i, true);

                } else {
                    listView.setItemChecked(i, false);
                }

            }
        }
    }


    @Override
    public void onClick(View v) {

        if(save == v) {

            RuntimeExceptionDao<LocalCourse, Object> courseDao = dbHelper.getCourseExceptionDao();
            List<LocalCourse> log_course = courseDao.queryForAll();
            String selected = "";
            int cntChoice = listView.getCount();

            SparseBooleanArray sparseBooleanArray = listView.getCheckedItemPositions();
            for (int i = 0; i < cntChoice; i++) {
                if (sparseBooleanArray.get(i)) {
                    selected += listView.getItemAtPosition(i).toString() + "\n";
                    System.out.println("Checking list while adding:" + listView.getItemAtPosition(i).toString());
                    SaveSelections();
                }
            }





            for (int x = 0; x < selectedItems.size(); x++) {

                for (Course z : corlist) {


                    if (selectedItems.get(x).contentEquals(z.getCoursename())) {

                        courseDao.createIfNotExists(new LocalCourse(z.getCode(), z.getYear(), z.getCoursename(), z.getCoordinator(), z.getMandatory()));







                    }

                }

            }

            List<LocalCourse> log_c = courseDao.queryForAll();
            // add elements to al, including duplicates
            System.out.println("these are the chosen electives " + log_c.toString());
            Log.d("list", log_c.size() + "");
            //courseDao.delete(log_course);
            Log.d("coursesss", log_course.toString());
            System.out.println("These are the saved options" +getSavedItems());
            OpenHelperManager.releaseHelper();

            Intent ii = new Intent(this, MainActivity.class);
            finish();
            startActivity(ii);

        }

        if (back == v) {

            OpenHelperManager.releaseHelper();
            Intent ii = new Intent(this, MainActivity.class);
            finish();
            startActivity(ii);

        }

    }


}