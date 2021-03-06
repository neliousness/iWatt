package uk.ac.hw.macs.nl148.iwatt;

import android.content.DialogInterface;
import android.content.Intent;
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
import com.firebase.client.snapshot.ValueIndex;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.RuntimeExceptionDao;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Author: Neio Lucas
 * File : GettingStartedFragThree.java
 * Platform : Android Operating System
 * Date:  01/02/2016..
 * Description: This fragment prompts the user to select a programme and a year of study for the selected
 * programme
 */
public class GettingStartedFragThree extends Fragment implements View.OnClickListener {

    AutoCompleteTextView myAutoComplete;
    NumberPicker year;
    DBHelper dbHelper;
    Button next;
    TextView tx_next;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_getting_started_three, container, false);
        myAutoComplete = (AutoCompleteTextView) view.findViewById(R.id.auto);
        year = ( NumberPicker) view.findViewById(R.id.length);
        next = (Button) view.findViewById(R.id.next_three);
        tx_next = (TextView) view.findViewById(R.id.tx_three);

        year.setMinValue(1);
        year.setMaxValue(5);
        ArrayList<String> list = getActivity().getIntent().getStringArrayListExtra("programme");

        ArrayAdapter arrayAdapter = new ArrayAdapter(getActivity().getApplicationContext(), android.R.layout.select_dialog_item, list);
        TextView tx = (TextView) view.findViewById(R.id.year_tv);
        Typeface tf = Typeface.createFromAsset(getActivity().getAssets(), "Simple tfb.ttf");
        tx.setTypeface(tf);


        myAutoComplete.setDropDownBackgroundResource(R.drawable.backg);
        myAutoComplete.setThreshold(1);
        myAutoComplete.setTypeface(tf);
        myAutoComplete.setAdapter(arrayAdapter);
        next.setOnClickListener(this);
        tx_next.setTypeface(tf);


        return view;


    }


    private void processProgrammeData()
    {

        Firebase.setAndroidContext(getActivity());

        Firebase programme = new Firebase("https://glowing-heat-7374.firebaseio.com/");

        programme.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if(myAutoComplete.getText().toString().isEmpty())
                {
                    Toast.makeText(getActivity(),"Please fill in  programme field" , Toast.LENGTH_SHORT).show();
                }
                else {

                    Pattern p = Pattern.compile(".*[a-zA-Z].*");
                    Matcher m = p.matcher(year.getValue()+"");

                    for (DataSnapshot progshot : dataSnapshot.getChildren()) {
                        OnlineProgramme prog = progshot.getValue(OnlineProgramme.class);

                        if (m.find()) {
                            Toast.makeText(getActivity(), "This field cannot contain letters", Toast.LENGTH_SHORT).show();
                        } else if (prog.getProgDesc().contentEquals(myAutoComplete.getText())) {

                            // Toast.makeText(getActivity(),"yay!" , Toast.LENGTH_SHORT).show();

                            dbHelper = OpenHelperManager.getHelper(getActivity(), DBHelper.class);
                            String proglength = prog.getLength();
                            int length = Integer.parseInt(proglength);
                            int study_year = Integer.parseInt(year.getValue()+"");
                            RuntimeExceptionDao<LocalProgramme,Object> programmeDao = dbHelper.getProgrammeExceptionDao();

                            if (study_year > length) {
                                Toast.makeText(getActivity(), "Year entered exceeds course length. please enter a year smaller than " + proglength, Toast.LENGTH_LONG).show();

                            } else {

                                if(prog.getProgDesc().contentEquals("BSc Computer Systems") || prog.getProgDesc().contentEquals("BSc Computer Science") ||
                                        prog.getProgDesc().contentEquals("BSc Information Systems") || prog.getProgDesc().contentEquals("MEng Software Engineering") ) {
                                    
                                    programmeDao.createIfNotExists(new LocalProgramme(prog.getProgCode(), myAutoComplete.getText().toString(), length, study_year));
                                    List<LocalProgramme> programmes = programmeDao.queryForAll();
                                    //Log.d("this programe", programmes.toString());
                                    //System.out.println("This other programme" + programmes.toString());
                                    //programmeDao.delete(programmes);
                                    OpenHelperManager.releaseHelper();

                                    GettingStarted g = (GettingStarted)getActivity();
                                    g.setCurrentItem (3, true);
                                    //System.out.println(prog.getProgDesc() + " +++ " + myAutoComplete.getText());
                                }
                                else
                                {

                                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                                    TextView tx = new TextView(getActivity());
                                    Button info = new Button(getActivity());
                                    builder.setTitle("Error");
                                    builder.setMessage("\n" +
                                            "\nInformation on the programme you selected is currently unavailable.\n" +
                                            "\nPlease select a programme provided by the MACS department. ");
                                    builder.setView(tx);
                                    builder.setPositiveButton("OKAY, GOT IT!", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {



                                            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                                            AlertDialog build = builder.create();
                                            build.cancel();

                                        }
                                    });

                                    AlertDialog build = builder.create();
                                    build.show();
                                }
                            }
                        }


                    }
                }



            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });








    }


    @Override
    public void onClick(View v) {

        if(v == next)
        {
            processProgrammeData();
        }

    }
}



