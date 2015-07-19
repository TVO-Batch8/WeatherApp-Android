package com.example.tuananh.weatherforecast.activitys;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;

import com.example.tuananh.weatherforecast.R;
import com.example.tuananh.weatherforecast.fragments.LanguageFragment;
import com.example.tuananh.weatherforecast.fragments.ListSettingFragment;
import com.example.tuananh.weatherforecast.fragments.ThemeFragment;
import com.example.tuananh.weatherforecast.other.SettingShare;

import java.util.Locale;

public class SettingActivity extends Activity implements ListSettingFragment.setOnClickLanuage,
        ListSettingFragment.setOnClickTheme {
    public static final String FRAGMENT_LANGUAGE = "frg_language";
    public static final String FRAGMENT_THEME = "frg_theme";
    public static final String FRAGMENT_LIST = "frg_list";
    private FragmentManager mManager;
    private int mOrientation;

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
        getActionBar().setTitle(getResources().getString(R.string.action_settings));

        setContentView(R.layout.activity_setting);
        mManager = getFragmentManager();

        mOrientation = getResources().getConfiguration().orientation;
        if (mOrientation == Configuration.ORIENTATION_PORTRAIT) {
            replaceFragment(R.id.listordetail_setting, FRAGMENT_LIST, new ListSettingFragment());
        } else {
            replaceFragment(R.id.layout_list, FRAGMENT_LIST, new ListSettingFragment());
            replaceFragment(R.id.layout_detail, FRAGMENT_LANGUAGE, new LanguageFragment());
        }
        Log.d("lifeactivity", "oncreate");
    }

    @Override
    public void onClickLanguage() {
        if (mOrientation == Configuration.ORIENTATION_PORTRAIT) {
            replaceFragment(R.id.listordetail_setting, FRAGMENT_LANGUAGE, new LanguageFragment());
        } else {
            replaceFragment(R.id.layout_detail, FRAGMENT_LANGUAGE, new LanguageFragment());
        }
    }

    @Override
    public void onClickTheme() {
        if (mOrientation == Configuration.ORIENTATION_PORTRAIT) {
            replaceFragment(R.id.listordetail_setting, FRAGMENT_THEME, new ThemeFragment());

        } else {
            replaceFragment(R.id.layout_detail, FRAGMENT_THEME, new ThemeFragment());
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        setContentView(R.layout.activity_setting);
        mOrientation = newConfig.orientation;
        if (mOrientation == Configuration.ORIENTATION_PORTRAIT) {
            setFragmentConfiguration(R.id.listordetail_setting);
        } else {
            if (mOrientation == Configuration.ORIENTATION_LANDSCAPE) {
                replaceFragment(R.id.layout_list, FRAGMENT_LIST, new ListSettingFragment());
                setFragmentConfiguration(R.id.layout_detail);
            }
        }
    }

    private String getVisibleFragment() {
        return mManager.getBackStackEntryAt(mManager.getBackStackEntryCount() - 1).getName();
    }

    private void setFragmentConfiguration(int id) {
        String tag = getVisibleFragment();
        switch (tag) {
            case FRAGMENT_THEME:
                replaceFragment(id, FRAGMENT_THEME, new ThemeFragment());
                break;
            case FRAGMENT_LANGUAGE:
                replaceFragment(id, FRAGMENT_LANGUAGE, new LanguageFragment());
                break;
            default:
                Log.e("ERROR", "NULL TAG");
        }
    }

    private void replaceFragment(int id, String stackName, Fragment frament) {
        FragmentTransaction fragmentTransaction = mManager.beginTransaction();
        fragmentTransaction.replace(id, frament);
        fragmentTransaction.addToBackStack(stackName);
        fragmentTransaction.commit();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (mOrientation == Configuration.ORIENTATION_LANDSCAPE) {
            Intent intent = new Intent(SettingActivity.this, MainActivity.class);
            startActivity(intent);
        } else if (mManager.getBackStackEntryCount() == 0) {
            Intent intent = new Intent(SettingActivity.this, MainActivity.class);
            startActivity(intent);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("lifeactivity", "onstop");
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("lifeactivity", "onstop");
    }
}
