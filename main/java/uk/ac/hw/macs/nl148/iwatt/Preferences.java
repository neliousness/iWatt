package uk.ac.hw.macs.nl148.iwatt;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.provider.SyncStateContract;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;

/**
 * Created by mrnel on 25/02/2016.
 */
public class Preferences extends Fragment {

    View view;





    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.first_layout, container,false);
        Intent i = new Intent(getActivity(), MainActivity.class);
        startActivity(i);

        return view;
    }




}
