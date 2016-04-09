package uk.ac.hw.macs.nl148.iwatt;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.stmt.UpdateBuilder;

import java.lang.reflect.Array;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


/**
 * Author: Neio Lucas
 * File : FirstFragment.java
 * Platform : Android Operating System
 * Date:  25/02/2016..
 * Description: This this fragement displays the options that allow the user to manage his/her
 * programme and/or manage his/her electives.
 */

public class FirstFragment extends Fragment implements View.OnClickListener {

    View view;
    Button back;
    EditText year;
    Button update_programme;
    Button update_courses;




    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.first_layout, container,false);

        update_programme = (Button) view.findViewById(R.id.update_programe);
        update_courses = (Button) view.findViewById(R.id.update_course);
        back = (Button) view.findViewById(R.id.prog_settings_back);

        Typeface tf = Typeface.createFromAsset(getActivity().getAssets(), "Simple tfb.ttf");


        update_programme.setTypeface(tf);
        update_courses.setTypeface(tf);
        back.setTypeface(tf);

        back.setOnClickListener(this);
        update_courses.setOnClickListener(this);
        update_programme.setOnClickListener(this);

        return view;
    }



    @Override
    public void onClick(View v) {

        if(update_courses == v)
        {
            MainActivity activity = (MainActivity) getActivity();
            ArrayList<Course> l = activity.getCourses();
            Intent i = new Intent(getActivity(),UpdateCourses.class);
            i.putExtra("courses",l);
            startActivity(i);
        }

        if(update_programme == v)
        {
            MainActivity activity = (MainActivity) getActivity();
            ArrayList<String> l = activity.getProgrames();
            Intent i = new Intent(getActivity(),UpdateProgramme.class);
            i.putStringArrayListExtra("programmes", l);
            startActivity(i);
        }

        if(back == v)
        {
            Intent i = new Intent(getActivity(),MainActivity.class);
            startActivity(i);
        }

    }
}
