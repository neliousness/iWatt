package uk.ac.hw.macs.nl148.iwatt;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.RuntimeExceptionDao;

import java.util.List;

/**
 * Created by mrnel on 01/02/2016.
 */
public class GettingStartedFragFour extends Fragment {

    private Button finishGettingStarted;
    DBHelper dbHelper;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_getting_started_four, container, false);
        finishGettingStarted = (Button) view.findViewById(R.id.finish_getting_started);
        Typeface finish_tf = Typeface.createFromAsset(getActivity().getAssets(),"Simple tfb.ttf");
        finishGettingStarted.setTypeface(finish_tf);

        Firebase.setAndroidContext(getActivity());


        finishGettingStarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                dbHelper = OpenHelperManager.getHelper(getActivity(), DBHelper.class);


                RuntimeExceptionDao<Student, Object> studentDao = dbHelper.getStudentExceptionDao();

                String name = "test";


                List<Student> students = studentDao.queryForAll();


                OpenHelperManager.releaseHelper();

                if (students.isEmpty()) {
                    Toast.makeText(getActivity(), "Please make sure that your name , surname and username is filled in.", Toast.LENGTH_SHORT).show();

                } else if (!students.isEmpty()) {


                    Firebase cor = new Firebase("https://testering.firebaseio.com/");
                    cor.addValueEventListener(new ValueEventListener() {

                        DBHelper dbHelper2 = OpenHelperManager.getHelper(getActivity().getApplicationContext(), DBHelper.class);
                        RuntimeExceptionDao<LocalProgramme, Object> programmeDao = dbHelper2.getProgrammeExceptionDao();
                        RuntimeExceptionDao<LocalCourse, Object> courseDao = dbHelper2.getCourseExceptionDao();

                        List<LocalProgramme> locall = programmeDao.queryForAll();

                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            List<LocalCourse> log_delete = courseDao.queryForAll();
                           // courseDao.delete(log_delete);
                            for (DataSnapshot progshot : dataSnapshot.getChildren()) {
                                Course c = progshot.getValue(Course.class);

                                Log.d("mandatory courses in getting started", locall.get(0).getYear() + "");
                                //check if courses correspond to programme year
                                if (locall.get(0).getYear() == c.getYear()) {

                                    //if not mandatory , course is added automatically as a subject
                                    if (c.getMandatory().equals("yes")) {


                                        courseDao.createIfNotExists(new LocalCourse(c.getCode(), c.getYear(), c.getCoursename(), c.getCoordinator(), c.getMandatory()));



                                    }
                                }

                            }
                            List<LocalCourse> log_course = courseDao.queryForAll();
                            Log.d("mandatory courses in getting started", log_course.toString());


                        }

                        @Override
                        public void onCancelled(FirebaseError firebaseError) {

                        }

                    });

                    Intent i = new Intent(getActivity(), MainActivity.class);
                    startActivity(i);
                    getActivity().finish();
                }

            }
        });

        return view;
    }







}



