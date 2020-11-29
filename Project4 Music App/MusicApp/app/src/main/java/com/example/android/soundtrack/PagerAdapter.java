package com.example.android.soundtrack;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

class PagerAdapter extends FragmentPagerAdapter {

    PagerAdapter(FragmentManager fm, Context context) {
        super(fm);
    }

    // Assign the number of fragments
    @Override
    public int getCount() {
        return 2;
    }

    // Get necessary fragment accorfing to its position
    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new MelodyFragment();
        } else {
            return new FavoriteFragment();
        }
    }
}
