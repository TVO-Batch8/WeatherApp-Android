package com.example.tuananh.weatherforecast.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.tuananh.weatherforecast.R;
import com.example.tuananh.weatherforecast.dummy.ValueSpinner;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by TuanAnh on 14/07/2015.
 */
public class AdapterSpinner extends ArrayAdapter<ValueSpinner>{
    Context mContext;
    int layoutInf;
    ArrayList<ValueSpinner> arraySpinner;

    public AdapterSpinner(Context context, int resource, ArrayList<ValueSpinner> objects) {
        super(context, resource, objects);
        this.mContext = context;
        this.layoutInf = resource;
        this.arraySpinner = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater)
                mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = convertView;
        view = inflater.inflate(layoutInf, null);
        final RadioButton rb_item = (RadioButton) view.findViewById(R.id.rb_item_spinner);
        final TextView item_title = (TextView) view.findViewById(R.id.item_spiner);
        if (position > -1 && arraySpinner.size() > 0) {
            item_title.setText(arraySpinner.get(position).getName_spinner());
        }

        return view;
    }
}
