package com.example.tuananh.weatherforecast.other;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.tuananh.weatherforecast.R;
import com.example.tuananh.weatherforecast.fragments.ThemeFragment;
import com.example.tuananh.weatherforecast.internet.HTTP_Url;

public class SettingShare {
    private SharedPreferences sharedPreferences;
    public static final String NAME_SHARE = "setting";
    public static final String LANGUAGE ="language";
    public static final String TEMPERATURE ="temperature";
    public static final String THEME ="background";
    public static final String API_KEY ="apikey";
    public static final String LANGUAGE_DEFALUT = "en";
    public static final String LANGUAGE_VI = "vi";
    public static final String LANGUAGE_BEFORE = "language_before";

    Context mContext;
    public SettingShare(Context context) {
        sharedPreferences = context.getSharedPreferences
                (NAME_SHARE, Context.MODE_PRIVATE);
        this.mContext = context;
    }

    /**
     * input value to sharedference
     * @param key is input value with key
     * @param input is value
     */
    public void saveShare(String key, String input){
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putString(key, input);
        edit.apply();
        //edit.commit();
    }

    public void saveShareInt(String key, int input){
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putInt(key, input);
        edit.apply();
        //edit.commit();
    }

    public String getShare(String key, String def){
        return sharedPreferences.getString(key, def);
    }

    public int getShareInt(String key, int def){
        return sharedPreferences.getInt(key, def);
    }

    public String chooseLanguage(String string){
        switch (string){
            case LANGUAGE_VI:
                return HTTP_Url.LINK_WEATHER_VI;
            case SettingShare.LANGUAGE_DEFALUT:
                return HTTP_Url.LINK_WEATHER;
            default:
                return HTTP_Url.LINK_WEATHER;
        }
    }

    public void setThemeContext(int theme){
        switch (theme)
        {
            case ThemeFragment.BLACK_THEME:
                mContext.setTheme(R.style.BlackBackground);
                break;
            case ThemeFragment.RED_THEME:
                mContext.setTheme(R.style.RedBackground);
                break;
            case ThemeFragment.PINK_THEME:
                mContext.setTheme(R.style.PinkBackground);
                break;
            case ThemeFragment.DEFAULE_THEME:
                mContext.setTheme(R.style.AppTheme);
                break;
        }
    }
}
