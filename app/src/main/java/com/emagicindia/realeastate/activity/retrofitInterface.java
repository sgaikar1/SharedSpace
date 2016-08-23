package com.emagicindia.realeastate.activity;

import com.emagicindia.realeastate.model.CityArrayItem;
import com.emagicindia.realeastate.model.JSONResponse;
import com.emagicindia.realeastate.model.PropertyItem;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by dell on 8/12/2016.
 */

public interface retrofitInterface {
    @FormUrlEncoded
    @POST("")
    void getFeeds(String area, String city, String date, String booking, String tenant, Callback<JSONResponse> callBack);

    @FormUrlEncoded
    @POST("/PropertyMaster.php")
    Observable<ArrayList<PropertyItem> > getProperty (@Field("City") String city);

    @POST("/CityMaster.php")
    Observable<ArrayList<CityArrayItem> > getCity ();
}
