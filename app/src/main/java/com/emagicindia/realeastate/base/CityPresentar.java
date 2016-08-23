package com.emagicindia.realeastate.base;

import com.emagicindia.realeastate.model.CityArrayItem;
import com.emagicindia.realeastate.model.PropertyItem;

import java.util.ArrayList;

import rx.Observer;

/**
 * Created by dell on 20/08/2016.
 */

public class CityPresentar extends BasePresentar implements Observer<ArrayList<CityArrayItem>> {

    private CityViewInterface cityViewInterface;

    public CityPresentar(CityViewInterface cityViewInterface) {
        this.cityViewInterface =cityViewInterface;
    }

    @Override
    public void onCompleted() {
        cityViewInterface.onCompleted();

    }

    @Override
    public void onError(Throwable e) {
        cityViewInterface.onError(e.getMessage());

    }

    @Override
    public void onNext(ArrayList<CityArrayItem> cityArrayItems) {
        cityViewInterface.onCityFetch(cityArrayItems);

    }

    public void fetchExample() {
        unSubscribeAll();
        subscribe(cityViewInterface.getCityItems(), CityPresentar.this);
    }
}