package com.bezatretailer.bezat.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.bezatretailer.bezat.MyApplication;
import com.bezatretailer.bezat.R;
import com.bezatretailer.bezat.utils.Loader;
import com.bezatretailer.bezat.utils.SharedPrefs;
import com.bezatretailer.bezat.utils.URLS;
import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.google.zxing.Result;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    ImageView imgBack;
    private OnFragmentInteractionListener mListener;

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
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_scan_coupon, container, false);

        CodeScannerView scannerView = view.findViewById(R.id.scanner_view);
        btnScan = view.findViewById(R.id.btnScan);
        mCodeScanner = new CodeScanner(getActivity(), scannerView);
        imgBack = view.findViewById(R.id.imgBack);

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
                        getAutoLogin();
                        Toast.makeText(getActivity(), result.getText(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        scannerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCodeScanner.startPreview();
            }
        });

        btnScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getAutoLogin();
            }
        });
        return view;
    }

    private void getAutoLogin() {
        Loader loader=new Loader(getContext());
        loader.show();
        JSONObject object = new JSONObject();
//        try {
//            object.put("userId", SharedPrefs.getKey(getActivity(),"userId"));
//            object.put("storeId", SharedPrefs.getKey(getActivity(),"storeID"));
//            object.put("device_id","device_id");
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
        String Url= URLS.Companion.getAUTOLOGIN()+"userId="+SharedPrefs.getKey(getActivity(),"userId")+
                "&storeId="+SharedPrefs.getKey(getActivity(),"storeID")+
                "&device_id=android";
        Log.v("couponurl",Url+" ");
        JsonObjectRequest jsonObjectRequest = new
                JsonObjectRequest(Request.Method.GET,
                        Url ,
                        object,
                        response -> {
                            loader.dismiss();
                            Log.v("vipcustomer",response+" ");
                            try {
                                String url = response.getJSONObject("result").getString("url");
                                Intent i = new Intent(Intent.ACTION_VIEW);
                                i.setData(Uri.parse(url));
                                startActivity(i);
                            } catch (JSONException e) {
                                e.printStackTrace();

                            }
                        },
                        error -> {
                            loader.dismiss();
                        }) {
                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        HashMap<String, String> headers = new HashMap<String, String>();
                        headers.put("apikey", "12345678");
                        return headers;
                    }
                };
        jsonObjectRequest.setShouldCache(false);
        MyApplication.getInstance().addToRequestQueue(jsonObjectRequest);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        mCodeScanner.startPreview();
    }

    @Override
    public void onPause() {
        mCodeScanner.releaseResources();
        super.onPause();
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
