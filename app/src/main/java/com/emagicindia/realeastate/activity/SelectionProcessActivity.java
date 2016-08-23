package com.emagicindia.realeastate.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Geocoder;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.emagicindia.realeastate.R;
import com.emagicindia.realeastate.adapter.PlaceAutoCompleteAdapter;
import com.emagicindia.realeastate.model.AdressParameters;
import com.emagicindia.realeastate.utils.AppUtils;
import com.vistrav.ask.Ask;
import com.vistrav.ask.annotations.AskDenied;
import com.vistrav.ask.annotations.AskGranted;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import static com.emagicindia.realeastate.MainActivity.citytext;
import static com.emagicindia.realeastate.utils.AppConfig.KEYCITY;

public class SelectionProcessActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{

    public static final String LOCATION_AREA = "area";
    public static final String LOCATION_CITY = "city";
    public static final String TENANT_TYPE = "tenant_type";
    public static final String BOOKING_TYPE = "booking_type";
    public static final String MOVEIN_DATE = "movein_date";
    Context mContext;
    private TextView tvCalender;
    private RadioGroup radioGroup1;
    private RadioGroup radioGroup2;
    private Menu menu;
    private Toolbar mToolbar;
    private String[] cityArray;
    private Button btnSearch;
    private String selectedDate;
    private String booking_type;
    private String tenantType;
    private static final String TAG = SelectionProcessActivity.class.getSimpleName();
    List<AdressParameters> listofAdd = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selection_process);
        mContext = this;

        mToolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("");

        TextView tvLocation = (TextView)findViewById(R.id.tv_location);
        tvCalender = (TextView)findViewById(R.id.tv_calender);
        btnSearch = (Button)findViewById(R.id.btn_search);

        mAdapter=new PlaceAutoCompleteAdapter(mContext, R.layout.row_autocomplete_place_api);
        final AutoCompleteTextView autocompleteView = (AutoCompleteTextView) findViewById(R.id.actv_place_api);
        autocompleteView.setAdapter(mAdapter);
        autocompleteView.setThreshold(1);

        cityArray = getResources().getStringArray(R.array.array_city);

        Ask.on(this)
                .forPermissions(Manifest.permission.ACCESS_FINE_LOCATION)
                .withRationales("Location permission needed to improve your property search.")
                .go();

        autocompleteView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Get data associated with the specified position
                // in the list (AdapterView)
                String description = (String) parent.getItemAtPosition(position);
                Toast.makeText(mContext, description, Toast.LENGTH_SHORT).show();
            }
        });

        final Geocoder geocoder = new Geocoder(this, Locale.getDefault());

        tvLocation.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                int hasFineLocationPermission = 0;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    hasFineLocationPermission = checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION);
                    if (hasFineLocationPermission != PackageManager.PERMISSION_GRANTED) {
                        AppUtils.showNoLocationPermissionDialogue(mContext);
                        return;
                    }
                }

//                List<String>addressParameters =  AppUtils.getCityAndSubLocality(mContext,geocoder);

                listofAdd = AppUtils.getCityAndSubLocality(mContext,geocoder);
                if (listofAdd != null) {
                    for (int i = 0; i < listofAdd.size(); i++) {
                        AdressParameters adressParameters = listofAdd.get(i);
                        String city = adressParameters.getCity();
                        String sublocal = adressParameters.getSubLocality();
                        System.out.println(TAG + "got city" + city);
                        autocompleteView.setText(sublocal);
                        citytext=city;
                        updateCityText();
                        setOptionTitle(R.id.action_city, citytext);
                        adressParameters.setCity("");
                        adressParameters.setSubLocality("");
                    }
                    listofAdd.clear();
                }

                /*List<String>addressParameters = new ArrayList<String>(2);
                addressParameters.add(adressParameters.getCity());
                addressParameters.add(adressParameters.getSubLocality());*/
               /* if(addressParameters!=null) {
                    addressParameters.get(0);
                    addressParameters.get(1);
                    for (int i = 0; i < cityArray.length; i++) {
                        if (cityArray[i].equalsIgnoreCase(addressParameters.get(0))) {
                            citytext = cityArray[i];
                            updateCityText();
                            System.out.print("Got city " + citytext);
                            setOptionTitle(R.id.action_city, citytext);
                            autocompleteView.setText(addressParameters.get(1));
                            break;
                        } else if (i == cityArray.length - 1) {
                            Toast.makeText(mContext, "We are not present in " + addressParameters.get(0) + ", Try other options", Toast.LENGTH_SHORT).show();
                        }

                    }
                }else{
                   System.out.println("Location not available in selection process activity");
                }*/
            }
        });

        tvCalender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar now = Calendar.getInstance();
                DatePickerDialog dpd = DatePickerDialog.newInstance(
                        SelectionProcessActivity.this,
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH)
                );
                now.add(Calendar.DATE,2);
                dpd.setMinDate(now);
                Calendar maxdate = Calendar.getInstance();
                maxdate.add(Calendar.DATE,47);
                dpd.setMaxDate(maxdate);
                dpd.setTitle("Set your move in date");
                dpd.setThemeDark(false);
                dpd.setAccentColor("#3F51B5");
//                dpd.setAccentColor(R.color.colorPrimary);
                dpd.show(getFragmentManager(), "Datepickerdialog");
            }
        });

        radioGroup1 = (RadioGroup) findViewById(R.id.radioGroup1);

        // Checked change Listener for RadioGroup 1
        radioGroup1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId)
            {
                switch (checkedId)
                {
                    case R.id.radioGirl:
                        findViewById(R.id.radioBed).setEnabled(true);
                        findViewById(R.id.radioRoom).setEnabled(true);
                        radioGroup2.check(R.id.radioBed);
//                        Toast.makeText(getApplicationContext(), "GIRL", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.radioBoy:
                        findViewById(R.id.radioBed).setEnabled(true);
                        findViewById(R.id.radioRoom).setEnabled(true);
                        radioGroup2.check(R.id.radioBed);
//                        Toast.makeText(getApplicationContext(), "BOY", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.radioFamily:
                        findViewById(R.id.radioBed).setEnabled(false);
                        findViewById(R.id.radioRoom).setEnabled(false);
                        radioGroup2.check(R.id.radioFlat);
//                        Toast.makeText(getApplicationContext(), "FAMILY", Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        break;
                }
            }
        });


        autocompleteView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                final String value = s.toString();

                // Remove all callbacks and messages
                mThreadHandler.removeCallbacksAndMessages(null);

                // Now add a new one
                mThreadHandler.postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        // Background thread
                        mAdapter.outer=mAdapter.mPlaceAPI.autocomplete(value);
                        mAdapter.resultList = mAdapter.outer.get(0);
                        mAdapter.remainingList = mAdapter.outer.get(1);

                        // Footer
                        if (mAdapter.resultList.size() > 0)
                            mAdapter.resultList.add("footer");

                        // Post to Main Thread
                        mThreadHandler.sendEmptyMessage(1);
                    }
                }, 500);
            }

            @Override
            public void afterTextChanged(Editable s) {
//                doAfterTextChanged();
            }
        });

        radioGroup2 = (RadioGroup) findViewById(R.id.radioGroup2);

        // Checked change Listener for RadioGroup 1
        radioGroup2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId)
            {
                switch (checkedId)
                {
                    case R.id.radioBed:
                        Toast.makeText(getApplicationContext(), "Bed", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.radioRoom:
                        Toast.makeText(getApplicationContext(), "Room", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.radioFlat:
                        Toast.makeText(getApplicationContext(), "Flat", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.radioBanglow:
                        Toast.makeText(getApplicationContext(), "Banglow", Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        break;
                }
            }
        });

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int rbBtnTenantTypeId=radioGroup1.getCheckedRadioButtonId();
                for(int i=0; i<radioGroup1.getChildCount(); i++) {
                    RadioButton btn = (RadioButton) radioGroup1.getChildAt(i);
                    if(btn.getId() == rbBtnTenantTypeId) {
                        tenantType = btn.getText().toString();
                        System.out.println("radio tenant type : "+tenantType);
                    }
                }
                int rbBtnBookinTypeId=radioGroup2.getCheckedRadioButtonId();
                for(int i=0; i<radioGroup2.getChildCount(); i++) {
                    RadioButton btn = (RadioButton) radioGroup2.getChildAt(i);
                    if(btn.getId() == rbBtnBookinTypeId) {
                        booking_type = btn.getText().toString();
                        System.out.println("radio booking type : "+booking_type);
                    }
                }
                String actv = ((autocompleteView.getText() == null) ? "NA" : autocompleteView.getText().toString());
                citytext = (citytext ==null ? "NA" : citytext);
                tenantType = ((tenantType == null) ? "NA" : tenantType);
                booking_type = ((booking_type == null) ? "NA" : booking_type);
                selectedDate = ((selectedDate == null) ? "NA" : selectedDate);
                Intent feedActivityIntent = new Intent(SelectionProcessActivity.this,FeedActivity.class);
                feedActivityIntent.putExtra(LOCATION_AREA,actv);
                feedActivityIntent.putExtra(LOCATION_CITY,citytext);
                feedActivityIntent.putExtra(TENANT_TYPE,tenantType);
                feedActivityIntent.putExtra(BOOKING_TYPE,booking_type);
                feedActivityIntent.putExtra(MOVEIN_DATE,selectedDate);
                System.out.println(actv +" "+citytext+" "+tenantType+" "+booking_type+" "+selectedDate);
                startActivity(feedActivityIntent);
            }
        });
    }

    private void updateCityText() {
        SharedPreferences saved_values = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor=saved_values.edit();
        editor.putString(KEYCITY,citytext);
        editor.commit();
    }


    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        selectedDate = dayOfMonth+"/"+(monthOfYear+1)+"/"+year;
        tvCalender.setText(selectedDate);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        // Get rid of our Place API Handlers
        if (mThreadHandler != null) {
            mThreadHandler.removeCallbacksAndMessages(null);
            mHandlerThread.quit();
        }
    }

    private PlaceAutoCompleteAdapter mAdapter;

    HandlerThread mHandlerThread;
    Handler mThreadHandler;
    public SelectionProcessActivity() {
        // Required empty public constructor

        if (mThreadHandler == null) {
            // Initialize and start the HandlerThread
            // which is basically a Thread with a Looper
            // attached (hence a MessageQueue)
            mHandlerThread = new HandlerThread(TAG, android.os.Process.THREAD_PRIORITY_BACKGROUND);
            mHandlerThread.start();

            // Initialize the Handler
            mThreadHandler = new Handler(mHandlerThread.getLooper()) {
                @Override
                public void handleMessage(Message msg) {
                    if (msg.what == 1) {
                        ArrayList<String> results = mAdapter.resultList;

                        if (results != null && results.size() > 0) {
//                            mAdapter.notifyDataSetChanged();
                        }
                        else {
//                            mAdapter.notifyDataSetInvalidated();
                        }
                    }
                }
            };
        }

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
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_city) {
            Intent cityIntent = new Intent(SelectionProcessActivity.this,CityActivity.class);
            startActivityForResult(cityIntent, 1);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void setOptionTitle(int id, String cityname)
    {
        if(menu!=null) {
            MenuItem item = menu.findItem(id);
            item.setTitle(cityname);
        }
    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if(resultCode == 1){
                citytext=data.getStringExtra("city_name");
                updateCityText();
                System.out.print("Got city " + citytext);
                setOptionTitle(R.id.action_city, citytext);
            }
        }
    }

    //optional
    @AskGranted(Manifest.permission.ACCESS_FINE_LOCATION)
    public void mapAccessGranted() {
        Log.i(TAG, "Location GRANTED");
    }

    //optional
    @AskDenied(Manifest.permission.ACCESS_FINE_LOCATION)
    public void mapAccessDenied() {
        Log.i(TAG, "Location DENIED");
    }

}