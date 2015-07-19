package com.example.tuananh.weatherforecast.fragments;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tuananh.weatherforecast.R;
import com.example.tuananh.weatherforecast.other.SettingShare;
import com.example.tuananh.weatherforecast.other.getImageView;
import com.example.tuananh.weatherforecast.sqlite.MyDatabase;

/**
 * A simple {@link Fragment} subclass.
 */
public class CurrentWeatherFragment extends Fragment {

    TextView tv_date, tv_country, tv_temperature, tv_pressure, tv_wind, tv_hummidity, tv_weather;
    ImageView imgv_icon;
    String mC = "", mF = "";
    public SQLiteDatabase mDb;

    public CurrentWeatherFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootview = inflater.inflate(R.layout.fragment_current_weather, container, false);

        tv_date = (TextView) rootview.findViewById(R.id.tv_date);
        tv_country = (TextView) rootview.findViewById(R.id.tv_country);
        tv_temperature = (TextView) rootview.findViewById(R.id.tv_temperature);
        tv_pressure = (TextView) rootview.findViewById(R.id.tv_pressure);
        tv_wind = (TextView) rootview.findViewById(R.id.tv_wind);
        tv_hummidity = (TextView) rootview.findViewById(R.id.tv_hummidity);
        tv_weather = (TextView) rootview.findViewById(R.id.tv_weather);


        imgv_icon = (ImageView) rootview.findViewById(R.id.imgv_icon);

        mDb = new MyDatabase(getActivity()).getWritableDatabase();
        String query = "SELECT * FROM " + MyDatabase.CURRENTTABLE;
        Cursor cu = mDb.rawQuery(query, null);
        String temperature = new SettingShare(getActivity()).getShare(
                SettingShare.TEMPERATURE, ""
        );
        if (cu.moveToFirst()) {
            mC = cu.getInt(cu.getColumnIndex(MyDatabase.TEAMP_C)) + "°C";
            mF = cu.getInt(cu.getColumnIndex(MyDatabase.TEAMP_F)) + "F";
            if (temperature.equals("") || temperature.equals("c")) {
                tv_temperature.setText(mC);
            } else {
                tv_temperature.setText(mF);
            }
            tv_date.setText(cu.getString(cu.getColumnIndex(MyDatabase.HOUR))+", "
                    +cu.getString(cu.getColumnIndex(MyDatabase.DATE))+", "
                    +cu.getLong(cu.getColumnIndex(MyDatabase.DAY))+" "
                    +cu.getString(cu.getColumnIndex(MyDatabase.MONTH))+" "
                    +cu.getLong(cu.getColumnIndex(MyDatabase.YEAR))
            );
            tv_country.setText(cu.getString(cu.getColumnIndex(MyDatabase.COUNTRY)));
            tv_pressure.setText((getResources().getString(R.string.presure)) + " "
                    + cu.getString(cu.getColumnIndex(MyDatabase.PRESSURE))+ "mb");
            tv_wind.setText((getResources().getString(R.string.wind)) + " "
                    + cu.getInt(cu.getColumnIndex(MyDatabase.WIND)) + "mdp");
            tv_hummidity.setText((getResources().getString(R.string.hummid)) + " "
                    + cu.getString(cu.getColumnIndex(MyDatabase.HUMMID)));
            tv_weather.setText(cu.getString(cu.getColumnIndex(MyDatabase.STATE)));
            String icon = cu.getString(cu.getColumnIndex(MyDatabase.ICON));
            getImageView.imgv(icon, imgv_icon);
        }

        return rootview;

    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onStop() {
        super.onStop();
    }
}
