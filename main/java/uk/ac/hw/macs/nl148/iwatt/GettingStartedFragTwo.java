package uk.ac.hw.macs.nl148.iwatt;

import android.graphics.Typeface;
import android.os.Bundle;
import android.provider.SyncStateContract;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.RuntimeExceptionDao;

import java.util.List;

/**
 * Author: Neio Lucas
 * File : GettingStartedFragTwo.java
 * Platform : Android Operating System
 * Date:  01/02/2016..
 * Description: This fragments prompta the user to enter his/her name ,surname and user name when
 * they first register with the application
 */

public class GettingStartedFragTwo extends Fragment {

    private  DBHelper dbHelper;
    TextView name_tx;
    TextView surname_tx;
    TextView username_tx;
    int count = 0;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_getting_started_two, container, false);

        name_tx = (TextView) view.findViewById(R.id.name);
        surname_tx = (TextView) view.findViewById(R.id.surname);
        username_tx = (TextView) view.findViewById(R.id.username);

        Typeface name_tf = Typeface.createFromAsset(getActivity().getAssets(),"Simple tfb.ttf");
        Typeface surname_tf = Typeface.createFromAsset(getActivity().getAssets(),"Simple tfb.ttf");
        Typeface username_tf = Typeface.createFromAsset(getActivity().getAssets(),"Simple tfb.ttf");

        name_tx.setTypeface(name_tf);
        surname_tx.setTypeface(surname_tf);
        username_tx.setTypeface(username_tf);

        final GestureDetector gesture = new GestureDetector(getActivity(),
                new GestureDetector.SimpleOnGestureListener() {

                    @Override
                    public boolean onDown(MotionEvent e) {
                        return true;
                    }

                    @Override
                    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
                                           float velocityY) {
                        Log.i(SyncStateContract.Constants.ACCOUNT_NAME, "onFling has been called!");
                        final int SWIPE_MIN_DISTANCE = 200;
                        final int SWIPE_MAX_OFF_PATH = 250;
                        final int SWIPE_THRESHOLD_VELOCITY = 210;
                        try {
                            if (Math.abs(e1.getY() - e2.getY()) > SWIPE_MAX_OFF_PATH)
                                return false;
                            if (e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE
                                    && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {

                                Log.i(SyncStateContract.Constants.ACCOUNT_NAME, "Right to Left");

                            } else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE
                                    && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                                Log.i(SyncStateContract.Constants.ACCOUNT_NAME, "Left to Right");
                            }
                        } catch (Exception e) {
                            // nothing
                        }
                        return super.onFling(e1, e2, velocityX, velocityY);
                    }
                });


        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                processStudentData();
                count++;
                return gesture.onTouchEvent(event);
            }
        });


        return view;

    }

    private void processStudentData()
    {


        if((name_tx.getText().length() <= 0) || (surname_tx.getText().length() <= 0) || (username_tx.getText().length() <= 0))
        {
            System.out.println("Please fill in all fields");
        }
        else
        {
            dbHelper = OpenHelperManager.getHelper(getActivity(), DBHelper.class);

            RuntimeExceptionDao<Student,Object> studentDao = dbHelper.getStudentExceptionDao();
            String name = name_tx.getText().toString();
            String surname = surname_tx.getText().toString();
            String username = username_tx.getText().toString();
            //studentDao.create(new Student(name.substring(name.indexOf(":")+1),surname.substring(name.indexOf(":")+4) , username.substring(name.indexOf(":")+5)));
            studentDao.createIfNotExists(new Student(name, surname, username));
            List<Student> student = studentDao.queryForAll();
            //studentDao.delete(student);

            Log.d("demo", student.toString());

            System.out.println(name_tx.getText().length());
            OpenHelperManager.releaseHelper();
        }

    }

}