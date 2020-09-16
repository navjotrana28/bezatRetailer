package com.bezatretailernew.bezat.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bezatretailernew.bezat.R;
import com.bezatretailernew.bezat.models.getOfferCodes.GetOfferCodesResponse;
import com.bezatretailernew.bezat.models.getOfferCodes.OfferCodeData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RedeemOfferAdapter extends RecyclerView.Adapter<RedeemOfferAdapter.MyViewHolder> {

    private Context mcontext;
    private GetOfferCodesResponse response;
    String lang;
    Map<Integer,MyViewHolder> map = new HashMap<>();
    public RedeemOfferAdapter(Context mcontext, GetOfferCodesResponse response, String lang) {
        this.mcontext = mcontext;
        this.response = response;
        this.lang = lang;
    }

    List<String> array_offers = new ArrayList<>();

    public String[] getArray_offers() {
        String[] arr = new String[array_offers.size()];
        for (int i = 0; i < array_offers.size(); i++) {
            arr[i] = array_offers.get(i);
        }
        return arr;
    }

    boolean single = false;
    private int selectedPosition = -1;

    public void setArray_offers(List<String> array_offers) {
        this.array_offers = array_offers;
    }

    @NonNull
    @Override
    public RedeemOfferAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_redeem_offer, parent, false);
        RedeemOfferAdapter.MyViewHolder mHolder = new MyViewHolder(view);
        return mHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RedeemOfferAdapter.MyViewHolder holder, int position) {
        if (response.getData() != null && response.getData().size() != 0) {
            OfferCodeData data = response.getData().get(position);
            if(lang.equals("")){
                holder.offer_name.setText(data.getOffer_name());
            }else{
                holder.offer_name.setText(data.getOffer_name_ar());
            }

        }
        if(selectedPosition == position){
            holder.selected.setChecked(true);
        }
        else{
            holder.selected.setChecked(false);
        }
        map.put(position,holder);
        holder.selected.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    if(selectedPosition!=-1){
                        map.get(selectedPosition).selected.setChecked(false);
                    }
                    selectedPosition = holder.getAdapterPosition();
                    if(single){
                        array_offers.clear();
                        array_offers.add(response.getData().get(position).getOffer_coupon_code());
                        single = true;
                    }else{
                        array_offers.clear();
                        array_offers.add(response.getData().get(position).getOffer_coupon_code());
                        single = true;
                    }

                } else {
                    array_offers.remove(response.getData().get(position).getOffer_coupon_code());
                    single = false;
                }
                Log.d("---test---",array_offers.toString());
            }
        });
    }

    @Override
    public int getItemCount() {

        if (response.getData() != null) {
            return response.getData().size();
        }
        return 0;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView offer_name;
        CheckBox selected;

        public MyViewHolder(@NonNull View itemView) {

            super(itemView);
            offer_name = itemView.findViewById(R.id.offer_name);
            selected = itemView.findViewById(R.id.cb_offer);
        }
    }
}
