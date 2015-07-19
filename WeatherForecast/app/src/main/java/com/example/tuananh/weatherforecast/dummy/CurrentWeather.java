package com.example.tuananh.weatherforecast.dummy;

import android.util.Log;

/**
 * Created by TuanAnh on 04/07/2015.
 */
public class CurrentWeather {
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }


    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    public String getRelative_hummid() {
        return relative_hummid;
    }

    public void setRelative_hummid(String relative_hummid) {
        this.relative_hummid = relative_hummid;
    }

    public String getPressure_mb() {
        return pressure_mb;
    }

    public void setPressure_mb(String pressure_mb) {
        this.pressure_mb = pressure_mb;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    private String date, country, weather, relative_hummid,pressure_mb, icon;


    public int getTeamp_f() {
        return teamp_f;
    }

    public void setTeamp_f(int teamp_f) {
        this.teamp_f = teamp_f;
    }

    public int getTeamp_c() {
        return teamp_c;
    }

    public void setTeamp_c(int teamp_c) {
        this.teamp_c = teamp_c;
    }

    private int teamp_f;
    private int teamp_c;

    public int getWind_mdp() {
        return wind_mdp;
    }

    public void setWind_mdp(int wind_mdp) {
        this.wind_mdp = wind_mdp;
    }

    private int wind_mdp;
}
