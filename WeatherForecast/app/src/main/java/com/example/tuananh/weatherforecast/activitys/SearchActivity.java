package com.example.tuananh.weatherforecast.activitys;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.tuananh.weatherforecast.R;
import com.example.tuananh.weatherforecast.asynctask.LocationTask;
import com.example.tuananh.weatherforecast.asynctask.WeatherTask;
import com.example.tuananh.weatherforecast.other.NetWorkState;
import com.example.tuananh.weatherforecast.other.SettingShare;

import java.util.Locale;

public class SearchActivity extends Activity implements View.OnClickListener{
    EditText edt_Request;
    Button btn_search, btn_cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int theme = new SettingShare(this)
                .getShareInt(SettingShare.THEME, 0);
        if (theme > 0 && theme < 4) {
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

        setContentView(R.layout.activity_search);
        edt_Request = (EditText) findViewById(R.id.edt_search);
        btn_search = (Button) findViewById(R.id.btn_search);
        btn_cancel = (Button) findViewById(R.id.btn_cancel_search);
        btn_search.setOnClickListener(this);
        btn_cancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.btn_search:
                if(edt_Request.getText().length() > 0) {
                    int internet = NetWorkState.getConnectivityStatus(this);
                    if (internet == NetWorkState.TYPE_MOBILE || internet == NetWorkState.TYPE_WIFI) {
                        new WeatherTask(this, false).execute(edt_Request.getText().toString());
                    } else Toast.makeText(this, getResources().getString(R.string.messagedialog2)
                            , Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(this,
                            getResources().getString(R.string.input_emty), Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btn_cancel_search:
                Intent intent = new Intent(SearchActivity.this, MainActivity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(SearchActivity.this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }
}
