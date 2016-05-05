package uk.ac.hw.macs.nl148.iwatt;

import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.Bundle;
import android.provider.SyncStateContract;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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

public class GettingStartedFragTwo extends Fragment implements View.OnClickListener{

    private  DBHelper dbHelper;
    TextView name_tx;
    TextView surname_tx;
    TextView username_tx;
    TextView email_tx;
    TextView tx_next;
    int count = 0;

    Button next;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_getting_started_two, container, false);

        name_tx = (TextView) view.findViewById(R.id.name);
        surname_tx = (TextView) view.findViewById(R.id.surname);
        username_tx = (TextView) view.findViewById(R.id.username);
        email_tx = (TextView) view.findViewById(R.id.email);
        next = (Button) view.findViewById(R.id.next_two);
        tx_next = (TextView) view.findViewById(R.id.tx_two);

        Typeface tf = Typeface.createFromAsset(getActivity().getAssets(),"Simple tfb.ttf");
        next.setOnClickListener(this);


        name_tx.setTypeface(tf);
        surname_tx.setTypeface(tf);
        username_tx.setTypeface(tf);
        email_tx.setTypeface(tf);
        tx_next.setTypeface(tf);


        return view;

    }

    private boolean processStudentData()
    {


        if((name_tx.getText().length() <= 0) || (surname_tx.getText().length() <= 0) || (username_tx.getText().length() <= 0) || (email_tx.getText().length() <= 0))
        {
            System.out.println("Please fill in all fields");
            Toast.makeText(getActivity().getBaseContext(),"Please fill in all fields",Toast.LENGTH_SHORT).show();

            return false;
        }
        else
        {

            //check correct email format
            if(!email_tx.getText().toString().contains("@") || !email_tx.getText().toString().contains(".") ) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                TextView tx = new TextView(getActivity());
                builder.setTitle("Report");
                builder.setMessage("\n" +
                        "\nEmail address is invalid.\n");
                builder.setView(tx);
                builder.setPositiveButton("OKAY", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                        AlertDialog build = builder.create();
                        build.cancel();

                    }

                });

                AlertDialog build = builder.create();
                build.setCanceledOnTouchOutside(false);
                build.show();
                
                return false;
            }
            else {


                dbHelper = OpenHelperManager.getHelper(getActivity(), DBHelper.class);

                RuntimeExceptionDao<Student, Object> studentDao = dbHelper.getStudentExceptionDao();
                String name = name_tx.getText().toString();
                String surname = surname_tx.getText().toString();
                String username = username_tx.getText().toString();
                String email = email_tx.getText().toString();
                //studentDao.create(new Student(name.substring(name.indexOf(":")+1),surname.substring(name.indexOf(":")+4) , username.substring(name.indexOf(":")+5)));
                studentDao.createIfNotExists(new Student(name, surname, username, email));
                List<Student> student = studentDao.queryForAll();
                //studentDao.delete(student);

                Log.d("demo", student.toString());

                System.out.println(name_tx.getText().length());
                OpenHelperManager.releaseHelper();

                return true;
            }
        }

    }

    @Override
    public void onClick(View v) {

                if(v == next)
                {

                    if(processStudentData())
                    {
                        ((GettingStarted)getActivity()).setCurrentItem (2, true);
                    }

                }

    }
}