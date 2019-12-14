package com.bezatretailer.bezat.fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.fragment.app.Fragment;
import com.bezatretailer.bezat.ClientRetrofit;
import com.bezatretailer.bezat.R;
import com.bezatretailer.bezat.interfaces.FeedbackCallback;
import com.bezatretailer.bezat.models.feedbackResponse.FeedbackRequest;
import com.bezatretailer.bezat.models.feedbackResponse.FeedbackResponse;
import com.bezatretailer.bezat.utils.SharedPrefs;

/**
 * A simple {@link Fragment} subclass.
 */
public class Feedback extends Fragment {
    EditText text;
    RatingBar ratingBar;
    Button button;
    ImageView imgBack;
    public Feedback() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_feedback, container, false);
        addViews(view);
        onCLickSendBtn();
        onClickBackButton(view);

        return view;
    }

    private void onCLickSendBtn() {
        button.setOnClickListener(v -> {
            FeedbackRequest request = new FeedbackRequest();
            request.setFeedback(text.getText().toString());
            request.setRatings(String.valueOf(ratingBar.getNumStars()));
            request.setUserId(SharedPrefs.getKey(getActivity(), "userId"));
            request.setRetailerId("1448");
            feedbackToServer(request);
        });

    }

    private void onClickBackButton(View view) {
        imgBack = view.findViewById(R.id.img_back);
        imgBack.setOnClickListener(view1 -> getActivity().onBackPressed());
    }

    private void feedbackToServer(FeedbackRequest request) {
        ClientRetrofit clientRetrofit = new ClientRetrofit();
        clientRetrofit.feedBackRequestApi(request, new FeedbackCallback() {
            @Override
            public void onSuccess(FeedbackResponse response) {
                Toast.makeText(getContext(), response.getStatus(), Toast.LENGTH_LONG).show();
                getActivity().onBackPressed();
            }

            @Override
            public void onFailure(Throwable e) {

            }
        });

    }

    private void addViews(View view) {
        text = view.findViewById(R.id.edit_text);
        ratingBar = view.findViewById(R.id.rating_bar);
        button = view.findViewById(R.id.send_button);

    }

}
