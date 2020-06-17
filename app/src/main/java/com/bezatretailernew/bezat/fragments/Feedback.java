package com.bezatretailernew.bezat.fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bezatretailernew.bezat.ClientRetrofit;
import com.bezatretailernew.bezat.R;
import com.bezatretailernew.bezat.adapter.FeedbackAdapter;
import com.bezatretailernew.bezat.interfaces.FeedbackCallback;
import com.bezatretailernew.bezat.models.feedbackResponse.FeedbackResponse;
import com.bezatretailernew.bezat.utils.Loader;
import com.bezatretailernew.bezat.utils.SharedPrefs;


import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class Feedback extends Fragment {
    EditText text;
    Button button;
    ImageView imgBack;
    RecyclerView recFeedback;
    FeedbackAdapter feedbackAdapter;
    Loader loader;
    LinearLayoutManager layoutManager;
    private FeedbackResponse responseData = new FeedbackResponse();

    View rootView;

    public Feedback() {
        // Required empty public constructor
    }

    String lang = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        container.setClickable(true);
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_feedback, container, false);
        if (SharedPrefs.getKey(getActivity(), "selectedlanguage").contains("ar")) {
            getActivity().getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
            lang = "_ar";
        } else {
            getActivity().getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
            lang = "";
        }
        recFeedback = rootView.findViewById(R.id.recFeedback);
        loader = new Loader(getContext());
        loader.show();
        getFeedbacks();
        onClickBackButton(rootView);
        return rootView;
    }


    private void getFeedbacks() {
        ClientRetrofit clientRetrofit = new ClientRetrofit();
        clientRetrofit.feedBackRequestApi(SharedPrefs.getKey(getActivity(), "storeID"),
                new FeedbackCallback() {
                    @Override
                    public void onSuccess(FeedbackResponse responseResult) {
                        if (responseResult.getData() != null) {
                            feedbackAdapter = new FeedbackAdapter(getActivity(), responseResult);
                            layoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
                            recFeedback.setLayoutManager(layoutManager);
                            recFeedback.setAdapter(feedbackAdapter);
                            feedbackAdapter.setDatumList(responseResult);
                            loader.dismiss();
                        } else {
                            Toast.makeText(getContext(), responseResult.getMessage(), Toast.LENGTH_SHORT).show();
                            loader.dismiss();
                            getActivity().onBackPressed();
                        }
                    }

                    @Override
                    public void onFailure(Throwable e) {

                    }
                });
    }

    private void onClickBackButton(View view) {
        imgBack = view.findViewById(R.id.img_back);
        if(lang.equals("_ar")){
            imgBack.setImageDrawable(getResources().getDrawable(R.drawable.ic_back_rtl));
        }
        imgBack.setOnClickListener(view1 -> getActivity().onBackPressed());
    }

}

