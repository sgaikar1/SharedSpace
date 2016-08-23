package com.emagicindia.realeastate.base;

import com.emagicindia.realeastate.model.CityArrayItem;
import com.emagicindia.realeastate.model.PropertyItem;

import java.util.ArrayList;

import rx.Observable;

/**
 * Created by dell on 19/08/2016.
 */

public interface CityViewInterface {

    void onCompleted();
    void onError(String message);
    void onCityFetch(ArrayList<CityArrayItem> cityItems);
    Observable<ArrayList<CityArrayItem>> getCityItems();
}