package com.bezatretailernew.bezat.adapter;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bezatretailernew.bezat.R;
import com.bezatretailernew.bezat.models.vip_lists.VipResult;
import com.squareup.picasso.Picasso;

public class VipAdapter extends RecyclerView.Adapter<VipAdapter.MyViewHolder> {
    private VipResult vipResult = new VipResult();
    String lang;

    public VipAdapter(String lang) {
        this.lang = lang;
    }

    @NonNull
    @Override
    public VipAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.vip_list_adapter, viewGroup, false);
        MyViewHolder mHolder = new MyViewHolder(view);
        return mHolder;
    }

    public void setData(VipResult result) {
        vipResult = result;
    }

    @Override
    public void onBindViewHolder(@NonNull final VipAdapter.MyViewHolder viewHolder, int i) {
        if(lang.equals("a")){
            viewHolder.customerName.setGravity(Gravity.RIGHT);
            viewHolder.code.setGravity(Gravity.RIGHT);
            viewHolder.Email.setGravity(Gravity.RIGHT);
            viewHolder.phone.setGravity(Gravity.RIGHT);
        }else{
            viewHolder.customerName.setGravity(Gravity.LEFT);
            viewHolder.code.setGravity(Gravity.LEFT);
            viewHolder.Email.setGravity(Gravity.LEFT);
            viewHolder.phone.setGravity(Gravity.LEFT);
        }
        viewHolder.customerName.setText(vipResult.getResult().get(i).getCustomerName());
        viewHolder.code.setText(vipResult.getResult().get(i).getCustomerCode());
        viewHolder.Email.setText(vipResult.getResult().get(i).getCustomerEmail());
        viewHolder.phone.setText(vipResult.getResult().get(i).getCustomerPhone());
        try {
            String path = vipResult.getResult().get(i).getCustomerImage();
            Picasso.get().load(path).into(viewHolder.imgVip);
        } catch (Exception e) {
            Log.d("", e.toString());
        }
    }

    @Override
    public int getItemCount() {
        if (vipResult.getResult().size() > 0) {
            return vipResult.getResult().size();
        } else
            return 0;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView customerName, code, Email, phone;
        ImageView imgVip;

        MyViewHolder(View view) {
            super(view);
            customerName = view.findViewById(R.id.txCustomerName);
            code = view.findViewById(R.id.txtCustomerCode);
            Email = view.findViewById(R.id.txtCustomerEmail);
            phone = view.findViewById(R.id.txtCustomerPhone);
            imgVip = view.findViewById(R.id.imageVip);

        }
    }
}
