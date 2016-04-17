package uk.ac.hw.macs.nl148.iwatt;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Author: Neio Lucas
 * File : About.java
 * Platform : Android Operating System
 * Date: 04/04/2016
 * Description: This fragment displays the About option seen in the navigation menu
 */
public class About extends Fragment {

    View view;

    TextView version;
    TextView iWatt;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.about, container,false);
        Typeface tf = Typeface.createFromAsset(getActivity().getAssets(), "Simple tfb.ttf");
        version = (TextView) view.findViewById(R.id.version_about);
        iWatt = (TextView) view.findViewById(R.id.iWatt_about);

        version.setTypeface(tf);
        iWatt.setTypeface(tf);

        version.setText("Version 1.3.5");

        return view;
    }




}
