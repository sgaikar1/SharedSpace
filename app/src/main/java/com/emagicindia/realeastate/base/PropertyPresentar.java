package com.emagicindia.realeastate.base;

/**
 * Created by dell on 19/08/2016.
 */


import com.emagicindia.realeastate.dependancy.PropertyModule;
import com.emagicindia.realeastate.model.PropertyItem;

import java.util.ArrayList;

import rx.Observer;

/**
 * Created by mkodekar on 8/19/2016.
 */

public class PropertyPresentar extends BasePresentar implements Observer<ArrayList<PropertyItem>> {

    private PropertyViewInterface propertyViewInterface;

    public PropertyPresentar(PropertyViewInterface propertyViewInterface) {
        this.propertyViewInterface = propertyViewInterface;
    }

    @Override
    public void onCompleted() {
        propertyViewInterface.onCompleted();

    }

    @Override
    public void onError(Throwable e) {
        propertyViewInterface.onError(e.getMessage());

    }

    @Override
    public void onNext(ArrayList<PropertyItem> propertyItems) {
        propertyViewInterface.onPropertyFetch(propertyItems);

    }

    public void fetchExample(String city) {
        unSubscribeAll();
        subscribe(propertyViewInterface.getPropertyItems(city), PropertyPresentar.this);
    }
}