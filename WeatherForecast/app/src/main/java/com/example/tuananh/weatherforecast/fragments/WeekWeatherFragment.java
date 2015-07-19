package com.example.tuananh.weatherforecast.fragments;


import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.tuananh.weatherforecast.R;
import com.example.tuananh.weatherforecast.activitys.MainActivity;
import com.example.tuananh.weatherforecast.adapter.WeekAdapter;
import com.example.tuananh.weatherforecast.dummy.WeekWeather;
import com.example.tuananh.weatherforecast.sqlite.MyDatabase;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class WeekWeatherFragment extends Fragment {
    private ArrayList<WeekWeather> mArrayList;
    private WeekAdapter weekAdapter;
    private ListView lvWeek;
    private SQLiteDatabase mDb;

    public WeekWeatherFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mDb = new MyDatabase(getActivity()).getWritableDatabase();
        View rootView = inflater.inflate(R.layout.fragment_week_weather, container, false);
        lvWeek = (ListView) rootView.findViewById(R.id.lv_week);
        //lvWeek.setScrollBarStyle(R.style.AppBaseTheme);
        mArrayList = getArray();
        // Inflate the layout for this fragment
        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        weekAdapter = new WeekAdapter(getActivity(), R.layout.item_week, mArrayList);
        lvWeek.setAdapter(weekAdapter);
    }

    private ArrayList<WeekWeather> getArray() {
        ArrayList<WeekWeather> mArray = new ArrayList<>();

        Cursor cu = mDb.query(MyDatabase.WEEKTABLE, null, null, null, null, null, null);
        while (cu.moveToNext()){
            WeekWeather mWeekWeather = new WeekWeather();
            mWeekWeather.setCelsius(cu.getString(cu.getColumnIndex(MyDatabase.TEAMP_C)));
            mWeekWeather.setConditions(cu.getString(cu.getColumnIndex(MyDatabase.STATE)));
            mWeekWeather.setDate(cu.getString(cu.getColumnIndex(MyDatabase.DATE)));
            mWeekWeather.setFahrenheit(cu.getString(cu.getColumnIndex(MyDatabase.TEAMP_F)));
            mWeekWeather.setIcon(cu.getString(cu.getColumnIndex(MyDatabase.ICON)));
            mArray.add(mWeekWeather);
        }

        return mArray;
    }


}
