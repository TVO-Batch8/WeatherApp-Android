package com.example.tuananh.weatherforecast.other;

import android.widget.ImageView;

import com.example.tuananh.weatherforecast.R;

/**
 * Created by TuanAnh on 08/07/2015.
 */
public class getImageView {

    public static void imgv(String data, ImageView imgv) {
        String choose = data;
        switch (choose) {
            case "chanceflurries": {
                imgv.setImageResource(R.drawable.chanceflurries);
                break;
            }
            case "chancerain": {
                imgv.setImageResource(R.drawable.chancerain);
                break;
            }
            case "chancesleet": {
                imgv.setImageResource(R.drawable.sleet);
                break;
            }
            case "chancesnow": {
                imgv.setImageResource(R.drawable.chancesnow);
                break;
            }
            case "chancetstorms": {
                imgv.setImageResource(R.drawable.chancetstorms);
                break;
            }
            case "clear": {
                imgv.setImageResource(R.drawable.clear);
                break;
            }
            case "cloudy" :{
                imgv.setImageResource(R.drawable.cloudy);
                break;
            }
            case "flurries": {
                imgv.setImageResource(R.drawable.flurries);
                break;
            }
            case "fog": {
                imgv.setImageResource(R.drawable.fog);
                break;
            }
            case "hazy": {
                imgv.setImageResource(R.drawable.fog);
                break;
            }
            case "mostlycloudy": {
                imgv.setImageResource(R.drawable.mostlycloudy);
                break;
            }
            case "mostlysunny": {
                imgv.setImageResource(R.drawable.mostlysunny);
                break;
            }
            case "partlycloudy": {
                imgv.setImageResource(R.drawable.partlycloudy);
                break;
            }
            case "partlysunny": {
                imgv.setImageResource(R.drawable.partlysunny);
                break;
            }
            case "sleet": {
                imgv.setImageResource(R.drawable.sleet);
                break;
            }
            case "rain": {
                imgv.setImageResource(R.drawable.rain);
                break;
            }
            case "snow": {
                imgv.setImageResource(R.drawable.snow);
                break;
            }
            case "sunny": {
                imgv.setImageResource(R.drawable.sunny);
                break;
            }
            case "tstorms": {
                imgv.setImageResource(R.drawable.tstorms);
                break;
            }

        }
    }
}
