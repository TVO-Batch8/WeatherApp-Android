package com.example.tuananh.weatherforecast.json;

import android.content.Context;

import com.example.tuananh.weatherforecast.dummy.Country;
import com.example.tuananh.weatherforecast.dummy.CurrentWeather;
import com.example.tuananh.weatherforecast.dummy.WeekWeather;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class WeatherJSONParser {
    public static final String COUNTRY = "full";
    public static final String DATE = "local_time_rfc822";
    public static final String TEMP_C = "temp_c";
    public static final String TEMP_F = "temp_f";
    public static final String PRESSURE = "pressure_mb";
    public static final String HUMMIDITY = "relative_humidity";
    public static final String WEATHER = "weather";
    public static final String ICON = "icon";
    public static final String WIND = "wind_mph";

    public static final String WEEKDAY_SHORT = "weekday_short";
    public static final String DAY = "day";
    public static final String MONTHNAME = "monthname";
    public static final String YEAR = "year";
    public static final String CONDITIONS = "conditions";
    public static final String FAHRENHEIT = "fahrenheit";
    public static final String CELSIUS = "celsius";

    public static final String CURRENT_OBSERVATION_JSON = "current_observation";
    public static final String DISPLAY_LOCATION_JSON = "display_location";
    public static final String FORECAST_JSON = "forecast";
    public static final String FORECASTDAY_JSON = "forecastday";
    public static final String SIMPLEFORECAST_JSON = "simpleforecast";
    public static final String DATE_JSON = "date";
    public static final String HIGH_JSON = "high";
    public static final String LOW_JSON = "low";

    public static final String ERROR_JSON = "error";
    public static final String TYPE = "type";

    // TODO: 18/07/2015
    public static final String RESPONSE_JSON = "response";
    public static final String RESULT_JSON = "results";
    public static final String ZMW = "zmw";
    public static final String CITY = "city";
    public static final String COUNTRY_STATE = "country_name";


    /**
     * get information weather of current day
     *
     * @param string is object json get from webservice
     */
    public static CurrentWeather getCurrentWeather(String string) throws JSONException {

        CurrentWeather currentWeather = new CurrentWeather();

        //get object from superobject
        JSONObject object = new JSONObject(string);
        JSONObject current_observation = object.getJSONObject(CURRENT_OBSERVATION_JSON);
        JSONObject displayLocation = current_observation.getJSONObject(DISPLAY_LOCATION_JSON);

        //get values from object
        String country = displayLocation.getString(COUNTRY);
        String date = current_observation.getString(DATE);
        String weather = current_observation.getString(WEATHER);
        int teamp_f = current_observation.getInt(TEMP_F);
        int teamp_c = current_observation.getInt(TEMP_C);
        String relative_hummid = current_observation.getString(HUMMIDITY);
        int wind_mdp = current_observation.getInt(WIND);
        String pressure_mb = current_observation.getString(PRESSURE);
        String icon = current_observation.getString(ICON);

        //insert value to currentWeather.class
        currentWeather.setCountry(country);
        currentWeather.setDate(date);
        currentWeather.setIcon(icon);
        currentWeather.setPressure_mb(pressure_mb);
        currentWeather.setRelative_hummid(relative_hummid);
        currentWeather.setTeamp_c(teamp_c);
        currentWeather.setTeamp_f(teamp_f);
        currentWeather.setWeather(weather);
        currentWeather.setWind_mdp(wind_mdp);

        return currentWeather;

    }

    /**
     * get information weather of current and next 6 day.
     *
     * @param string is object json get from webservice
     * @param day    is day in a week from 1 day to 7 day
     */
    public static WeekWeather getWeekWeather(String string, int day) throws JSONException {
        WeekWeather week = new WeekWeather();

        //get object from superobject
        JSONObject object = new JSONObject(string);
        JSONObject forecast = object.getJSONObject(FORECAST_JSON);
        JSONObject simpleforecast = forecast.getJSONObject(SIMPLEFORECAST_JSON);
        JSONArray arrayForecastday = simpleforecast.getJSONArray(FORECASTDAY_JSON);

        JSONObject weekJSON = arrayForecastday.getJSONObject(day);//get a day in 7 day
        JSONObject hight = weekJSON.getJSONObject(HIGH_JSON);
        JSONObject low = weekJSON.getJSONObject(LOW_JSON);
        JSONObject jsonDate = weekJSON.getJSONObject(DATE_JSON);

        //insert value to currentWeather.class
        String date = jsonDate.getString(WEEKDAY_SHORT) + ", "
                + jsonDate.getString(DAY) + " "
                + jsonDate.getString(MONTHNAME) + " "
                + jsonDate.getString(YEAR);
        String icon = weekJSON.getString(ICON);
        String conditions = weekJSON.getString(CONDITIONS);

        String fahrenheit = low.getString(FAHRENHEIT) + "/"
                + hight.getString(FAHRENHEIT) + "F";
        String celsius = low.getString(CELSIUS) + "/"
                + hight.getString(CELSIUS) + "°C";

        //insert value to WeekWeather.class
        week.setIcon(icon);
        week.setDate(date);
        week.setCelsius(celsius);
        week.setConditions(conditions);
        week.setFahrenheit(fahrenheit);
        return week;
    }

    public static int countArrayWeek(String string) throws JSONException {
        int count = 0;

        JSONObject object = new JSONObject(string);
        JSONObject forecast = object.getJSONObject(FORECAST_JSON);
        JSONObject simpleforecast = forecast.getJSONObject(SIMPLEFORECAST_JSON);
        JSONArray arrayForecastday = simpleforecast.getJSONArray(FORECASTDAY_JSON);
        count = arrayForecastday.length();
        return count;

    }

    // TODO: 18/07/2015
    public static ArrayList<Country> getCountry(String string) throws JSONException {
        ArrayList<Country> arrayCountry = new ArrayList<>();
        JSONObject jsonObject = new JSONObject(string);
        JSONObject jsonResponse = jsonObject.getJSONObject(RESPONSE_JSON);
        JSONArray arrayResults = jsonResponse.getJSONArray(RESULT_JSON);
        for (int i = 0; i < arrayResults.length(); i++) {
            JSONObject itemResult = arrayResults.getJSONObject(i);
            Country country = new Country();
            country.setZmw("zmw:" + itemResult.getString(ZMW));
            String city = itemResult.getString(CITY);
            String country_name = itemResult.getString(COUNTRY_STATE);
            country.setCountryname(
                    city + "("
                            + country_name + ")");
            arrayCountry.add(country);
        }
        return arrayCountry;

    }

    public static String readError(String string) throws JSONException {
        String data = "";
        JSONObject jsonObject = new JSONObject(string);
        JSONObject jsonResponse = jsonObject.getJSONObject(RESPONSE_JSON);
        JSONObject jsonError = jsonResponse.getJSONObject(ERROR_JSON);
        data = jsonError.getString(TYPE);
        return data;
    }
}