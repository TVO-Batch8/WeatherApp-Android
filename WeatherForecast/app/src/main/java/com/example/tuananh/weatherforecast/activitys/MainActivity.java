package com.example.tuananh.weatherforecast.activitys;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.tuananh.weatherforecast.R;
import com.example.tuananh.weatherforecast.adapter.TabAdapter;
import com.example.tuananh.weatherforecast.asynctask.LocationTask;
import com.example.tuananh.weatherforecast.fragments.CurrentWeatherFragment;
import com.example.tuananh.weatherforecast.fragments.ListSettingFragment;
import com.example.tuananh.weatherforecast.fragments.WeekWeatherFragment;
import com.example.tuananh.weatherforecast.internet.GPS;
import com.example.tuananh.weatherforecast.other.NetWorkState;
import com.example.tuananh.weatherforecast.other.ReadFile;
import com.example.tuananh.weatherforecast.other.SettingShare;
import com.example.tuananh.weatherforecast.sqlite.MyDatabase;
import com.example.tuananh.weatherforecast.tabs.SlidingTabLayout;

import java.util.Locale;

public class MainActivity extends FragmentActivity {

    boolean mFlag;
    ViewPager mPager;
    TabAdapter adapter;
    String[] arrayTab;
    SlidingTabLayout tabs;
    public SQLiteDatabase mDb;
    public MyDatabase mDbHelper;
    private GPS gps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final ActionBar actionBar;
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

        setContentView(R.layout.activity_main);

        String language_before = new SettingShare(this)
                .getShare(SettingShare.LANGUAGE_BEFORE, "");
        if (language_before.equals("")
                || language_before.equals(Locale.getDefault().getLanguage())) {
            if (language_before.equals("")) {
                new SettingShare(this).saveShare(SettingShare.LANGUAGE_BEFORE,
                        Locale.getDefault().getLanguage());
            }
        } else {
            new SettingShare(this).saveShare(SettingShare.LANGUAGE_BEFORE,
                    Locale.getDefault().getLanguage());
            setShowDialog();
        }
        arrayTab = getResources().getStringArray(R.array.tab_name);

        actionBar = getActionBar();
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(false);
        }

        adapter = new TabAdapter(getSupportFragmentManager(), arrayTab);

        mPager = (ViewPager) findViewById(R.id.pager);
        mPager.setAdapter(adapter);

        tabs = (SlidingTabLayout) findViewById(R.id.tab);
        tabs.setDistributeEvenly(true);
        tabs.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return getResources().getColor(R.color.tabsScrollColor);
            }
        });
        tabs.setViewPager(mPager);

        mDbHelper = new MyDatabase(this);
        mDb = mDbHelper.getWritableDatabase();

        new ReadFile(this).getFile(0);
        Log.d("lifeactivity", "onCreate");

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        switch (id) {
            case R.id.action_settings: {
                mFlag = true;
                Intent intent = new Intent(MainActivity.this, SettingActivity.class);
                startActivity(intent);
                return true;
            }
            case R.id.action_search:
                mFlag = true;
                Intent intent = new Intent(MainActivity.this, SearchActivity.class);
                startActivity(intent);
                return true;
            case R.id.action_refesh:
                mFlag = true;
                setRefresh();
                return true;
        }


        return super.onOptionsItemSelected(item);
    }

    /*private void replaceFragment(int id, String stackName, Fragment frament) {
        FragmentTransaction fragmentTransaction = mManager.beginTransaction();
        fragmentTransaction.replace(id, frament);
        fragmentTransaction.addToBackStack(stackName);
        fragmentTransaction.commit();
    }*/


    public void setShowDialog() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                MainActivity.this, R.style.NewDialog);

        // set title
        alertDialogBuilder.setTitle(getResources().getString(R.string.title_dialog_question));

        // set dialog message
        alertDialogBuilder
                .setMessage(getResources().getString(R.string.message_dialog_question))
                .setCancelable(false)
                .setPositiveButton(getResources().getString(R.string.btn_yes_dialog_question)
                        , new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        setRefresh();
                    }
                })
                .setNegativeButton(getResources().getString(R.string.btn_no_dialog_question)
                        , new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // if this button is clicked, just close
                        // the dialog box and do nothing
                        dialog.cancel();
                        //finish();
                    }
                });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();
    }

    private void setRefresh() {
        gps = new GPS(MainActivity.this);
        int internet = NetWorkState.getConnectivityStatus(MainActivity.this);
        if (internet == NetWorkState.TYPE_MOBILE || internet == NetWorkState.TYPE_WIFI) {
            if (gps.canGetLocation()) {
                new LocationTask(MainActivity.this, mFlag).execute();

            } else Toast.makeText(this, getResources().getString(R.string.messagedialog1)
                    , Toast.LENGTH_SHORT).show();
        }
        else Toast.makeText(this, getResources().getString(R.string.messagedialog2)
                , Toast.LENGTH_SHORT).show();

    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
        Log.d("lifeactivity", "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("lifeactivity", "onDestroy");
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        /*if (mOretation == Configuration.ORIENTATION_LANDSCAPE) {
            finish();
        } else if (mManager.getBackStackEntryCount() == 0) {
            finish();
        }*/
        finish();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }
}
