package com.example.tuananh.weatherforecast.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import com.example.tuananh.weatherforecast.fragments.CurrentWeatherFragment;
import com.example.tuananh.weatherforecast.fragments.WeekWeatherFragment;

public class TabAdapter extends FragmentPagerAdapter {
    int count = 2;
    String[] title;
    public TabAdapter(FragmentManager fm, String[] title) {
        super(fm);
        this.title = title;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new CurrentWeatherFragment();
            case 1:
                return new WeekWeatherFragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        return count;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return title[position];
    }
}
