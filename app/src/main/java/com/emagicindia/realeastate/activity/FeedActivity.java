package com.emagicindia.realeastate.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.emagic.libraries.layouts.SweetAlertDialog;
import com.emagic.libraries.widgets.FloatingActionMenu;
import com.emagicindia.realeastate.Fragment.FilterFragment;
import com.emagicindia.realeastate.Fragment.HorizontalFeedFragment;
import com.emagicindia.realeastate.R;
import com.emagicindia.realeastate.adapter.FeedsAdapter;
import com.emagicindia.realeastate.base.PropertyPresentar;
import com.emagicindia.realeastate.base.PropertyViewInterface;
import com.emagicindia.realeastate.model.PropertyItem;
import com.emagicindia.realeastate.utils.ClickableViewPager;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

import javax.inject.Inject;

import rx.Observable;

import static com.emagicindia.realeastate.activity.SelectionProcessActivity.BOOKING_TYPE;
import static com.emagicindia.realeastate.activity.SelectionProcessActivity.LOCATION_AREA;
import static com.emagicindia.realeastate.activity.SelectionProcessActivity.LOCATION_CITY;
import static com.emagicindia.realeastate.activity.SelectionProcessActivity.MOVEIN_DATE;
import static com.emagicindia.realeastate.activity.SelectionProcessActivity.TENANT_TYPE;
import static com.emagicindia.realeastate.utils.AppConfig.KEYPOSITION;
import static com.emagicindia.realeastate.utils.AppConfig.KEYPROPERTYITEMS;

/**
 * Created by dell on 8/12/2016.
 */
public class FeedActivity extends AppCompatActivity implements PropertyViewInterface, OnMapReadyCallback,FeedsAdapter.OnItemClick {
    private static final String TAG = FeedActivity.class.getSimpleName();
    private FeedsAdapter adapter;
    private ArrayList<String> data;
    private String viewtype;
    private Menu menu;
    private Toolbar mToolbar;
    private String city;
    private PropertyPresentar propertyPresenter;
    private Context mContext;

    private PagerAdapter pagerAdapter;
    protected int CurrentPosition;


    @Inject
    retrofitInterface retrofitInterface;

    private ArrayList<PropertyItem> propertyItems;
    private RecyclerView recyclerView;
    private RelativeLayout rlMapView;
    private SweetAlertDialog ad;
    private ClickableViewPager viewPager;
    private GoogleMap googleMap;
    private FloatingActionButton fabFilter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);

        resolveDependancy();

        ((MyApplication) getApplicationContext()).getPropertyComponent().inject(FeedActivity.this);

        propertyPresenter = new PropertyPresentar(this);
        propertyPresenter.onCreate();

        mContext = this;
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        TextView mTitle = (TextView) mToolbar.findViewById(R.id.tv_toolbar_title);
        rlMapView = (RelativeLayout) findViewById(R.id.rl_map_view);
        recyclerView = (RecyclerView) findViewById(R.id.rv_feeds);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mContext);
        recyclerView.setLayoutManager(layoutManager);
        fabFilter = (FloatingActionButton)findViewById(R.id.fab_filter);

        viewPager = (ClickableViewPager) findViewById(R.id.rv_horizontal_feeds);

        try {
            // Loading map
            initilizeMap();

        } catch (Exception e) {
            e.printStackTrace();
        }

        fabFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FeedActivity.this,FilterActivity.class);
                startActivity(intent);
            }
        });

        viewPager.setOnViewPagerClickListener(new ClickableViewPager.OnClickListener() {
            @Override
            public void onViewPagerClick(ViewPager viewPager) {
                int position = viewPager.getCurrentItem();

                openDetailedActivity(position);
            }
        });

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
//                mAdapter.notifyItemChanged(mAdapter.selected_position);
//                mAdapter.selected_position = position;
//                mAdapter.notifyItemChanged(mAdapter.selected_position);
//                recyclerview.smoothScrollToPosition(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        data = new ArrayList<String>();

        Intent intent = getIntent();
        String area = intent.getStringExtra(LOCATION_AREA);
        city = intent.getStringExtra(LOCATION_CITY);
        String date = intent.getStringExtra(MOVEIN_DATE);
        String tenant = intent.getStringExtra(TENANT_TYPE);
        String booking = intent.getStringExtra(BOOKING_TYPE);

        ad = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        ad.setCancelable(false);
        ad.setTitleText("Loading");
        ad.getProgressHelper().setBarWidth(3);
        ad.show();
        propertyPresenter.fetchExample(city);

        // set the toolbar title
        getSupportActionBar().setTitle(city);
        mTitle.setText(city);
        System.out.println(area + " " + city + " " + date + " " + booking + " " + tenant);

    }

    public void resolveDependancy() {
        ((MyApplication) getApplication()).getPropertyComponent().inject(FeedActivity.this);
    }

    private void initilizeMap() {
        MapFragment mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap map) {
        //DO WHATEVER YOU WANT WITH GOOGLEMAP
        map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        map.setTrafficEnabled(true);
        map.setIndoorEnabled(true);
        map.setBuildingsEnabled(true);
        map.getUiSettings().setZoomControlsEnabled(true);

        CameraPosition cameraPosition = new CameraPosition.Builder().target(new LatLng(19.021324,
                72.842418)).zoom(12.0f).build();
        CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition);
        map.moveCamera(cameraUpdate);
        this.googleMap = map;

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.menu = menu;
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_feed, menu);
        if (viewtype == null || viewtype.equals("")) {
            viewtype = "MAP";
        }
        setOptionTitle(R.id.action_viewtype, viewtype);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_viewtype) {
            if (viewtype.equals("FEED")) {
                viewtype = "MAP";
                recyclerView.setVisibility(View.VISIBLE);
                rlMapView.setVisibility(View.GONE);
                setOptionTitle(R.id.action_viewtype, viewtype);
            } else {
                viewtype = "FEED";
                recyclerView.setVisibility(View.GONE);
                rlMapView.setVisibility(View.VISIBLE);
                setOptionTitle(R.id.action_viewtype, viewtype);
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void setOptionTitle(int id, String viewtype) {
        if (menu != null) {
            MenuItem item = menu.findItem(id);
            if(viewtype.equals("FEED")) {
                item.setIcon(getResources().getDrawable(R.drawable.picture));
            }else if(viewtype.equals("MAP")){
                item.setIcon(getResources().getDrawable(R.drawable.marker));
            }
        }
    }

    @Override
    public void onCompleted() {

    }

    @Override
    public void onError(String message) {
        Log.e(TAG, message);
        ad.dismiss();
    }

    @Override
    public void onPropertyFetch(ArrayList<PropertyItem> propertyItems) {
        ad.dismiss();
        this.propertyItems = propertyItems;
        adapter = new FeedsAdapter(mContext, propertyItems);
        adapter.registerEvent(FeedActivity.this);
        recyclerView.setAdapter(adapter);

        pagerAdapter = new PagerAdapter(getSupportFragmentManager());
        pagerAdapter.setArrayList(propertyItems);
        viewPager.setAdapter(pagerAdapter);


        ArrayList<Double> LatArray = new ArrayList<Double>();
        ArrayList<Double> LongArray = new ArrayList<Double>();

        LatArray.add(19.021324);
        LongArray.add(72.842418);
        LatArray.add(19.015128);
        LongArray.add(72.858064);
        LatArray.add(19.002157);
        LongArray.add(72.841579);
        LatArray.add(19.049708);
        LongArray.add(72.875917);
        LatArray.add(19.026875);
        LongArray.add(72.855335);

        for (int i = 0; i < LatArray.size(); i++) {

//            createMarker(propertyItems.get(i).getLatitude(), propertyItems.get(i).getLongitude(), propertyItems.get(i).getTitle(), propertyItems.get(i).getSnippet(), propertyItems.get(i).getIconResID());
            createMarker(LatArray.get(i), LongArray.get(i));
        }

    }

    @Override
    public Observable<ArrayList<PropertyItem>> getPropertyItems(String city) {
        return retrofitInterface.getProperty("THANE");
    }

    @Override
    public void onResume() {
        super.onResume();
        propertyPresenter.onResume();
        initilizeMap();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        propertyPresenter.onDestroy();
    }

    @Override
    public void clickresponce(int position) {
        openDetailedActivity(position);
    }

    public class PagerAdapter extends FragmentPagerAdapter {
        ArrayList<PropertyItem> propertyList = new ArrayList<>();

        public void setArrayList(ArrayList<PropertyItem> propertyList) {
            this.propertyList = propertyList;
            notifyDataSetChanged();
        }

        public PagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            PropertyItem item = propertyList.get(position);
            CurrentPosition = position;
            return HorizontalFeedFragment.newInstance(item);
        }

        @Override
        public int getCount() {
            return propertyList.size();
        }
    }

    protected Marker createMarker(double latitude, double longitude) {

        return googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(latitude, longitude))
                .anchor(0.5f, 0.5f));
    }

    private void openDetailedActivity(int position){
        Intent intent = new Intent(FeedActivity.this,FeedDetailsActivity.class);
        intent.putExtra(KEYPROPERTYITEMS,(ArrayList<PropertyItem>) propertyItems);
        intent.putExtra(KEYPOSITION,position);
        startActivity(intent);
    }
}
