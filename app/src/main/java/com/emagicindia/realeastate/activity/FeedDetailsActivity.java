package com.emagicindia.realeastate.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.emagicindia.realeastate.R;
import com.emagicindia.realeastate.model.PropertyItem;
import com.emagicindia.realeastate.utils.AppConfig;
import com.emagicindia.realeastate.utils.SquareImageView;

import java.util.ArrayList;

/**
 * Created by dell on 23/08/2016.
 */
public class FeedDetailsActivity extends AppCompatActivity {
    private static final String TAG = FeedDetailsActivity.class.getSimpleName();
    private Context mContext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_details);
        mContext=this;

        SquareImageView squareImageView = (SquareImageView)findViewById(R.id.image);
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
                .into(squareImageView);
        Log.i(TAG,propertyItem.get(position).getPropertyName());
    }
}
