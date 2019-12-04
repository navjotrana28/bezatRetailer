package com.bezatretailer.bezat.fragments;


import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bezatretailer.bezat.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchRetailer extends Fragment {


    public SearchRetailer() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search_retailer, container, false);
    }

}
