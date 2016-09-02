package com.emagicindia.realeastate.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.emagicindia.realeastate.R;

/**
 * Created by dell on 19/08/2016.
 */

public class FeedbackActivity extends AppCompatActivity {
    private RatingBar ratingBar;
    private TextView tvRatingText;
    private LinearLayout llView;
    private TableLayout tlOptions;
    private EditText etFeedback;
    private CheckBox cbDesign;
    private CheckBox cbAvailability;
    private CheckBox cbCare;
    private CheckBox cbFeatures;
    private CheckBox cbOthers;
    private CheckBox cbPricing;
    private Button btnSubmit;
    private Context mContext;
    public int ratingCount = 0;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        mContext = this;

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        TextView mTitle = (TextView) mToolbar.findViewById(R.id.tv_toolbar_title);
        mTitle.setText(getResources().getString(R.string.nav_item_feedback));


        ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        tvRatingText = (TextView) findViewById(R.id.tv_ratingtext);

        llView = (LinearLayout) findViewById(R.id.ll_view);
        tlOptions = (TableLayout) findViewById(R.id.tl_options);
        etFeedback = (EditText) findViewById(R.id.et_feedback);

        cbDesign = (CheckBox) findViewById(R.id.cb_design);
        cbAvailability = (CheckBox) findViewById(R.id.cb_availability);
        cbCare = (CheckBox) findViewById(R.id.cb_care);
        cbFeatures = (CheckBox) findViewById(R.id.cb_features);
        cbOthers = (CheckBox) findViewById(R.id.cb_others);
        cbPricing = (CheckBox) findViewById(R.id.cb_pricing);
        btnSubmit = (Button) findViewById(R.id.btn_submit);

        llView.setVisibility(View.GONE);


        //if rating value is changed,
        //display the current rating value in the result (textview) automatically
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {

            public void onRatingChanged(RatingBar ratingBar, float rating,
                                        boolean fromUser) {

                llView.setVisibility(View.VISIBLE);

                ratingCount = (int) rating;
                switch ((int) rating) {
                    case 0:
                        tvRatingText.setText("");
                        llView.setVisibility(View.GONE);
                        break;
                    case 1:
                        tvRatingText.setText("Very Bad!");
                        tlOptions.setVisibility(View.VISIBLE);
                        break;
                    case 2:
                        tvRatingText.setText("Bad!");
                        tlOptions.setVisibility(View.VISIBLE);
                        break;
                    case 3:
                        tvRatingText.setText("Ok Ok!");
                        tlOptions.setVisibility(View.VISIBLE);
                        break;
                    case 4:
                        tvRatingText.setText("Good!");
                        tlOptions.setVisibility(View.GONE);
                        break;
                    case 5:
                        tvRatingText.setText("Awesome!");
                        tlOptions.setVisibility(View.GONE);
                        break;
                    default:
                        tvRatingText.setText("");
                        llView.setVisibility(View.GONE);
                        break;
                }
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String comment = etFeedback.getText().toString().isEmpty()?"":etFeedback.getText().toString();
                if (llView.getVisibility() == View.VISIBLE) {

                    if (tlOptions.getVisibility() != View.VISIBLE) {
                        Toast.makeText(mContext, "Thanx for your feedback", Toast.LENGTH_SHORT).show();
                    } else {

                        Toast.makeText(mContext, "Availability " + String.valueOf(cbAvailability.isChecked()) +
                                ", Care " + String.valueOf(cbCare.isChecked()) +
                                ", Design " + String.valueOf(cbDesign.isChecked()) +
                                ", Features " + String.valueOf(cbFeatures.isChecked()) +
                                ", Others " + String.valueOf(cbOthers.isChecked()) +
                                ", Pricing " + String.valueOf(cbPricing.isChecked())+
                                ", Comment " + comment, Toast.LENGTH_SHORT).show();
                    }


                } else {
                    Toast.makeText(mContext, "Please provide a rating", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
