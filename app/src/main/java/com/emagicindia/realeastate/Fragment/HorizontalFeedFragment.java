package com.emagicindia.realeastate.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.emagicindia.realeastate.R;
import com.emagicindia.realeastate.model.PropertyItem;

import static com.emagicindia.realeastate.utils.AppConfig.BASE_URL;
import static com.emagicindia.realeastate.utils.AppConfig.KEYBEDCOUNTS;
import static com.emagicindia.realeastate.utils.AppConfig.KEYBEDROOMCOUNT;
import static com.emagicindia.realeastate.utils.AppConfig.KEY_CITY;
import static com.emagicindia.realeastate.utils.AppConfig.KEYIMAGE;
import static com.emagicindia.realeastate.utils.AppConfig.KEYLOCATION;
import static com.emagicindia.realeastate.utils.AppConfig.KEYPROPERTYNAME;
import static com.emagicindia.realeastate.utils.AppConfig.KEYRENTAMOUNT;
import static com.emagicindia.realeastate.utils.AppConfig.KEYTENANTTYPE;

/**
 * Created by dell on 8/3/2016.
 */

public class HorizontalFeedFragment extends Fragment {

    private static HorizontalFeedFragment slideshowFragment;

    private ImageView imageView;
    private TextView tvRentAmount,tvTenantType,tvAddress1,tvAddress2,tvBedroomCount,tvBedsCount;

    public HorizontalFeedFragment(){

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.row_horizontal_feeds,container,false);
        imageView = (ImageView)view.findViewById(R.id.iv_feed_img);
        tvRentAmount = (TextView)view.findViewById(R.id.tv_rent_amount);
        tvTenantType = (TextView)view.findViewById(R.id.tv_tenant_type);
        tvAddress1 = (TextView)view.findViewById(R.id.tv_address1);
        tvAddress2 = (TextView)view.findViewById(R.id.tv_address2);
        tvBedroomCount = (TextView)view.findViewById(R.id.tv_bedroom_count);
        tvBedsCount = (TextView)view.findViewById(R.id.tv_bed_count);

        String image=BASE_URL+getArguments().getString(KEYIMAGE);

        tvRentAmount.setText(getArguments().getString(KEYRENTAMOUNT));
        tvTenantType.setText(getArguments().getString(KEYTENANTTYPE));
        tvAddress1.setText(getArguments().getString(KEYPROPERTYNAME));
        tvAddress2.setText(getArguments().getString(KEYLOCATION)+", "+getArguments().getString(KEY_CITY));
        tvBedroomCount.setText("3 BHK");
        tvBedsCount.setText("1 Bed Available");

        Glide.with(getContext())
                .load(image)
                .placeholder(R.drawable.ic_launcher)
                .crossFade()
                .centerCrop()
                .into(imageView);

        return view;
    }

    public static Fragment newInstance(PropertyItem item){
        Bundle bundle = new Bundle();
        String img = item.getImagePath();
        String rentAmount = item.getPropertyRent();
        String tenantType = item.getTenantType();
        String propertyName = item.getPropertyName();
        String location = item.getLocation();
        String city = item.getCity();
        String bedroomCount = item.getNoBedrooms();
        String bedCounts = "1 Bed Available";

        bundle.putString(KEYIMAGE,img);
        bundle.putString(KEYRENTAMOUNT,rentAmount);
        bundle.putString(KEYTENANTTYPE,tenantType);
        bundle.putString(KEYPROPERTYNAME,propertyName);
        bundle.putString(KEYLOCATION,location);
        bundle.putString(KEY_CITY,city);
        bundle.putString(KEYBEDROOMCOUNT,bedroomCount);
        bundle.putString(KEYBEDCOUNTS,bedCounts);

        HorizontalFeedFragment slideshowFragment = new HorizontalFeedFragment();
        slideshowFragment.setArguments(bundle);
        return slideshowFragment;
    }
}
