package uk.ac.hw.macs.nl148.iwatt;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * Author: Neio Lucas
 * File : GettingStartedFragOne.java
 * Platform : Android Operating System
 * Date:  01/02/2016..
 * Description: This fragament displays a welcome message when the application open for the first time
 */

public class GettingStartedFragOne extends Fragment  implements  View.OnClickListener{

    Button begin;
    TextView tx_begin;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_getting_started_one,container,false);
        Typeface tf = Typeface.createFromAsset(getActivity().getAssets(),"Simple tfb.ttf");


        begin = (Button) view.findViewById(R.id.button_one);
        tx_begin = (TextView) view.findViewById(R.id.tx_one);

        begin.setOnClickListener(this);
        tx_begin.setOnClickListener(this);
        tx_begin.setTypeface(tf);

        return view;

    }

    @Override
    public void onClick(View v) {

        if (v == begin || v == tx_begin) {
           // getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.f_one, new GettingStartedFragTwo()).commit();
            ((GettingStarted)getActivity()).setCurrentItem (1, true);
        }
    }
}
