package com.emagicindia.realeastate.adapter;

import android.content.Context;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.emagicindia.realeastate.R;
import com.emagicindia.realeastate.utils.PlaceAPI;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by dell on 8/9/2016.
 */
public class PlaceAutoCompleteAdapter extends ArrayAdapter<String> implements Filterable  {

    public ArrayList<String> resultList;

    Context mContext;
    int mResource;

    public PlaceAPI mPlaceAPI = new PlaceAPI();
    public ArrayList<ArrayList<String>>outer =new ArrayList<ArrayList<String>>(3);
    public ArrayList<String> remainingList;
    private String text="";

    public PlaceAutoCompleteAdapter(Context context, int resource) {
        super(context, resource);

        mContext = context;
        mResource = resource;
    }



    @Override
    public int getCount() {
        // Last item will be the footer
        return resultList.size();
    }

    @Override
    public String getItem(int position) {
        return resultList.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;

        //if (convertView == null) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (position != (resultList.size() - 1))
            view = inflater.inflate(R.layout.row_autocomplete_place_api, null);
        else
            view = inflater.inflate(R.layout.row_autocomplete_google_logo, null);
        //}
        //else {
        //    view = convertView;
        //}

        if (position != (resultList.size() - 1)) {
            TextView autocompleteTextView = (TextView) view.findViewById(R.id.autocompleteText);
            TextView autocompleteTextView1 = (TextView) view.findViewById(R.id.autocompleteText1);
            autocompleteTextView.setText(highlight(text,resultList.get(position)));
            autocompleteTextView1.setText(remainingList.get(position));
        }
        else {
            ImageView imageView = (ImageView) view.findViewById(R.id.imageView);
            // not sure what to do <img draggable="false" class="emoji" alt="😀" src="https://s.w.org/images/core/emoji/72x72/1f600.png">
        }

        return view;
    }


    public CharSequence highlight(String search, String originalText) {
        // ignore case and accents
        // the same thing should have been done for the search text
        String normalizedText = Normalizer
                .normalize(originalText, Normalizer.Form.NFD)
                .replaceAll("\\p{InCombiningDiacriticalMarks}+", "")
                .toLowerCase(Locale.ENGLISH);

        int start = normalizedText.indexOf(search.toLowerCase(Locale.ENGLISH));
        if (start < 0) {
            // not found, nothing to to
            return originalText;
        } else {
            // highlight each appearance in the original text
            // while searching in normalized text
            Spannable highlighted = new SpannableString(originalText);
            while (start >= 0) {
                int spanStart = Math.min(start, originalText.length());
                int spanEnd = Math.min(start + search.length(),
                        originalText.length());

                highlighted.setSpan(new ForegroundColorSpan(mContext.getResources().getColor(R.color.colorAccent)),
                        spanStart, spanEnd, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

                start = normalizedText.indexOf(search, spanEnd);
            }

            return highlighted;
        }
    }


    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults = new FilterResults();
                if (constraint != null) {
                    text=constraint.toString();
                    outer=mPlaceAPI.autocomplete(constraint.toString());
                    resultList = outer.get(0);
                    remainingList = outer.get(1);

                    // Footer
                    resultList.add("footer");

                    filterResults.values = resultList;
                    filterResults.count = resultList.size();
                }

                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                if (results != null && results.count > 0) {
                    notifyDataSetChanged();
                }
                else {
                    notifyDataSetInvalidated();
                }
            }
        };

        return filter;
    }
}
