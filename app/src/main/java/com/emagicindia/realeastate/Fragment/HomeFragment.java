package com.emagicindia.realeastate.Fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.emagicindia.realeastate.R;
import com.emagicindia.realeastate.activity.SelectionProcessActivity;

/**
 * Created by dell on 8/6/2016.
 */

public class HomeFragment extends Fragment {

    private TextView tvSelect;
    private Context mContext;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        mContext = getContext();

        tvSelect = (TextView)rootView.findViewById(R.id.tv_select);
        tvSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(mContext,SelectionProcessActivity.class);//SelectionProcessActivity.class);
                startActivity(intent);
//                Toast.makeText(mContext,"Select Location",Toast.LENGTH_SHORT).show();
            }
        });


        // Inflate the layout for this fragment
        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
