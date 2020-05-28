package com.bezatretailernew.bezat.fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bezatretailernew.bezat.ClientRetrofit;
import com.bezatretailernew.bezat.R;
import com.bezatretailernew.bezat.interfaces.FeedbackCallback;
import com.bezatretailernew.bezat.models.feedbackResponse.FeedbackRequest;
import com.bezatretailernew.bezat.models.feedbackResponse.FeedbackResponse;
import com.bezatretailernew.bezat.utils.SharedPrefs;

/**
 * A simple {@link Fragment} subclass.
 */
public class Feedback extends Fragment {
    EditText text;
    RatingBar ratingBar;
    Button button;
    ImageView imgUser, imgBack;
    TextView username, txtFeedback;
    RecyclerView recFeedback;
    View rootView;
    public Feedback() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_feedback, container, false);
        recFeedback=rootView.findViewById(R.id.recFeedback);
//        addViews(view);
        onClickBackButton(rootView);
        return rootView;
    }

    private void onClickBackButton(View view) {
        imgBack = view.findViewById(R.id.img_back);
        imgBack.setOnClickListener(view1 -> getActivity().onBackPressed());
    }


//    private void addViews(View view) {
//
//    }
}
