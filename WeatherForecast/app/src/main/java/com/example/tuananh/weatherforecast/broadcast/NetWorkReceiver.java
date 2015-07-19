package com.example.tuananh.weatherforecast.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.tuananh.weatherforecast.asynctask.WeatherTask;
import com.example.tuananh.weatherforecast.other.NetWorkState;

public class NetWorkReceiver extends BroadcastReceiver {
    private boolean mFlag;
    public NetWorkReceiver(boolean flag) {
        this.mFlag = flag;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        int check = NetWorkState.getConnectivityStatus(context);
        if(check == 1 || check == 2){
            if(mFlag){

            }
        }
    }
}
