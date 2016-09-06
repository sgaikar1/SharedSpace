package com.emagicindia.realeastate;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.emagicindia.realeastate.Database.DatabaseHelper;
import com.emagicindia.realeastate.Fragment.FragmentDrawer;
import com.emagicindia.realeastate.Fragment.HomeFragment;
import com.emagicindia.realeastate.activity.AboutActivity;
import com.emagicindia.realeastate.activity.CityActivity;
import com.emagicindia.realeastate.activity.FeedActivity;
import com.emagicindia.realeastate.activity.FeedbackActivity;
import com.emagicindia.realeastate.activity.HowItWorksActivity;
import com.emagicindia.realeastate.activity.ListHouseActivity;
import com.emagicindia.realeastate.activity.MyApplication;
import com.emagicindia.realeastate.activity.PolicyActivity;
import com.emagicindia.realeastate.activity.retrofitInterface;
import com.emagicindia.realeastate.base.CityPresentar;
import com.emagicindia.realeastate.base.CityViewInterface;
import com.emagicindia.realeastate.model.CityArrayItem;

import java.util.ArrayList;

import javax.inject.Inject;

import rx.Observable;

import static com.emagicindia.realeastate.activity.SelectionProcessActivity.LOCATION_CITY;
import static com.emagicindia.realeastate.utils.AppConfig.KEYCITY;

public class MainActivity extends AppCompatActivity implements FragmentDrawer.FragmentDrawerListener, CityViewInterface {

    private Toolbar mToolbar;
    private FragmentDrawer drawerFragment;
    private Context mContext;
    private Menu menu;
    public static String citytext;
    private static final String TAG = MainActivity.class.getSimpleName();
    private CityPresentar cityPresentar;

    @Inject
    retrofitInterface retrofitInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        resolveDependancy();

        ((MyApplication)getApplicationContext()).getPropertyComponent().inject(MainActivity.this);

        cityPresentar = new CityPresentar(this);
        cityPresentar.onCreate();
        cityPresentar.fetchExample();

        mContext=this;
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        TextView mTitle = (TextView) mToolbar.findViewById(R.id.tv_toolbar_title);
        mTitle.setText("");
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        drawerFragment = (FragmentDrawer)
                getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        drawerFragment.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), mToolbar);
        drawerFragment.setDrawerListener(this);

        displayView(0);

    }

    public  void resolveDependancy(){
        ((MyApplication)getApplication()).getPropertyComponent().inject(MainActivity.this);
    }

    @Override
    public void onDrawerItemSelected(View view, int position) {
        displayView(position);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.menu=menu;
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        SharedPreferences saved_values = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        citytext = saved_values.getString(KEYCITY, "");
        if(citytext==null||citytext.equals("")){
            saved_values = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            SharedPreferences.Editor editor=saved_values.edit();
            editor.putString(KEYCITY,"Mumbai");
            editor.commit();
            citytext="Mumbai";
        }
        setOptionTitle(R.id.action_city, citytext);

        final MenuItem item = menu.findItem(R.id.action_city);
        item.getActionView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cityIntent = new Intent(MainActivity.this,CityActivity.class);
                startActivityForResult(cityIntent, 1);
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_city) {
            Intent cityIntent = new Intent(MainActivity.this,CityActivity.class);
            startActivityForResult(cityIntent, 1);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void displayView(int position) {
        Fragment fragment = null;
//        String title = getString(R.string.app_name);
        switch (position) {
            case 0:
                fragment = new HomeFragment();
//                title = getString(R.string.title_home);
                break;
            case 1:
                Intent feedActivityIntent = new Intent(MainActivity.this,FeedActivity.class);
                feedActivityIntent.putExtra(LOCATION_CITY,citytext);
                System.out.println(TAG+" : "+citytext);
                startActivity(feedActivityIntent);
                break;
            case 2:
                Intent welcomeIntent = new Intent(MainActivity.this,HowItWorksActivity.class);
                startActivity(welcomeIntent);
                break;
            case 3:

                break;
            case 4:
                Intent listhouseIntent = new Intent(MainActivity.this, ListHouseActivity.class);
                startActivity(listhouseIntent);
                break;
            case 5:
//                Intent welcomeIntent = new Intent(MainActivity.this,HowItWorksActivity.class);
//                startActivity(welcomeIntent);
                break;
            case 6:
//                fragment = new HomeFragment();
////                title = getString(R.string.title_home);
                break;
            case 7:
                Intent feedbackIntent = new Intent(MainActivity.this,FeedbackActivity.class);
                startActivity(feedbackIntent);
                break;
            case 8:
                Intent policyIntent = new Intent(MainActivity.this,PolicyActivity.class);
                startActivity(policyIntent);
                break;
            case 9:
                Intent aboutIntent = new Intent(MainActivity.this,AboutActivity.class);
                startActivity(aboutIntent);
                break;
            default:
                break;
        }

        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_body, fragment);
            fragmentTransaction.commit();

            // set the toolbar title
            getSupportActionBar().setTitle("");
        }
    }


    private void setOptionTitle(int id, String cityname)
    {
        if(menu!=null) {
            MenuItem item = menu.findItem(id);
//            item.setTitle(cityname);
            TextView textView = (TextView) item.getActionView().findViewById(R.id.tv_row_menu);
            textView.setText(cityname);
        }
    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if(resultCode == 1){
                citytext=data.getStringExtra("city_name");
                SharedPreferences saved_values = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor editor=saved_values.edit();
                editor.putString(KEYCITY,citytext);
                editor.commit();
                System.out.print("Got city " + citytext);
                setOptionTitle(R.id.action_city, citytext);
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences saved_values = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        citytext = saved_values.getString(KEYCITY, "");
        setOptionTitle(R.id.action_city, citytext);
    }


    @Override
    public void onCompleted() {

    }

    @Override
    public void onError(String message) {
        Log.e(TAG,message);
    }

    @Override
    public void onCityFetch(ArrayList<CityArrayItem> cityItems) {
//        Toast.makeText(mContext,cityItems.size()+": "+TAG,Toast.LENGTH_SHORT).show();
        DatabaseHelper db = new DatabaseHelper(mContext);
        int count = db.getCityTableCount();
        if(count!=cityItems.size()) {
            db.addCity(cityItems);
        }
    }

    @Override
    public Observable<ArrayList<CityArrayItem>> getCityItems() {
        return retrofitInterface.getCity();
    }



}

