package uk.ac.hw.macs.nl148.iwatt;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;


/**
 * Author: Neio Lucas
 * File : SwipeAdaptor.java
 * Platform : Android Operating System
 * Date:  01/02/2016.
 * Description: This class enables the swipe movement through the GettingStarted fragments
 */

public class SwipeAdaptor extends FragmentStatePagerAdapter {

    public SwipeAdaptor(FragmentManager fm) {

        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        switch (position)
        {
            case 0 :
                return new GettingStartedFragOne();

            case  1 :
                return  new GettingStartedFragTwo();

            case 2 :
                return  new GettingStartedFragThree();
            case 3:
                return  new GettingStartedFragFour();

            default:
                break;
        }

        return null;

    }

    @Override
    public int getCount() {
        return 4;
    }
}
