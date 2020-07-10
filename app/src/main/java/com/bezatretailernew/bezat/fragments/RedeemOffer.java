package com.bezatretailernew.bezat.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bezatretailernew.bezat.ClientRetrofit;
import com.bezatretailernew.bezat.R;
import com.bezatretailernew.bezat.adapter.RedeemOfferAdapter;
import com.bezatretailernew.bezat.interfaces.GetOfferCodeCallback;
import com.bezatretailernew.bezat.interfaces.RedeemUserOfferCallback;
import com.bezatretailernew.bezat.interfaces.UserCodeInterface;
import com.bezatretailernew.bezat.interfaces.UserPhoneInterface;
import com.bezatretailernew.bezat.models.UserCodeResponse;
import com.bezatretailernew.bezat.models.getOfferCodes.GetOfferCodesResponse;
import com.bezatretailernew.bezat.models.redeemUserOffer.RedeemUserOfferRequest;
import com.bezatretailernew.bezat.models.redeemUserOffer.RedeemUserOfferResponse;
import com.bezatretailernew.bezat.models.userPhoneResponse;
import com.bezatretailernew.bezat.utils.Loader;
import com.bezatretailernew.bezat.utils.SharedPrefs;
import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.google.zxing.Result;

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
    Loader loader;
    EditText customer_code, phone_number;
    RedeemOfferAdapter adapter;
    TextView error;

    private CodeScanner mCodeScanner;
    CodeScannerView scannerView;


    public RedeemOffer() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        container.setClickable(true);
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_redeem_offer, container, false);

        if (SharedPrefs.getKey(getActivity(), "selectedlanguage").contains("ar")) {
            getActivity().getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
            lang = "_ar";
        } else {
            getActivity().getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
            lang = "";
        }
        retailerId = SharedPrefs.getKey(getActivity(), "storeID");
        imgBack = view.findViewById(R.id.imgBack);
        if(lang.equals("_ar")){
            imgBack.setImageDrawable(getResources().getDrawable(R.drawable.ic_back_rtl));
        }
        recyclerViewOffers = view.findViewById(R.id.rv_get_offers);
        layoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerViewOffers.setLayoutManager(layoutManager);
        redeem = view.findViewById(R.id.btn_redeem);
        customer_code = view.findViewById(R.id.et_customer_code);
        phone_number = view.findViewById(R.id.et_phone_number);
        error = view.findViewById(R.id.tv_redeem_offer);
        loader = new Loader(getContext());
        loader.show();
        loadOffers();
        onClickBackButton();

        scannerView = view.findViewById(R.id.scanner_view);
        mCodeScanner = new CodeScanner(view.getContext(), scannerView);
        mCodeScanner.setDecodeCallback(new DecodeCallback() {
            @Override
            public void onDecoded(@NonNull final Result result) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ClientRetrofit clientRetrofit = new ClientRetrofit();
                        clientRetrofit.getUserPhone(result.getText(), new UserPhoneInterface() {
                            @Override
                            public void onSuccess(userPhoneResponse responseResult) {
                                if(result.getText()!="" && result.getText()!=null){
                                    Log.d("---scanner---",retailerId);
                                    phone_number.setText(responseResult.getData().getUser_phone());
                                    customer_code.setText( result.getText());
                                }
                            }

                            @Override
                            public void onFailure(Throwable e) {

                            }
                        });

                    }
                });
            }
        });
        mCodeScanner.startPreview();

        customer_code.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus && customer_code.getText().toString()!="" && customer_code.getText().toString()!=null && !customer_code.getText().toString().isEmpty()){
                    ClientRetrofit clientRetrofit = new ClientRetrofit();
                    clientRetrofit.getUserPhone(customer_code.getText().toString(), new UserPhoneInterface() {
                        @Override
                        public void onSuccess(userPhoneResponse responseResult) {
                            Log.d("---response---",customer_code.getText().toString());
                            if(responseResult.getStatus().startsWith("suc")){
                                Log.d("---code---",retailerId);
                                phone_number.setText(responseResult.getData().getUser_phone());
                                phone_number.clearFocus();
                                customer_code.clearFocus();
                            }

                        }

                        @Override
                        public void onFailure(Throwable e) {

                        }
                    });
                }
            }
        });

        phone_number.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus && phone_number.getText().toString()!="" && phone_number.getText().toString()!=null){
                    ClientRetrofit clientRetrofit = new ClientRetrofit();
                    clientRetrofit.getUserCode(phone_number.getText().toString(), new UserCodeInterface() {
                        @Override
                        public void onSuccess(UserCodeResponse responseResult) {
                            if(responseResult.getStatus().startsWith("suc")){
                                customer_code.setText(responseResult.getData().getUser_code());
                                phone_number.clearFocus();
                                customer_code.clearFocus();
                            }

                        }

                        @Override
                        public void onFailure(Throwable e) {

                        }
                    });
                }
            }
        });

        redeem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean code = true,offers = true;
                if(phone_number.getText().toString().trim().equals("") && customer_code.getText().toString().trim().equals("")) {
                    new AlertDialog.Builder(getActivity())
                            .setTitle("Information")
                            .setMessage("Please enter Phone Number or Customer Code")

                            // Specifying a listener allows you to take an action before dismissing the dialog.
                            // The dialog is automatically dismissed when a dialog button is clicked.
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // Continue with delete operation
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                }else if(adapter.getArray_offers() == null || adapter.getArray_offers().length == 0){
                    new AlertDialog.Builder(getActivity())
                            .setTitle("No offers selected")
                            .setMessage("Please choose some offer to redeem")

                            // Specifying a listener allows you to take an action before dismissing the dialog.
                            // The dialog is automatically dismissed when a dialog button is clicked.
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // Continue with delete operation
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                }else{
                    RedeemUserOfferRequest request = new RedeemUserOfferRequest();
                    request.setCustomer_code(customer_code.getText().toString());
                    request.setPhone_number(phone_number.getText().toString());
                    request.setOffer_code(adapter.getArray_offers());
                    ClientRetrofit clientRetrofit = new ClientRetrofit();
                    clientRetrofit.redeemUserOffer(request, new RedeemUserOfferCallback() {
                        @Override
                        public void onSuccess(RedeemUserOfferResponse responseResult) {
                            new AlertDialog.Builder(getActivity())
                                    .setTitle(responseResult.getStatus())
                                    .setMessage(responseResult.getMsg())
                                    // Specifying a listener allows you to take an action before dismissing the dialog.
                                    // The dialog is automatically dismissed when a dialog button is clicked.
                                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            // Continue with delete operation
                                            getActivity().onBackPressed();

                                        }
                                    })
                                    .show();

                        }

                        @Override
                        public void onFailure(Throwable e) {

                        }
                    });
                }
            }
        });


        return view;
    }

    private void loadOffers() {
        ClientRetrofit clientRetrofit = new ClientRetrofit();
        clientRetrofit.getOfferCodes(retailerId, new GetOfferCodeCallback() {
            @Override
            public void onSuccess(GetOfferCodesResponse responseResult) {
                if (responseResult.getStatus().equals("error")) {
                    recyclerViewOffers.setVisibility(View.GONE);
                    error.setVisibility(View.VISIBLE);
                    error.setText(responseResult.getMsg());
                    //adapter = new RedeemOfferAdapter(getActivity().getBaseContext(), responseResult);
                } else {
                    recyclerViewOffers.setVisibility(View.VISIBLE);
                    error.setVisibility(View.GONE);
                    adapter = new RedeemOfferAdapter(getActivity().getBaseContext(), responseResult,lang);
                    recyclerViewOffers.setAdapter(adapter);
                }
                loader.dismiss();
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
