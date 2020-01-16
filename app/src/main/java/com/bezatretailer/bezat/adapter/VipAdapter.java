package com.bezatretailer.bezat.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bezatretailer.bezat.R;

public class VipAdapter extends RecyclerView.Adapter<VipAdapter.MyViewHolder> {

    @NonNull
    @Override
    public VipAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.vip_list_adapter, viewGroup, false);
        MyViewHolder mHolder = new MyViewHolder(view);
        return mHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final VipAdapter.MyViewHolder viewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return 4;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        MyViewHolder(View view) {
            super(view);

        }
    }
}
