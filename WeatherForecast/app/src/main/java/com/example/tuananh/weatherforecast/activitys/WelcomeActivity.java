package com.example.tuananh.weatherforecast.activitys;

import android.app.Activity;
import android.content.ContentValues;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.tuananh.weatherforecast.R;
import com.example.tuananh.weatherforecast.asynctask.LocationTask;
import com.example.tuananh.weatherforecast.asynctask.WeatherTask;
import com.example.tuananh.weatherforecast.internet.GPS;
import com.example.tuananh.weatherforecast.internet.HTTP_Url;
import com.example.tuananh.weatherforecast.other.NetWorkState;
import com.example.tuananh.weatherforecast.other.SettingShare;
import com.example.tuananh.weatherforecast.sqlite.MyDatabase;

import java.util.Locale;

public class WelcomeActivity extends Activity {

    //private String url;
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
        /*String key = new SettingShare(WelcomeActivity.this)
                .getShare(SettingShare.API_KEY, "513c19f62f591775");
        String langauage = new SettingShare(WelcomeActivity.this)
                .getShare(SettingShare.LANGUAGE, "");
        String language_default = Locale.getDefault().getLanguage();

        if (langauage.equals("")) {
            url = HTTP_Url.URL + key +
                    new SettingShare(this).chooseLanguage(language_default);
        } else {
            url = HTTP_Url.URL + key +
                    new SettingShare(this).chooseLanguage(langauage);
        }*/

        gps = new GPS(WelcomeActivity.this);


    }

    @Override
    protected void onStart() {
        super.onStart();
        int internet = NetWorkState.getConnectivityStatus(WelcomeActivity.this);
        if (internet == NetWorkState.TYPE_MOBILE || internet == NetWorkState.TYPE_WIFI && gps.canGetLocation()) {
            new LocationTask(WelcomeActivity.this, true).execute();
            //new WeatherTask(WelcomeActivity.this, true).execute("london");
        } else gps.showSettingsAlert();
    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }
}
