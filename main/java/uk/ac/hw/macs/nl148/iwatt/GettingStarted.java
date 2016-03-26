package uk.ac.hw.macs.nl148.iwatt;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;

public class GettingStarted extends FragmentActivity {

    ViewPager viewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_getting_started);


        viewPager = (ViewPager) findViewById(R.id.pager);
        SwipeAdaptor swipeAdaptor = new SwipeAdaptor(getSupportFragmentManager());
        viewPager.setAdapter(swipeAdaptor);

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


}
