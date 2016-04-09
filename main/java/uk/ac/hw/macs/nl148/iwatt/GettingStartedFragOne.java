package uk.ac.hw.macs.nl148.iwatt;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Author: Neio Lucas
 * File : GettingStartedFragOne.java
 * Platform : Android Operating System
 * Date:  01/02/2016..
 * Description: This fragament displays a welcome message when the application open for the first time
 */

public class GettingStartedFragOne extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_getting_started_one,container,false);

    }

}
