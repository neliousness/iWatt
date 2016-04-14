package uk.ac.hw.macs.nl148.iwatt;

import android.app.FragmentManager;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.RuntimeExceptionDao;

import java.util.List;

/**
 * Author: Neio Lucas
 * File : CampusGuide.java
 * Platform : Android Operating System
 * Date: 02/04/2016
 * Description: This activity displays buttons that enables the user to select other options
 */

public class CampusGuide extends AppCompatActivity implements View.OnClickListener{


    Button wifi;
    Button study_spaces;
    Button resources;
    Button workshops;
    Button study_support;
    Button back;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.d);




        wifi = (Button) findViewById(R.id.wifi);
        study_spaces = (Button) findViewById(R.id.study_spaces);
        resources = (Button) findViewById(R.id.resources);
        workshops = (Button) findViewById(R.id.workshops);
        study_support = (Button) findViewById(R.id.study_support);
        back = (Button) findViewById(R.id.rate_back);


        wifi.setOnClickListener(this);
        study_spaces.setOnClickListener(this);
        resources.setOnClickListener(this);
        workshops.setOnClickListener(this);
        study_support.setOnClickListener(this);
        back.setOnClickListener(this);

        Typeface tf = Typeface.createFromAsset(getAssets(), "Simple tfb.ttf");

        workshops .setTypeface(tf);
        resources .setTypeface(tf);
        study_spaces .setTypeface(tf);
        wifi .setTypeface(tf);
        back.setTypeface(tf);
        study_support .setTypeface(tf);

        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar_campus);
        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CampusGuide.this.finish();

            }
        });
        TextView toolbar_title = (TextView) toolbar.findViewById(R.id.toolbar_title5);
        toolbar.setTitle("");
        toolbar_title.setTypeface(tf);


        setSupportActionBar(toolbar);


    }

    @Override
    public void onClick(View v) {

        if(wifi == v)
        {
            Intent i = new Intent(this , Wifi.class);
            startActivity(i);
        }

        if(resources== v)
        {
            Intent i = new Intent(this , Resources.class);
            startActivity(i);
        }

        if(study_spaces == v)
        {
            Intent i = new Intent(this ,StudySpaces.class);
            startActivity(i);
        }
        if(study_support == v)
        {
            Intent i = new Intent(this , StudySupport.class);
            startActivity(i);
        }

        if(workshops == v)
        {
            Intent i = new Intent(this , WorkShops.class);
            startActivity(i);
        }

        if(back == v)
        {
            finish();
        }


    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }



}
