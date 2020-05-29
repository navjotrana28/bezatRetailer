package com.bezatretailernew.bezat.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bezatretailernew.bezat.R;
import com.bezatretailernew.bezat.models.feedbackResponse.FeedbackResponse;
import com.bezatretailernew.bezat.models.searchRetailerResponses.SearchResponseData;
import com.squareup.picasso.Picasso;

public class FeedbackAdapter extends RecyclerView.Adapter<FeedbackAdapter.MyViewHolder> {

    private Context mcontext;
    private FeedbackResponse responseResult;

    public FeedbackAdapter(Context context, FeedbackResponse responseResult) {
        mcontext = context;
        this.responseResult = responseResult;
    }

    public void setDatumList(FeedbackResponse datumList) {
        this.responseResult = datumList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.feedback_item, parent, false);
        MyViewHolder mHolder = new MyViewHolder(view);
        return mHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.title.setText(responseResult.getData().get(position).getUserName());
        holder.feedback.setText(responseResult.getData().get(position).getFeedback());
        holder.ratingBar.setNumStars(Integer.parseInt(responseResult.getData().get(position).getRatings()));
        Picasso.get()
                .load(responseResult.getData().get(position).getImage())
                .resize(500, 200)
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        if (responseResult.getData() != null) {
            return responseResult.getData().size();
        } else
            return 0;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView title, feedback;
        RatingBar ratingBar;

        MyViewHolder(View view) {
            super(view);
            imageView = view.findViewById(R.id.imgUser);
            title = view.findViewById(R.id.userName);
            feedback = view.findViewById(R.id.txtFeedback);
            ratingBar = view.findViewById(R.id.rating);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getAdapterPosition();
                }
            });
        }
    }
}
