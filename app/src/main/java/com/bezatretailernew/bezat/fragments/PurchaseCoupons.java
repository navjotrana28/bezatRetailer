package com.bezatretailernew.bezat.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bezatretailernew.bezat.ClientRetrofit;
import com.bezatretailernew.bezat.R;
import com.bezatretailernew.bezat.adapter.PackageAdapter;
import com.bezatretailernew.bezat.interfaces.SearchPackageInterface;
import com.bezatretailernew.bezat.models.packageResponse.PackageResponse;
import com.bezatretailernew.bezat.utils.SharedPrefs;

/**
 * A simple {@link Fragment} subclass.
 */
public class PurchaseCoupons extends Fragment {

    String lang = "";
    ImageView imgBack;
    private RecyclerView recyclerViewPackages;
    LinearLayoutManager layoutManager;
    PackageAdapter adapter;
    PackageResponse response;

    public PurchaseCoupons() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        container.setClickable(true);
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_purchase_coupons, container, false);

        if (SharedPrefs.getKey(getActivity(), "selectedlanguage").contains("ar")) {
            getActivity().getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
            lang = "_ar";
        } else {
            getActivity().getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
            lang = "";
        }
        imgBack = view.findViewById(R.id.imgBack);
        recyclerViewPackages = view.findViewById(R.id.rv_packages);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerViewPackages.setLayoutManager(layoutManager);
        loadPackages();
        onClickBackButton();



        return view;
    }

    private void onClickBackButton() {
        imgBack.setOnClickListener(v -> getActivity().onBackPressed());
    }

    private void loadPackages() {
        ClientRetrofit clientRetrofit = new ClientRetrofit();
        clientRetrofit.getPackages(new SearchPackageInterface() {
            @Override
            public void onSuccess(PackageResponse responseResult) {
                response = responseResult;
                setUpRecyclerView();
            }

            @Override
            public void onFailure(Throwable e) {
                Log.d("---Failure---",e.getMessage());
            }
        });
    }

    private void setUpRecyclerView() {
        adapter = new PackageAdapter(getActivity().getBaseContext(),response);
        recyclerViewPackages.setAdapter(adapter);
    }
}
