package com.emagicindia.realeastate.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.emagicindia.realeastate.R;
import com.emagicindia.realeastate.model.FeedArray;
import com.emagicindia.realeastate.model.PropertyItem;

import java.util.ArrayList;

import static com.emagicindia.realeastate.utils.AppConfig.BASE_URL;

/**
 * Created by dell on 8/12/2016.
 */

public class FeedsAdapter extends RecyclerView.Adapter<FeedsAdapter.ViewHolder> {
    private final Context mContext;
    private ArrayList<PropertyItem> android;

    public FeedsAdapter(Context mContext, ArrayList<PropertyItem> android) {
        this.mContext =mContext;
        this.android = android;
    }

    @Override
    public FeedsAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_feeds, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FeedsAdapter.ViewHolder viewHolder, int i) {

        viewHolder.tvRentAmount.setText("₹ "+android.get(i).getPropertyRent());
        viewHolder.tvTenantType.setText(android.get(i).getTenantType());
        viewHolder.tvAddress1.setText(android.get(i).getPropertyName());
        viewHolder.tvAddress2.setText(android.get(i).getLocation()+", "+android.get(i).getCity());
        viewHolder.tvBedroomCount.setText("3 BHK");
        viewHolder.tvBedsCount.setText("1 Bed Available");
        Glide.with(mContext)
            .load(BASE_URL+android.get(i).getImagePath())
            .centerCrop()
            .placeholder(R.mipmap.ic_launcher)
            .crossFade()
            .into(viewHolder.ivImage);
    }

    @Override
    public int getItemCount() {
        return android.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private final ImageView ivImage;
        private TextView tvRentAmount,tvTenantType,tvAddress1,tvAddress2,tvBedroomCount,tvBedsCount;
        public ViewHolder(View view) {
            super(view);

            tvRentAmount = (TextView)view.findViewById(R.id.tv_rent_amount);
            tvTenantType = (TextView)view.findViewById(R.id.tv_tenant_type);
            tvAddress1 = (TextView)view.findViewById(R.id.tv_address1);
            tvAddress2 = (TextView)view.findViewById(R.id.tv_address2);
            tvBedroomCount = (TextView)view.findViewById(R.id.tv_bedroom_count);
            tvBedsCount = (TextView)view.findViewById(R.id.tv_bed_count);
            ivImage = (ImageView)view.findViewById(R.id.iv_feed_img);

        }
    }

}
