package com.example.tuananh.weatherforecast.asynctask;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.widget.Toast;

import com.example.tuananh.weatherforecast.R;
import com.example.tuananh.weatherforecast.activitys.MainActivity;
import com.example.tuananh.weatherforecast.dummy.Country;
import com.example.tuananh.weatherforecast.dummy.CurrentWeather;
import com.example.tuananh.weatherforecast.dummy.WeekWeather;
import com.example.tuananh.weatherforecast.internet.GPS;
import com.example.tuananh.weatherforecast.internet.HTTP_Url;
import com.example.tuananh.weatherforecast.json.WeatherJSONParser;
import com.example.tuananh.weatherforecast.other.NetWorkState;
import com.example.tuananh.weatherforecast.other.ReadFile;
import com.example.tuananh.weatherforecast.other.SettingShare;
import com.example.tuananh.weatherforecast.sqlite.MyDatabase;

import org.json.JSONException;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.Locale;

public class WeatherTask extends AsyncTask<String, Integer, String> {
    //check current language of device;
    private SQLiteDatabase mDb;
    private Context mContext;
    private boolean mFlag;
    private ProgressDialog showDialog;
    private CurrentWeather getCurrent;
    private ArrayList<WeekWeather> mArrayList;
    private ArrayList<Country> arrayCountry;
    private int mCount = 0;
    private String mError = "";
    private String location = "";

    /**
     * @param flag_show check what is Activity(welcomeActivity or MainActivity)
     *                  if(welcomeActivity) flag_show = false
     *                  else flag_show = true
     */
    public WeatherTask(Context context, boolean flag_show) {
        this.mFlag = flag_show;
        this.mContext = context;
        this.mDb = new MyDatabase(mContext).getWritableDatabase();
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if (!mFlag) {
            showDialog = new ProgressDialog(mContext, R.style.NewDialog);
            showDialog.setMessage(mContext.getResources().getString(R.string.please) + "...");
            showDialog.setCancelable(false);
            showDialog.show();
        }
    }

    @Override
    protected String doInBackground(String... params) {
        String url;
        //set automatic key
        int key = new SettingShare(mContext)
                .getShareInt(SettingShare.API_KEY, 0);
        //chosse language
        String langauage = new SettingShare(mContext)
                .getShare(SettingShare.LANGUAGE, Locale.getDefault().getLanguage());

        url = HTTP_Url.URL + new ReadFile(mContext).getFile(key) +
                new SettingShare(mContext).chooseLanguage(langauage)
                + params[0] + HTTP_Url.JSON;
        String getUrl = "";
        location = params[0];
        try {
            getUrl = HTTP_Url.readJSON(url);

        } catch (SocketTimeoutException e) {
            e.printStackTrace();
        }
        mArrayList = new ArrayList<>();
        arrayCountry = new ArrayList<>();
        getCurrent = new CurrentWeather();

        if (getUrl != null) {
            getCurrent = WeatherJSONParser.getCurrentWeather(getUrl);
            String error = WeatherJSONParser.readError(getUrl);
            mCount = WeatherJSONParser.countArrayWeek(getUrl);
            for (int i = 0; i < mCount; i++) {
                WeekWeather getWeek;
                getWeek = WeatherJSONParser.getWeekWeather(getUrl, i);
                mArrayList.add(getWeek);
            }
            arrayCountry = WeatherJSONParser.getCountry(getUrl);
            return params[0];
        } else
            return null;
    }

    @Override
    protected void onPostExecute(String aVoid) {
        super.onPostExecute(aVoid);
        if (!mFlag) {
            showDialog.dismiss();
        }

        //valid information weather not nul
        if (aVoid != null) {
            if (getCurrent != null && mArrayList.size() > 0) {
                //delete all value in table
                mDb.delete(MyDatabase.CURRENTTABLE, null, null);
                mDb.delete(MyDatabase.WEEKTABLE, null, null);

                ContentValues valuesCurrent = new ContentValues();
                valuesCurrent.put(MyDatabase.COUNTRY, getCurrent.getCountry());
                valuesCurrent.put(MyDatabase.DATE, getCurrent.getDate());
                valuesCurrent.put(MyDatabase.STATE, getCurrent.getWeather());
                valuesCurrent.put(MyDatabase.TEAMP_F, getCurrent.getTeamp_f());
                valuesCurrent.put(MyDatabase.TEAMP_C, getCurrent.getTeamp_c());
                valuesCurrent.put(MyDatabase.HUMMID, getCurrent.getRelative_hummid());
                valuesCurrent.put(MyDatabase.PRESSURE, getCurrent.getPressure_mb());
                valuesCurrent.put(MyDatabase.ICON, getCurrent.getIcon());
                valuesCurrent.put(MyDatabase.WIND, getCurrent.getWind_mdp());
                valuesCurrent.put(MyDatabase.HOUR, getCurrent.getHour());
                valuesCurrent.put(MyDatabase.DAY, getCurrent.getDay());
                valuesCurrent.put(MyDatabase.MONTH, getCurrent.getMonth());
                valuesCurrent.put(MyDatabase.YEAR, getCurrent.getYear());
                mDb.insert(MyDatabase.CURRENTTABLE, null, valuesCurrent);

                for (int i = 1; i < mCount; i++) {
                    ContentValues week = new ContentValues();
                    week.put(MyDatabase.DATE, mArrayList.get(i).getDate());
                    week.put(MyDatabase.ICON, mArrayList.get(i).getIcon());
                    week.put(MyDatabase.TEAMP_C, mArrayList.get(i).getCelsius());
                    week.put(MyDatabase.TEAMP_F, mArrayList.get(i).getFahrenheit());
                    week.put(MyDatabase.STATE, mArrayList.get(i).getConditions());
                    mDb.insert(MyDatabase.WEEKTABLE, null, week);
                }

                //if (mFlag) {
                Intent intent = new Intent(mContext, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                mContext.startActivity(intent);
                //}
            } else {
                if (arrayCountry.size() > 0) {
                    //Log.e("country", "not null");
                    String names[] = new String[arrayCountry.size()];
                    for (int i = 0; i < arrayCountry.size(); i++) {
                        names[i] = arrayCountry.get(i).getCountryname();
                    }

                    AlertDialog.Builder builder = new AlertDialog.
                            Builder(mContext, R.style.NewDialog);

                    builder.setTitle(mContext.getResources().getString(R.string.listcountry))
                            .setItems(names, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    new WeatherTask(mContext, mFlag).execute(
                                            arrayCountry.get(which).getZmw()
                                    );

                                }
                            });
                    builder.create();
                    builder.show();
                } else {
                    Toast.makeText(mContext, mContext.getResources()
                            .getString(R.string.find), Toast.LENGTH_SHORT).show();

                }
            }
        } else {
            if (!mFlag) {
                Toast.makeText(mContext, mContext.getResources()
                        .getString(R.string.messagedialog2), Toast.LENGTH_SHORT).show();
            } else setDialog();
        }
    }


    public void setDialog() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                mContext, R.style.NewDialog);

        // set dialog message
        alertDialogBuilder
                .setMessage(mContext.getResources().getString(R.string.messagedialog2)+", "
                        +mContext.getResources().getString(R.string.refreshapp) + "\n"
                                + mContext.getResources().getString(R.string.clickyes) + "\n"
                                + mContext.getResources().getString(R.string.clickno)
                )
                .setCancelable(false)
                .setPositiveButton(mContext.getResources().getString(R.string.btn_yes_dialog_question)
                        , new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        GPS gps = new GPS(mContext);
                        int internet = NetWorkState.getConnectivityStatus(mContext);
                        if (internet == NetWorkState.TYPE_MOBILE || internet == NetWorkState.TYPE_WIFI) {
                            new WeatherTask(mContext, mFlag).execute(location);
                            mFlag = true;

                        } else {
                            mFlag = false;
                            gps.showSettingsAlert(false);

                        }
                    }
                })
                .setNegativeButton(mContext.getResources().getString(R.string.btn_no_dialog_question)
                        , new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        System.exit(0);
                        //finish();
                    }
                });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();
    }
}
