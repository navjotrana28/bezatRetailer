package com.bezatretailer.bezat.fragments;

import android.content.Context;
import android.os.Bundle;
import android.widget.ImageView;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bezatretailer.bezat.R;


public class ContactUsFragment extends Fragment {
    ImageView imgBack;

    public ContactUsFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_contact_us, container, false);
        onClickBackButton(view);
        return view;
    }

    private void onClickBackButton(View view) {
        imgBack = view.findViewById(R.id.img_back);
        imgBack.setOnClickListener(view1 -> getActivity().onBackPressed());
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
