package com.emagicindia.realeastate.activity;

import android.app.Activity;

import com.emagicindia.realeastate.R;
import com.stephentuso.welcome.WelcomeScreenBuilder;
import com.stephentuso.welcome.ui.WelcomeActivity;
import com.stephentuso.welcome.util.WelcomeScreenConfiguration;

/**
 * Created by dell on 19/08/2016.
 */
public class HowItWorksActivity extends WelcomeActivity {
    @Override
    protected WelcomeScreenConfiguration configuration() {
        return new WelcomeScreenBuilder(this)
                .theme(R.style.WelcomeScreenTheme_Light)
                .defaultBackgroundColor(R.color.ColorPrimary)
                .titlePage(R.mipmap.ic_launcher, "Title")
                .basicPage(R.mipmap.ic_launcher, "Header", "More text.", R.color.colorAccent)
                .basicPage(R.mipmap.ic_launcher, "Lorem ipsum", "dolor sit amet.")
                .swipeToDismiss(true)
                .build();
    }
}
