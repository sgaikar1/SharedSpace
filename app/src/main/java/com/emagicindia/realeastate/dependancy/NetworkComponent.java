package com.emagicindia.realeastate.dependancy;

import javax.inject.Singleton;

import dagger.Component;
import retrofit2.Retrofit;

/**
 * Created by dell on 19/08/2016.
 */
@Singleton
@Component(modules = NetworkModule.class)
public interface NetworkComponent {

    Retrofit retrofit();
}
