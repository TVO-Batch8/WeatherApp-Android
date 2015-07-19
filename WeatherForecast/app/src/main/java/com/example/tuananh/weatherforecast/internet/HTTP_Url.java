package com.example.tuananh.weatherforecast.internet;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;

public class HTTP_Url {

    public static final String URL ="http://api.wunderground.com/api/";
    public static final String LINK_WEATHER ="/conditions/forecast7day/q/";
    public static final String LINK_WEATHER_VI ="/conditions/forecast7day/lang:VU/q/";
    public static final String JSON =".json";

    public static String readJSON(String url) throws SocketTimeoutException{

        HttpURLConnection httpURLConnection = null;
        InputStream inputStream = null;
        try {
            //connect web from url
            httpURLConnection =
                    (HttpURLConnection) (new URL(url)).openConnection();
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setConnectTimeout(20*1000);
            httpURLConnection.setReadTimeout(20 * 1000);
            //connect to webservice
            httpURLConnection.connect();

            //read data from file url and input data from url
            inputStream = httpURLConnection.getInputStream();

            StringBuffer buffer = new StringBuffer();

            BufferedReader readBuffered = new BufferedReader(new InputStreamReader(inputStream));
            String line = null;
            while ((line = readBuffered.readLine() )!= null) {
                buffer.append(line);
            }
            inputStream.close();
            httpURLConnection.disconnect();
            return buffer.toString();

        } catch (IOException e) {
            e.printStackTrace();
            try {
                if (e instanceof SocketTimeoutException) {// compare IOException and SocketTimeoutExcepTion
                    throw new SocketTimeoutException();
                }
            } catch (SocketTimeoutException f) {
            }
        }catch(Throwable t) {
                t.printStackTrace();
                Log.e("time out", t + "");

        } finally {
            if (httpURLConnection != null && inputStream != null) {
                httpURLConnection.disconnect();
                try {
                    inputStream.close();
                } catch (Throwable t) {
                }
            }
        }
        return null;
    }

}
