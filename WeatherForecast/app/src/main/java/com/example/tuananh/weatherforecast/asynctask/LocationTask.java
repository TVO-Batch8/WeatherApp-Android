package com.example.tuananh.weatherforecast.asynctask;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.example.tuananh.weatherforecast.R;
import com.example.tuananh.weatherforecast.internet.GPS;

public class LocationTask extends AsyncTask<String, Integer, String> {

    private Context mContext = null;
    private boolean mFlag = false;
    private GPS gps;
    private ProgressDialog dialog;

    /**
     * @param Flag what is your's activity(welcomeactivity or mainactivity)
     *             if(mainactivity) Flag = true
     *             else Flag = false
     */
    public LocationTask(Context context, boolean Flag) {
        this.mContext = context;
        this.mFlag = Flag;
        gps = new GPS(mContext);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        String mLoad = mContext.getResources().getString(R.string.gettinglocation);
        dialog = new ProgressDialog(mContext, R.style.NewDialog);
        dialog.setMessage(mLoad + "...");
        dialog.setCancelable(false);
        dialog.show();
    }

    @Override
    protected String doInBackground(String... params) {
        String url = "";

        for (int i = 0; i < i + 1; i++) {
            if (gps.getLatitude() != 0 && gps.getLongitude() != 0) {
                Log.e("getinglocation", gps.getLatitude() + "," + gps.getLongitude());
                url = gps.getLatitude() + "," + gps.getLongitude();
                break;
            }
            Log.e("getinglocation", i + "");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return url;
    }

    @Override
    protected void onPostExecute(String string) {
        super.onPostExecute(string);
        dialog.dismiss();
        /*if (gps.getLongitude() == 0 && gps.getLatitude() == 0){
            // TODO: if gps don't get values
        }
        else {
            new WeatherTask(mContext, mDb, mFlag).execute();
        }*/
        new WeatherTask(mContext, mFlag).execute(string);
        Log.d("linkUrlweather", string);
    }
}
