package com.emagicindia.realeastate.base;

import com.emagicindia.realeastate.model.PropertyItem;

import java.util.ArrayList;

import rx.Observable;

/**
 * Created by dell on 19/08/2016.
 */

public interface PropertyViewInterface {

    void onCompleted();
    void onError(String message);
    void onPropertyFetch(ArrayList<PropertyItem> propertyItems);
    Observable<ArrayList<PropertyItem>> getPropertyItems(String city);
}