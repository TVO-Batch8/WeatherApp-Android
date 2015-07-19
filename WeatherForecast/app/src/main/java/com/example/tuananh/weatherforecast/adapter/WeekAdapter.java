package com.example.tuananh.weatherforecast.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tuananh.weatherforecast.R;
import com.example.tuananh.weatherforecast.dummy.CurrentWeather;
import com.example.tuananh.weatherforecast.dummy.WeekWeather;
import com.example.tuananh.weatherforecast.other.SettingShare;
import com.example.tuananh.weatherforecast.other.getImageView;

import java.util.ArrayList;

/**
 * Created by TuanAnh on 08/07/2015.
 */
public class WeekAdapter extends ArrayAdapter<WeekWeather> {
    private Context context = null;
    private int layoutInfla;
    private ArrayList<WeekWeather> arrayWeek;

    public WeekAdapter(Context context, int resource, ArrayList<WeekWeather> objects) {
        super(context, resource, objects);
        this.context = context;
        this.layoutInfla = resource;
        this.arrayWeek = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rootView = convertView;
        viewHolder holder;
        if (rootView == null) {
            LayoutInflater inflater = (LayoutInflater)
                    context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rootView = inflater.inflate(layoutInfla, null);
            holder = new viewHolder();
            holder.img_icon = (ImageView) rootView.findViewById(R.id.img_item_icon);
            holder.tv_date = (TextView) rootView.findViewById(R.id.tv_item_date);
            holder.tv_state = (TextView) rootView.findViewById(R.id.tv_item_state);
            holder.tv_temperature = (TextView) rootView.findViewById(R.id.tv_item_temperature);
            rootView.setTag(holder);
        } else {
            holder = (viewHolder) rootView.getTag();
        }
        if (arrayWeek.size() > 0 && position > -1) {
            String temperature = new SettingShare(context).getShare(
                    SettingShare.TEMPERATURE, ""
            );
            holder.tv_date.setText(arrayWeek.get(position).getDate());

            if (temperature.equals("") || temperature.equals("c")) {
                holder.tv_temperature.setText(arrayWeek.get(position).getCelsius());
            } else holder.tv_temperature.setText(arrayWeek.get(position).getFahrenheit());

            holder.tv_state.setText(arrayWeek.get(position).getConditions());
            getImageView.imgv(arrayWeek.get(position).getIcon(), holder.img_icon);
        }
        return rootView;
    }

    private class viewHolder {
        TextView tv_date, tv_state, tv_temperature;
        ImageView img_icon;
    }
}
