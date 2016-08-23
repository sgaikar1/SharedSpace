package com.emagicindia.realeastate.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.emagicindia.realeastate.R;
import com.emagicindia.realeastate.model.CityItem;

import java.util.Collections;
import java.util.List;

/**
 * Created by dell on 8/6/2016.
 */

public class CityAdapter extends RecyclerView.Adapter<CityAdapter.MyViewHolder> {
    List<CityItem> data = Collections.emptyList();
    private LayoutInflater inflater;
    private Context mContext;
    CityListsener cityListsener;

    public CityAdapter(Context context) {
        this.mContext = context;
        inflater = LayoutInflater.from(context);
    }

    public void setCities(List<CityItem> data) {
        this.data = data;
    }
    public void delete(int position) {
        data.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.row_city, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final CityItem current = data.get(position);
        holder.title.setText(current.getTitle());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cityListsener.onCitySelected(current);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView title;

        public MyViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.tv_row_city);

        }
    }

    public void registerCityListener(CityListsener cityListsener) {
     this.cityListsener = cityListsener;
    }

    public interface CityListsener {
       void onCitySelected(CityItem city);
    }
}
