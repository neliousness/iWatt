package uk.ac.hw.macs.nl148.iwatt;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by mrnel on 01/02/2016.
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
