package com.emagicindia.realeastate.activity;

import android.app.Application;

import com.emagicindia.realeastate.dependancy.DaggerNetworkComponent;
import com.emagicindia.realeastate.dependancy.DaggerPropertyComponent;
import com.emagicindia.realeastate.dependancy.NetworkComponent;
import com.emagicindia.realeastate.dependancy.NetworkModule;
import com.emagicindia.realeastate.dependancy.PropertyComponent;
import com.emagicindia.realeastate.utils.AppConfig;

/**
 * Created by dell on 19/08/2016.
 */

public class MyApplication extends Application{

    private PropertyComponent propertyComponent;
    @Override
    public void onCreate() {
        resolveDependacy();
        super.onCreate();

    }

    public void resolveDependacy(){
        propertyComponent = DaggerPropertyComponent.builder().networkComponent(getNetworkComponent()).build();
    }

    public NetworkComponent getNetworkComponent(){
        return DaggerNetworkComponent.builder().networkModule(new NetworkModule(AppConfig.BASE_URL)).build();
    }

    public PropertyComponent getPropertyComponent(){
        return propertyComponent;
    }
}
