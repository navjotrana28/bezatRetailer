package com.bezatretailernew.bezat.fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.toolbox.JsonObjectRequest;
import com.bezatretailernew.bezat.ClientRetrofit;
import com.bezatretailernew.bezat.R;
import com.bezatretailernew.bezat.interfaces.FeedbackCallback;
import com.bezatretailernew.bezat.models.feedbackResponse.FeedbackRequest;
import com.bezatretailernew.bezat.models.feedbackResponse.FeedbackResponse;
import com.bezatretailernew.bezat.utils.SharedPrefs;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Request;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class Feedback extends Fragment {
    EditText text;
    Button button;
    ImageView  imgBack;
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
            getFeedbacks();
//        addViews(view);
        onClickBackButton(rootView);





        return rootView;
    }

private void getFeedbacks(){
    JSONObject object=new JSONObject();
    String url="http://bezatapp.com/bezatapi/staff/feedbacks"+"retailerId="+SharedPrefs.getKey(getActivity(),"retailerId");
   // JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request)
}

    private void onClickBackButton(View view) {
        imgBack = view.findViewById(R.id.img_back);
        imgBack.setOnClickListener(view1 -> getActivity().onBackPressed());
    }


//    private void addViews(View view) {
//
//    }

private  class PostAdapter extends  RecyclerView.Adapter<PostAdapter.MyViewHolder>{


        JSONArray jsonArray;
        public PostAdapter(JSONArray array){this.jsonArray=array;}

    public void append(JSONArray array) {
        try {
            for (int i = 0; i < array.length(); i++) {
                this.jsonArray.put(array.get(i));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @NonNull
    @Override
    public PostAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemView= LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.feedback_item,parent,false);
        return new PostAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

            try {
                holder.userName.setText(jsonArray.getJSONObject(position).getString("user_name"));
                holder.txtFeedback.setText(jsonArray.getJSONObject(position).getString("feedback"));
                holder.ratingBar.setText(jsonArray.getJSONObject(position).getString("ratings"));
                Picasso.get().load(jsonArray.getJSONObject(position).getString("image")).into(holder.imgUser);

            }catch (Exception e){
                e.printStackTrace();
            }

    }

//    @Override
//    public void onBindViewHolder(@NonNull PostAdapter.MyViewHolder holder, int position, @NonNull List<Object> payloads) {
//        super.onBindViewHolder(holder, position, payloads);
//    }

    @Override
    public int getItemCount() {
        return jsonArray.length();
    }

    public  class MyViewHolder extends RecyclerView.ViewHolder{

            ImageView imgUser;
            TextView userName, txtFeedback;
            TextView ratingBar;

            public MyViewHolder(@NonNull View itemView) {
                super(itemView);
                imgUser=itemView.findViewById(R.id.imgUser);
                userName=itemView.findViewById(R.id.userName);
                txtFeedback=itemView.findViewById(R.id.txtFeedback);
                ratingBar=itemView.findViewById(R.id.rating);
            }
        }

}

}

