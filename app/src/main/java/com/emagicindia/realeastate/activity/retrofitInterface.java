package com.emagicindia.realeastate.activity;

import com.emagicindia.realeastate.model.CityArrayItem;
import com.emagicindia.realeastate.model.ForgetPasswordResponse;
import com.emagicindia.realeastate.model.JSONResponse;
import com.emagicindia.realeastate.model.LoginResponse;
import com.emagicindia.realeastate.model.PropertyItem;
import com.emagicindia.realeastate.model.RegistrationResponse;

import java.util.ArrayList;

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

    @FormUrlEncoded
    @POST("/Prop_Login.php")
    Observable<LoginResponse>  getUser(@Field("Email_Id") String username, @Field("Password") String password,
                                       @Field("Login_Type") String loginType);

    @FormUrlEncoded
    @POST("/Prop_Registration.php")
    Observable<RegistrationResponse>  getRegistrationDetails(@Field("First_Name") String firstname, @Field("Last_Name") String lastname,
                                                                        @Field("Email_Id") String email, @Field("Mobile_No") String mobile,
                                                                        @Field("Password") String password, @Field("Login_Type") String loginType,
                                                                        @Field("Lati") String lat, @Field("Longi") String lang);

    @FormUrlEncoded
    @POST("/Prop_Forget_Password.php")
    Observable<ForgetPasswordResponse>  setForgetPassword(@Field("Email_Id") String username);


    @POST("/CityMaster.php")
    Observable<ArrayList<CityArrayItem> > getCity ();
}
