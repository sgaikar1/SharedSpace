package com.emagicindia.realeastate.dependancy;

import com.emagicindia.realeastate.activity.retrofitInterface;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * Created by dell on 19/08/2016.
 */
@Module
public class PropertyModule {
    @Provides
    @PropertyScope
    retrofitInterface getProperty(Retrofit retrofit){
        return retrofit.create(retrofitInterface.class);
    }
}
