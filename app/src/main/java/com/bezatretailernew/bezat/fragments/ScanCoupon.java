package com.bezatretailernew.bezat.fragments;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
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

import com.bezatretailernew.bezat.R;
import com.bezatretailernew.bezat.activities.ExampleDialog;
import com.bezatretailernew.bezat.utils.Loader;
import com.bezatretailernew.bezat.utils.SharedPrefs;
import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.google.zxing.Result;

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
    EditText customer_phone,customer_code;
    ImageView imgBack;
    String lang = "";
    CodeScannerView scannerView;
    private OnFragmentInteractionListener mListener;
    private String userPhone="";

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
        } else {
            getActivity().getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
            lang = "";
        }
        View view = inflater.inflate(R.layout.fragment_scan_coupon, container, false);

        scannerView = view.findViewById(R.id.scanner_view);
        btnScan = view.findViewById(R.id.btnScan);
        imgBack = view.findViewById(R.id.imgBack);
        mCodeScanner = new CodeScanner(view.getContext(), scannerView);
        customer_code=view.findViewById(R.id.customer_code);
        customer_phone=view.findViewById(R.id.customer_phone);
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
                        getAutoLogin(result.getText(),"");
                        Toast.makeText(getActivity(), result.getText(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

//        scannerView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
                mCodeScanner.startPreview();
//            }
//        });

        ;
        btnScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String qr="";
                String userPhone="";
                String customerCode=customer_code.getText().toString();
                String customerPhone=customer_phone.getText().toString();
                if (customerCode.isEmpty() && customerPhone.isEmpty()){
                    openDialog();
                }else {
                    if (customerCode.isEmpty()) {
                        userPhone = customerPhone;
                        getAutoLogin(qr, userPhone);
                    }
                    else if (customerPhone.isEmpty()){
                        qr = customerCode;
                        getAutoLogin(qr,userPhone);
                    }else {
                        userPhone = customerPhone;
                        qr = customerCode;
                        getAutoLogin(qr, userPhone);
                    }
                }
//                mCodeScanner.setDecodeCallback(new DecodeCallback() {
//                    @Override
//                    public void onDecoded(@NonNull final Result result) {
//                        getActivity().runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {

                            }
//                        });
//                    }
                });
//            }
//        });

        return view;
    }
        public void openDialog(){
            ExampleDialog exampleDialog=new ExampleDialog();
            exampleDialog.show(getFragmentManager(),"example dialog");

        }
    private void getAutoLogin(String qr,String userPhone) {
        Loader loader = new Loader(getContext());
        loader.show();

        String url = "http://bezatapp.com/manage_App/admin/mobile_app/scan_coupon?" + "user_id=" + SharedPrefs.getKey(getActivity(), "userId") +
                "&customer_code=" + qr +"&user_phone=" +userPhone;
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
