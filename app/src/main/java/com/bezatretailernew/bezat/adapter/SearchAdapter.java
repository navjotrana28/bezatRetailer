package com.bezatretailernew.bezat.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bezatretailernew.bezat.R;
import com.bezatretailernew.bezat.interfaces.SearchRetailerCallback;
import com.bezatretailernew.bezat.models.searchRetailerResponses.SearchResponseResult;
import com.squareup.picasso.Picasso;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.MyViewHolder> {

    private Context mcontext;
    private SearchResponseResult responseResult;
    private SearchRetailerCallback searchRetailerCallback;

    public SearchAdapter(Context context, SearchResponseResult responseResult, SearchRetailerCallback searchRetailerCallback) {
        mcontext = context;
        this.responseResult = responseResult;
        this.searchRetailerCallback = searchRetailerCallback;
    }

    public void setDatumList(SearchResponseResult datumList) {
        this.responseResult = datumList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SearchAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.horizontal_adapter_search_retailer, parent, false);
        MyViewHolder mHolder = new MyViewHolder(view);
        return mHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SearchAdapter.MyViewHolder holder, int position) {
        holder.imnageHorizontalName.setText(responseResult.getResult().get(position).getCategory());
        Picasso.get()
                .load(responseResult.getResult().get(position).getCategoryImage())
                .resize(500, 500)
                .centerInside()
                .into(holder.imageHorizontal);
    }

    @Override
    public int getItemCount() {
        if (responseResult.getResult() != null) {
            return responseResult.getResult().size();
        } else
            return 0;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imageHorizontal;
        TextView imnageHorizontalName;

        MyViewHolder(View view) {
            super(view);
            imageHorizontal = view.findViewById(R.id.image_horizontal);
            imnageHorizontalName = view.findViewById(R.id.text_horizontal);

            view.setOnClickListener(view1 -> {
                int pos = getAdapterPosition();
                searchRetailerCallback.onClickHorizonView(pos);
            });
        }
    }
}