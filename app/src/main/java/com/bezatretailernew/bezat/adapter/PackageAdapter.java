package com.bezatretailernew.bezat.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bezatretailernew.bezat.R;
import com.bezatretailernew.bezat.models.packageResponse.PackageData;
import com.bezatretailernew.bezat.models.packageResponse.PackageResponse;
import com.bumptech.glide.Glide;

import de.hdodenhof.circleimageview.CircleImageView;

public class PackageAdapter extends RecyclerView.Adapter<PackageAdapter.MyViewHolder> {

    private Context mcontext;
    private PackageResponse packageResponse;

    public PackageAdapter(Context mcontext, PackageResponse packageResponse) {
        this.mcontext = mcontext;
        this.packageResponse = packageResponse;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_package, parent, false);
        MyViewHolder mHolder = new MyViewHolder(view);
        return mHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        PackageData data = packageResponse.getData().get(position);

        holder.name.setText(data.getPackage_name());
        holder.raffles.setText(data.getNo_of_raffles()+ " COUPONS");
        Glide.with(mcontext)
                .load(data.getPkg_img())
                .into(holder.img);

        Log.d("---test---",data.getPkg_img());

        holder.bg.setBackgroundColor(Color.parseColor(data.getColor()));

        holder.know_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, "This is my text to send.");
                sendIntent.setType("text/plain");
                sendIntent.setPackage("com.whatsapp");
                sendIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                Intent i = new Intent(Intent.ACTION_VIEW,
                        Uri.parse(
                                "https://api.whatsapp.com/send?phone="+packageResponse.getWhatsapp()+"&text=Hi there, I would like to know more details about "+data.getPackage_name()
                        )
                );
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mcontext.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return packageResponse.getData().size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView name, raffles;
        CircleImageView img;
        Button know_more;
        LinearLayout bg;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.tv_package_name);
            raffles = itemView.findViewById(R.id.tv_package_raffles);
            img = itemView.findViewById(R.id.iv_package);
            know_more = itemView.findViewById(R.id.btn_know_more);
            bg = itemView.findViewById(R.id.bg);
        }
    }
}
