package android.mayur.com.maddysexpensetracker;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by cg-mayur on 23/4/17.
 */

public class TypeTabAdapter extends FragmentStatePagerAdapter {
    private static final String TAG = TypeTabAdapter.class.getSimpleName();
    private static final String[] titles = {"Daily", "Weekly", "Monthly"};
    public static final int DAILY = 0;
    public static final int WEEKLY = 1;
    public static final int MONTHLY = 2;

    public TypeTabAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }


    @Override
    public Fragment getItem(int position) {
        StatsFragment fragment = new StatsFragment();
        Bundle args = new Bundle();
        switch (position) {
            case DAILY:
                args.putInt("SELECTION", DAILY);
                break;
            case WEEKLY:
                args.putInt("SELECTION", WEEKLY);
                break;
            case MONTHLY:
                args.putInt("SELECTION", MONTHLY);
                break;
        }
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getCount() {
        return titles.length;
    }
}
