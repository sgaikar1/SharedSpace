package com.emagicindia.realeastate.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.emagicindia.realeastate.R;
import com.emagicindia.realeastate.adapter.CityAdapter;
import com.emagicindia.realeastate.model.AdressParameters;
import com.emagicindia.realeastate.model.CityItem;
import com.emagicindia.realeastate.utils.AppUtils;
import com.emagicindia.realeastate.utils.LocationService;
import com.vistrav.ask.Ask;
import com.vistrav.ask.annotations.AskDenied;
import com.vistrav.ask.annotations.AskGranted;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by dell on 8/11/2016.
 */

public class CityActivity extends AppCompatActivity implements CityAdapter.CityListsener{
    private RecyclerView recyclerView;
    private Context mContext;
    private CityAdapter adapter;
    private String[] cityArray = null;
    private Toolbar mToolbar;
    private static final String TAG = CityActivity.class.getSimpleName();
    List<AdressParameters> listofAddress = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city);
        mContext=this;

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        TextView mTitle = (TextView) mToolbar.findViewById(R.id.tv_toolbar_title);
        mTitle.setText(getResources().getString(R.string.change_location));

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(getResources().getString(R.string.change_location));
        TextView tvLocation = (TextView)findViewById(R.id.tv_city_location);

        recyclerView = (RecyclerView)findViewById(R.id.rv_city);
        cityArray = getResources().getStringArray(R.array.array_city);
        adapter = new CityAdapter(mContext);
        adapter.setCities(getData());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        adapter.registerCityListener(this);
        final Geocoder geocoder = new Geocoder(mContext, Locale.getDefault());

        Ask.on(this)
                .forPermissions(Manifest.permission.ACCESS_FINE_LOCATION)
                .withRationales("Location permission needed to improve your property search.")
                .go();

        tvLocation.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                int hasFineLocationPermission = 0;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    hasFineLocationPermission = checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION);
                    if (hasFineLocationPermission != PackageManager.PERMISSION_GRANTED) {
                        AppUtils.showNoLocationPermissionDialogue(mContext);
                        return;
                    }
                }

//                List<String>addressParameters =  AppUtils.getCityAndSubLocality(mContext,geocoder);
                listofAddress = AppUtils.getCityAndSubLocality(mContext,geocoder);
                if (listofAddress != null) {
                    for (int i = 0; i < listofAddress.size(); i++) {
                        AdressParameters adressParameters = listofAddress.get(i);
                        String city = adressParameters.getCity();
                        String sublocal = adressParameters.getSubLocality();
                        System.out.println(TAG + " got city " + city);

                        for(int k=0;k<cityArray.length;k++){
                            if(cityArray[k].equalsIgnoreCase(city)){
                                String cityname = cityArray[k];
                                Intent intent = new Intent();
                                intent.putExtra("city_name", cityname);
                                setResult(1, intent);
                                finish();
                                System.out.print("Got city " + cityname);
                                break;
                            }else if(k==cityArray.length-1){
                                Toast.makeText(mContext,"We are not present in "+city+", Try other options",Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        }
                        adressParameters.setCity("");
                        adressParameters.setSubLocality("");
                    }
                    listofAddress.clear();
                }
                /*AdressParameters adressParameters = new AdressParameters();
                List<String>addressParameters = new ArrayList<String>(2);
                addressParameters.add(adressParameters.getCity());
                addressParameters.add(adressParameters.getSubLocality());
                if(addressParameters!=null) {
                    addressParameters.get(0);
                    addressParameters.get(1);
                    for(int i=0;i<cityArray.length;i++){
                        if(cityArray[i].equalsIgnoreCase(addressParameters.get(0))){
                            String cityname = cityArray[i];
                            Intent intent = new Intent();
                            intent.putExtra("city_name", cityname);
                            setResult(1, intent);
                            finish();
                            System.out.print("Got city " + cityname);
                            break;
                        }else if(i==cityArray.length-1){
                            Toast.makeText(mContext,"We are not present in "+addressParameters.get(0)+", Try other options",Toast.LENGTH_SHORT).show();
                            finish();
                        }

                    }
                }else{
                    System.out.println("Location not available in city activity");
                }*/
            }
        });
    }
    public List<CityItem> getData() {
        List<CityItem> data = new ArrayList<>();

        // preparing navigation drawer items
        for (int i = 0; i < cityArray.length; i++) {
            CityItem navItem = new CityItem();
            navItem.setTitle(cityArray[i]);
            data.add(navItem);
        }
        return data;
    }

    @Override
    public void onCitySelected(CityItem city) {
        String cityname = city.getTitle();
        Intent intent = new Intent();
        intent.putExtra("city_name", cityname);
        setResult(1, intent);
        finish();
    }

    //optional
    @AskGranted(Manifest.permission.ACCESS_FINE_LOCATION)
    public void mapAccessGranted() {
        Log.i(TAG, "MAP GRANTED");
    }

    //optional
    @AskDenied(Manifest.permission.ACCESS_FINE_LOCATION)
    public void mapAccessDenied() {
        Log.i(TAG, "MAP DENIED");
    }
}
