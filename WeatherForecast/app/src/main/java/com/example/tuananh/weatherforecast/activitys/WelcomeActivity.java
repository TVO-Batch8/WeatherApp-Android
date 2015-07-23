package com.example.tuananh.weatherforecast.activitys;

import android.app.Activity;


import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import com.example.tuananh.weatherforecast.R;
import com.example.tuananh.weatherforecast.asynctask.WeatherTask;
import com.example.tuananh.weatherforecast.internet.GPS;
import com.example.tuananh.weatherforecast.other.NetWorkState;
import com.example.tuananh.weatherforecast.other.SettingShare;
import java.util.Locale;

public class WelcomeActivity extends Activity {

    private boolean mFlag = true;
    private GPS gps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int theme = new SettingShare(this)
                .getShareInt(SettingShare.THEME, 0);
        if (theme > 0) {
            new SettingShare(this).setThemeContext(theme);
        }
        String language = new SettingShare(this)
                .getShare(SettingShare.LANGUAGE, Locale.getDefault().getLanguage());
        Locale locale = new Locale(language);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config,
                getBaseContext().getResources().getDisplayMetrics());
        getActionBar().setTitle(getResources().getString(R.string.app_name));
        setContentView(R.layout.activity_welcome);
    }

    @Override
    protected void onStart() {
        super.onStart();
        gps = new GPS(WelcomeActivity.this);
        int internet = NetWorkState.getConnectivityStatus(WelcomeActivity.this);
        if (internet == NetWorkState.TYPE_MOBILE || internet == NetWorkState.TYPE_WIFI) {
            if (gps.canGetLocation()) {
                for (int i = 0; i < i + 1; i++) {
                    gps = new GPS(WelcomeActivity.this);
                    if (gps.getLatitude() != 0 && gps.getLongitude() != 0) {
                        Log.e("getinglocation", gps.getLatitude() + "," + gps.getLongitude());
                        String url = gps.getLatitude() + "," + gps.getLongitude();
                        new WeatherTask(WelcomeActivity.this, true).execute(url);
                        break;
                    }
                    Log.e("getinglocation", i + "");
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                mFlag = true;
            } else {
                mFlag = false;
                gps.showSettingsAlert(true);
            }
        } else {
            mFlag = false;
            gps.showSettingsAlert(false);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mFlag) {
            finish();
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

}
