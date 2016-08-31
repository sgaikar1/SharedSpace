package com.emagicindia.realeastate.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.bumptech.glide.Glide;
import com.emagicindia.realeastate.R;
import com.emagicindia.realeastate.model.PropertyItem;
import com.emagicindia.realeastate.utils.AppConfig;
import com.emagicindia.realeastate.utils.SquareImageView;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollView;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollViewCallbacks;
import com.github.ksoichiro.android.observablescrollview.ScrollState;
import com.github.ksoichiro.android.observablescrollview.ScrollUtils;
import com.nineoldandroids.view.ViewHelper;

import java.util.ArrayList;

/**
 * Created by dell on 23/08/2016.
 */

/**
 * Created by dell on 31/08/2016.
 */

public class FeedDetailsActivity  extends AppCompatActivity implements ObservableScrollViewCallbacks {
    private SquareImageView mImageView;
    private View mToolbarView;
    private ObservableScrollView mScrollView;
    private int mParallaxImageHeight;
    private static final String TAG = FeedDetailsActivity.class.getSimpleName();
    private Context mContext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_details);
        mContext=this;

        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));

        mImageView = (SquareImageView)findViewById(R.id.image);

        Intent i = this.getIntent();
        ArrayList<PropertyItem> propertyItem = i.getParcelableArrayListExtra(AppConfig.KEYPROPERTYITEMS);
        int position = i.getIntExtra(AppConfig.KEYPOSITION,0);

        String image = AppConfig.BASE_URL+propertyItem.get(position).getImagePath();
        System.out.println(image);
        Glide.with(mContext)
                .load(image)
                .placeholder(R.drawable.ic_launcher)
                .crossFade()
                .centerCrop()
                .into(mImageView);
        Log.i(TAG,propertyItem.get(position).getPropertyName());
        mToolbarView = findViewById(R.id.toolbar);
        mToolbarView.setBackgroundColor(ScrollUtils.getColorWithAlpha(0, getResources().getColor(R.color.colorPrimary)));

        mScrollView = (ObservableScrollView) findViewById(R.id.scroll);
        mScrollView.setScrollViewCallbacks(this);

        mParallaxImageHeight = getResources().getDimensionPixelSize(R.dimen.parallax_image_height);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        onScrollChanged(mScrollView.getCurrentScrollY(), false, false);
    }

    @Override
    public void onScrollChanged(int scrollY, boolean firstScroll, boolean dragging) {
        int baseColor = getResources().getColor(R.color.colorPrimary);
        float alpha = Math.min(1, (float) scrollY / mParallaxImageHeight);
        mToolbarView.setBackgroundColor(ScrollUtils.getColorWithAlpha(alpha, baseColor));
        ViewHelper.setTranslationY(mImageView, scrollY / 2);
    }

    @Override
    public void onDownMotionEvent() {
    }

    @Override
    public void onUpOrCancelMotionEvent(ScrollState scrollState) {
    }


}