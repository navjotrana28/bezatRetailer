package com.bezatretailernew.bezat.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.bezatretailernew.bezat.ClientRetrofit;
import com.bezatretailernew.bezat.R;
import com.bezatretailernew.bezat.adapter.PackageAdapter;
import com.bezatretailernew.bezat.adapter.RedeemOfferAdapter;
import com.bezatretailernew.bezat.interfaces.GetOfferCodeCallback;
import com.bezatretailernew.bezat.interfaces.RedeemUserOfferCallback;
import com.bezatretailernew.bezat.interfaces.SearchPackageInterface;
import com.bezatretailernew.bezat.models.getOfferCodes.GetOfferCodesResponse;
import com.bezatretailernew.bezat.models.packageResponse.PackageResponse;
import com.bezatretailernew.bezat.models.redeemUserOffer.RedeemUserOfferRequest;
import com.bezatretailernew.bezat.models.redeemUserOffer.RedeemUserOfferResponse;
import com.bezatretailernew.bezat.utils.SharedPrefs;

/**
 * A simple {@link Fragment} subclass.
 */
public class RedeemOffer extends Fragment {

    String lang = "";
    ImageView imgBack;
    private RecyclerView recyclerViewOffers;
    GridLayoutManager layoutManager;
    String retailerId;
    Button redeem;
    EditText customer_code,phone_number;
    RedeemOfferAdapter adapter;


    public RedeemOffer() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_redeem_offer, container, false);

        if (SharedPrefs.getKey(getActivity(), "selectedlanguage").contains("ar")) {
            getActivity().getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
            lang = "_ar";
        } else {
            getActivity().getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
            lang = "";
        }
        retailerId = SharedPrefs.getKey(getActivity(), "userId");
        imgBack = view.findViewById(R.id.imgBack);
        recyclerViewOffers = view.findViewById(R.id.rv_get_offers);
        layoutManager = new GridLayoutManager(getActivity(),2);
        recyclerViewOffers.setLayoutManager(layoutManager);
        redeem = view.findViewById(R.id.btn_redeem);
        customer_code = view.findViewById(R.id.et_customer_code);
        phone_number = view.findViewById(R.id.et_phone_number);
        setupRedeemButton();
        loadOffers();
        onClickBackButton();



        return view;
    }

    private void setupRedeemButton() {
        RedeemUserOfferRequest request = new RedeemUserOfferRequest();
        request.setCustomer_code(customer_code.getText().toString());
        request.setPhone_number(phone_number.getText().toString());
        redeem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                request.setOffer_code(adapter.getArray_offers());
                ClientRetrofit clientRetrofit = new ClientRetrofit();
                clientRetrofit.redeemUserOffer(request, new RedeemUserOfferCallback() {
                    @Override
                    public void onSuccess(RedeemUserOfferResponse responseResult) {

                    }

                    @Override
                    public void onFailure(Throwable e) {

                    }
                });
            }
        });
    }

    private void loadOffers() {
        ClientRetrofit clientRetrofit = new ClientRetrofit();
        clientRetrofit.getOfferCodes(retailerId, new GetOfferCodeCallback() {
            @Override
            public void onSuccess(GetOfferCodesResponse responseResult) {
                adapter = new RedeemOfferAdapter(getActivity().getBaseContext(),responseResult);
                recyclerViewOffers.setAdapter(adapter);
            }

            @Override
            public void onFailure(Throwable e) {

            }
        });
    }

    private void onClickBackButton() {
        imgBack.setOnClickListener(v -> getActivity().onBackPressed());
    }
}
