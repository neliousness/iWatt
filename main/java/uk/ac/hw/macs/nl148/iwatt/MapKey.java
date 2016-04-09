package uk.ac.hw.macs.nl148.iwatt;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;



/**
 * Author: Neio Lucas
 * File : MapKey.java
 * Platform : Android Operating System
 * Date:  25/02/2016.
 * Description: This fragment displays the map key to the KnowGo feature.
 */

public class MapKey extends Fragment {

    View view;
    Button back;





    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.map_key, container,false);

        back = (Button) view.findViewById(R.id.back_to_map);
        Typeface tf = Typeface.createFromAsset(getActivity().getAssets(), "Simple tfb.ttf");
        back.setTypeface(tf);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), KnowGo.class);
                getActivity().finish();
                startActivity(i);
            }
        });

        return view;
    }




}
