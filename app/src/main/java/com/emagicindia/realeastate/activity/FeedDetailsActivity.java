package com.emagicindia.realeastate.activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

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
import com.nineoldandroids.view.ViewPropertyAnimator;

import java.util.ArrayList;

/**
 * Created by dell on 23/08/2016.
 */
public class FeedDetailsActivity extends AppCompatActivity implements ObservableScrollViewCallbacks {
    private static final String TAG = FeedDetailsActivity.class.getSimpleName();
    private Context mContext;

//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_feed_details);
//        mContext=this;
//
//        SquareImageView squareImageView = (SquareImageView)findViewById(R.id.image);
//        Intent i = this.getIntent();
//        ArrayList<PropertyItem> propertyItem = i.getParcelableArrayListExtra(AppConfig.KEYPROPERTYITEMS);
//        int position = i.getIntExtra(AppConfig.KEYPOSITION,0);
//
//        String image = AppConfig.BASE_URL+propertyItem.get(position).getImagePath();
//        System.out.println(image);
//        Glide.with(mContext)
//                .load(image)
//                .placeholder(R.drawable.ic_launcher)
//                .crossFade()
//                .centerCrop()
//                .into(squareImageView);
//        Log.i(TAG,propertyItem.get(position).getPropertyName());
//    }
//}
        private static final float MAX_TEXT_SCALE_DELTA = 0.3f;

        private View mImageView;
        private View mOverlayView;
        private ObservableScrollView mScrollView;
        private TextView mTitleView;
        private View mFab;
        private int mActionBarSize;
        private int mFlexibleSpaceShowFabOffset;
        private int mFlexibleSpaceImageHeight;
        private int mFabMargin;
        private boolean mFabIsShown;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_flexiblespacewithimagescrollview);

            mFlexibleSpaceImageHeight = getResources().getDimensionPixelSize(R.dimen.flexible_space_image_height);
            mFlexibleSpaceShowFabOffset = getResources().getDimensionPixelSize(R.dimen.flexible_space_show_fab_offset);
            mActionBarSize = getActionBarSize();

            mImageView = findViewById(R.id.image);
            mOverlayView = findViewById(R.id.overlay);
            mScrollView = (ObservableScrollView) findViewById(R.id.scroll);
            mScrollView.setScrollViewCallbacks(this);
            mTitleView = (TextView) findViewById(R.id.title);
            mTitleView.setText(getTitle());
            setTitle(null);
            mFab = findViewById(R.id.fab);
            mFab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(FeedDetailsActivity.this, "FAB is clicked", Toast.LENGTH_SHORT).show();
                }
            });
            mFabMargin = getResources().getDimensionPixelSize(R.dimen.margin_standard);
            ViewHelper.setScaleX(mFab, 0);
            ViewHelper.setScaleY(mFab, 0);

            ScrollUtils.addOnGlobalLayoutListener(mScrollView, new Runnable() {
                @Override
                public void run() {
                    mScrollView.scrollTo(0, mFlexibleSpaceImageHeight - mActionBarSize);

                    // If you'd like to start from scrollY == 0, don't write like this:
                    //mScrollView.scrollTo(0, 0);
                    // The initial scrollY is 0, so it won't invoke onScrollChanged().
                    // To do this, use the following:
                    //onScrollChanged(0, false, false);

                    // You can also achieve it with the following codes.
                    // This causes scroll change from 1 to 0.
                    //mScrollView.scrollTo(0, 1);
                    //mScrollView.scrollTo(0, 0);
                }
            });
        }

    protected int getActionBarSize() {
        TypedValue typedValue = new TypedValue();
        int[] textSizeAttr = new int[]{R.attr.actionBarSize};
        int indexOfAttrTextSize = 0;
        TypedArray a = obtainStyledAttributes(typedValue.data, textSizeAttr);
        int actionBarSize = a.getDimensionPixelSize(indexOfAttrTextSize, -1);
        a.recycle();
        return actionBarSize;
    }

    @Override
        public void onScrollChanged(int scrollY, boolean firstScroll, boolean dragging) {
            // Translate overlay and image
            float flexibleRange = mFlexibleSpaceImageHeight - mActionBarSize;
            int minOverlayTransitionY = mActionBarSize - mOverlayView.getHeight();
            ViewHelper.setTranslationY(mOverlayView, ScrollUtils.getFloat(-scrollY, minOverlayTransitionY, 0));
            ViewHelper.setTranslationY(mImageView, ScrollUtils.getFloat(-scrollY / 2, minOverlayTransitionY, 0));

            // Change alpha of overlay
            ViewHelper.setAlpha(mOverlayView, ScrollUtils.getFloat((float) scrollY / flexibleRange, 0, 1));

            // Scale title text
            float scale = 1 + ScrollUtils.getFloat((flexibleRange - scrollY) / flexibleRange, 0, MAX_TEXT_SCALE_DELTA);
            ViewHelper.setPivotX(mTitleView, 0);
            ViewHelper.setPivotY(mTitleView, 0);
            ViewHelper.setScaleX(mTitleView, scale);
            ViewHelper.setScaleY(mTitleView, scale);

            // Translate title text
            int maxTitleTranslationY = (int) (mFlexibleSpaceImageHeight - mTitleView.getHeight() * scale);
            int titleTranslationY = maxTitleTranslationY - scrollY;
            ViewHelper.setTranslationY(mTitleView, titleTranslationY);

            // Translate FAB
            int maxFabTranslationY = mFlexibleSpaceImageHeight - mFab.getHeight() / 2;
            float fabTranslationY = ScrollUtils.getFloat(
                    -scrollY + mFlexibleSpaceImageHeight - mFab.getHeight() / 2,
                    mActionBarSize - mFab.getHeight() / 2,
                    maxFabTranslationY);
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
                // On pre-honeycomb, ViewHelper.setTranslationX/Y does not set margin,
                // which causes FAB's OnClickListener not working.
                FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) mFab.getLayoutParams();
                lp.leftMargin = mOverlayView.getWidth() - mFabMargin - mFab.getWidth();
                lp.topMargin = (int) fabTranslationY;
                mFab.requestLayout();
            } else {
                ViewHelper.setTranslationX(mFab, mOverlayView.getWidth() - mFabMargin - mFab.getWidth());
                ViewHelper.setTranslationY(mFab, fabTranslationY);
            }

            // Show/hide FAB
            if (fabTranslationY < mFlexibleSpaceShowFabOffset) {
                hideFab();
            } else {
                showFab();
            }
        }

        @Override
        public void onDownMotionEvent() {
        }

        @Override
        public void onUpOrCancelMotionEvent(ScrollState scrollState) {
        }

    private void showFab() {
        if (!mFabIsShown) {
            ViewPropertyAnimator.animate(mFab).cancel();
            ViewPropertyAnimator.animate(mFab).scaleX(1).scaleY(1).setDuration(200).start();
            mFabIsShown = true;
        }
    }

    private void hideFab() {
        if (mFabIsShown) {
            ViewPropertyAnimator.animate(mFab).cancel();
            ViewPropertyAnimator.animate(mFab).scaleX(0).scaleY(0).setDuration(200).start();
            mFabIsShown = false;
        }
    }
}
