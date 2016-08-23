package com.emagicindia.realeastate.utils;

import android.util.Log;

import com.emagicindia.realeastate.activity.SelectionProcessActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

import static com.emagicindia.realeastate.MainActivity.citytext;

/**
 * Created by dell on 8/9/2016.
 */
public class PlaceAPI {

    private static final String TAG = PlaceAPI.class.getSimpleName();

    private static final String PLACES_API_BASE = "https://maps.googleapis.com/maps/api/place";
    private static final String TYPE_AUTOCOMPLETE = "/autocomplete";
    private static final String OUT_JSON = "/json";

    private static final String API_KEY = "AIzaSyCXctO9hmTv2PHGq3jO393Ohfreb0SUeak";

    public ArrayList<ArrayList<String>> autocomplete (String input) {
        ArrayList<String> resultList = null;
        ArrayList<String> remainingAddressList = null;
        ArrayList<ArrayList<String>> outer = null;

        HttpURLConnection conn = null;
        StringBuilder jsonResults = new StringBuilder();

        try {
            if(citytext==null){
                citytext="Mumbai";
            }
            StringBuilder sb = new StringBuilder(PLACES_API_BASE + TYPE_AUTOCOMPLETE + OUT_JSON);
            sb.append("?key=" + API_KEY);
            sb.append("&components=country:in&types=geocode");
            sb.append("&input=" + URLEncoder.encode(citytext+" "+input, "utf8"));

            URL url = new URL(sb.toString());
            conn = (HttpURLConnection) url.openConnection();
            InputStreamReader in = new InputStreamReader(conn.getInputStream());

            // Load the results into a StringBuilder
            int read;
            char[] buff = new char[1024];
            while ((read = in.read(buff)) != -1) {
                jsonResults.append(buff, 0, read);
            }
        } catch (MalformedURLException e) {
            Log.e(TAG, "Error processing Places API URL", e);
            return outer;
        } catch (IOException e) {
            Log.e(TAG, "Error connecting to Places API", e);
            return outer;
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }

        try {
            // Log.d(TAG, jsonResults.toString());

            // Create a JSON object hierarchy from the results
            JSONObject jsonObj = new JSONObject(jsonResults.toString());
            JSONArray predsJsonArray = jsonObj.getJSONArray("predictions");

            // Extract the Place descriptions from the results
            resultList = new ArrayList<String>(predsJsonArray.length());
            remainingAddressList = new ArrayList<String>(predsJsonArray.length());
            outer=new ArrayList<ArrayList<String>>(2);
            for (int i = 0; i < predsJsonArray.length(); i++) {
                resultList.add(predsJsonArray.getJSONObject(i).getJSONArray("terms").getJSONObject(0).getString("value"));

                StringBuilder s=new StringBuilder("");
                for (int j = 1; j < predsJsonArray.getJSONObject(i).getJSONArray("terms").length(); j++) {
                    if (j == 1) {
                        s.append(predsJsonArray.getJSONObject(i).getJSONArray("terms").getJSONObject(j).getString("value").isEmpty() ? null : predsJsonArray.getJSONObject(i).getJSONArray("terms").getJSONObject(j).getString("value"));
                    }
                    else{
                        s.append(predsJsonArray.getJSONObject(i).getJSONArray("terms").getJSONObject(j).getString("value").isEmpty() ? null : ", " + predsJsonArray.getJSONObject(i).getJSONArray("terms").getJSONObject(j).getString("value"));
                    }
                }
                if(s!=null) {
                    remainingAddressList.add(s.toString());
                }else{
                    break;
                }

            }
            outer.add(resultList);
            outer.add(remainingAddressList);
        } catch (JSONException e) {
            Log.e(TAG, "Cannot process JSON results", e);
        }

        return outer;
    }
}
