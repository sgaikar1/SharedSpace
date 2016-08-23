package com.emagicindia.realeastate.dependancy;

import com.emagicindia.realeastate.MainActivity;
import com.emagicindia.realeastate.activity.FeedActivity;

import dagger.Component;

/**
 * Created by dell on 19/08/2016.
 */
@PropertyScope
@Component(modules = PropertyModule.class,dependencies = NetworkComponent.class)
public interface PropertyComponent {
    void inject (FeedActivity feedActivity);
    void inject (MainActivity mainActivity);
}
