package com.bezatretailernew.bezat.fragments;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.bezatretailernew.bezat.ClientRetrofit;
import com.bezatretailernew.bezat.R;
import com.bezatretailernew.bezat.activities.ExampleDialog;
import com.bezatretailernew.bezat.interfaces.UserCodeInterface;
import com.bezatretailernew.bezat.interfaces.UserPhoneInterface;
import com.bezatretailernew.bezat.models.UserCodeResponse;
import com.bezatretailernew.bezat.models.userPhoneResponse;
import com.bezatretailernew.bezat.utils.Loader;
import com.bezatretailernew.bezat.utils.SharedPrefs;
import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.google.zxing.Result;

import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ScanCoupon.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ScanCoupon#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ScanCoupon extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private CodeScanner mCodeScanner;
    Button btnScan;
    EditText customer_phone, customer_code;
    ImageView imgBack;
    String lang = "";
    CodeScannerView scannerView;
    private OnFragmentInteractionListener mListener;
    private String userPhone = "";

    public ScanCoupon() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ScanCoupon.
     */
    // TODO: Rename and change types and number of parameters
    public static ScanCoupon newInstance(String param1, String param2) {
        ScanCoupon fragment = new ScanCoupon();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        container.setClickable(true);
        if (SharedPrefs.getKey(getActivity(), "selectedlanguage").contains("ar")) {
            getActivity().getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
            lang = "_ar";
            setLocale("ar");
        } else {
            getActivity().getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
            lang = "";
            setLocale("en");
        }
        View view = inflater.inflate(R.layout.fragment_scan_coupon, container, false);

        scannerView = view.findViewById(R.id.scanner_view);
        btnScan = view.findViewById(R.id.btnScan);
        imgBack = view.findViewById(R.id.imgBack);
        if(lang.equals("_ar")){
            imgBack.setImageDrawable(getResources().getDrawable(R.drawable.ic_back_rtl));
        }
        mCodeScanner = new CodeScanner(view.getContext(), scannerView);
        customer_code = view.findViewById(R.id.customer_code);
        customer_phone = view.findViewById(R.id.customer_phone);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });
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
                                    getAutoLogin(result.getText(), responseResult.getData().getUser_phone());
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

//        scannerView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
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
                                //Log.d("---code---",retailerId);
                                customer_phone.setText(responseResult.getData().getUser_phone());
                                customer_phone.clearFocus();
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

        customer_phone.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus && customer_phone.getText().toString()!="" && customer_phone.getText().toString()!=null){
                    ClientRetrofit clientRetrofit = new ClientRetrofit();
                    clientRetrofit.getUserCode(customer_phone.getText().toString(), new UserCodeInterface() {
                        @Override
                        public void onSuccess(UserCodeResponse responseResult) {
                            if(responseResult.getStatus().startsWith("suc")){
                                customer_code.setText(responseResult.getData().getUser_code());
                                customer_phone.clearFocus();
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
//            }
//        });

        ;
        btnScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String qr = "";
                String userPhone = "";
                String customerCode = customer_code.getText().toString();
                String customerPhone = customer_phone.getText().toString();
                if (customerCode.isEmpty() && customerPhone.isEmpty()) {
                    openDialog();
                } else {
                    if (customerCode.isEmpty()) {
                        userPhone = customerPhone;
                        getAutoLogin(qr, userPhone);
                    } else if (customerPhone.isEmpty()) {
                        qr = customerCode;
                        getAutoLogin(qr, userPhone);
                    } else {
                        userPhone = customerPhone;
                        qr = customerCode;
                        getAutoLogin(qr, userPhone);
                    }
                }
            }
        });
        return view;
    }

    public void openDialog() {
        ExampleDialog exampleDialog = new ExampleDialog();
        exampleDialog.show(getFragmentManager(), "example dialog");

    }

    public void setLocale(String lang) {

        Locale myLocale = new Locale(lang);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);

    }

    private void getAutoLogin(String qr, String userPhone) {
        Loader loader = new Loader(getContext());
        loader.show();

        String url = "https://bezatapp.com/manage_App/admin/mobile_app/scan_coupon?" + "user_id=" + SharedPrefs.getKey(getActivity(), "userId") +
                "&customer_code=" + qr + "&user_phone=" + userPhone;
        Log.v("couponurl", url + " ");
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.container, new WebViewCoupon(url));
        ft.addToBackStack(null);
        ft.commit();
        loader.dismiss();
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

//    @Override
//    public void onResume() {
//        super.onResume();
//        mCodeScanner.startPreview();
//    }

    @Override
    public void onPause() {
        super.onPause();
        mCodeScanner.releaseResources();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
