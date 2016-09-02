package com.emagicindia.realeastate.dependancy;

import com.emagicindia.realeastate.MainActivity;
import com.emagicindia.realeastate.activity.FeedActivity;
import com.emagicindia.realeastate.activity.LoginActivity;
import com.emagicindia.realeastate.activity.RegistrationActivity;

import dagger.Component;

/**
 * Created by dell on 19/08/2016.
 */
@PropertyScope
@Component(modules = PropertyModule.class,dependencies = NetworkComponent.class)
public interface PropertyComponent {
    void inject (FeedActivity feedActivity);
    void inject (MainActivity mainActivity);
    void inject (LoginActivity loginActivity);
    void inject (RegistrationActivity registrationActivity);
}
