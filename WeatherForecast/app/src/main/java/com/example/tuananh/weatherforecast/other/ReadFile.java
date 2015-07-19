package com.example.tuananh.weatherforecast.other;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ReadFile {
    Context mContext;

    public ReadFile(Context mContext) {
        this.mContext = mContext;
    }

    private String readFromfile() {
        BufferedReader in = null;
        try {
            StringBuilder buf = new StringBuilder();
            InputStream is = mContext.getAssets().open("KeySetting.txt");
            in = new BufferedReader(new InputStreamReader(is));

            String str;
            boolean isFirst = true;
            while ( (str = in.readLine()) != null ) {
                if (isFirst)
                    isFirst = false;
                else
                    buf.append('\n');
                buf.append(str);
            }
            return buf.toString();
        } catch (IOException e) {
            Log.e("io", "Error opening asset " + "KeySetting.txt");
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    Log.e("io", "Error closing asset " + "KeySetting.txt");
                }
            }
        }
        return null;
    }

    public String getFile(int position){
        String value = readFromfile();
        if (value != null) {
            String[] pargram;
            pargram = value.split("#");
            return pargram[position].trim();
        }
        return null;
    }

    public int getCount(){
        String value = readFromfile();
        if (value != null) {
            String[] pargram = new String[value.split("#").length];
            return pargram.length;
        }
        else return 0;
    }
}
