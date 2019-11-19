package com.bezatretailer.bezat.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.*;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.OrientationHelper;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import com.android.volley.*;
import com.android.volley.toolbox.JsonObjectRequest;
import com.bezatretailer.bezat.MyApplication;
import com.bezatretailer.bezat.R;
import com.bezatretailer.bezat.activities.ChangePassword;
import com.bezatretailer.bezat.activities.Login;
import com.bezatretailer.bezat.activities.LoginActivity;
import com.bezatretailer.bezat.utils.Loader;
import com.bezatretailer.bezat.utils.SharedPrefs;
import com.bezatretailer.bezat.utils.URLS;
import com.bezatretailer.bezat.utils.VolleyMultipartRequest;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link vipcustomer.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link vipcustomer#newInstance} factory method to
 * create an instance of this fragment.
 */
public class vipcustomer extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    Button btnaddVip;
    View rootView;
    LinearLayout layoutContent;
    ImageView imgSearch,imgBack;
    TextView txtCustomerName,txtCustomerCode,txtCustomerEmail,txtCustomerPhone;
    EditText etSearch;
    Loader loader;
    private OnFragmentInteractionListener mListener;

    public vipcustomer() {
        // Required empty public constructor
    }

    public static vipcustomer newInstance(String param1, String param2) {
        vipcustomer fragment = new vipcustomer();
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

        rootView = inflater.inflate(R.layout.fragment_vipcustomer, container, false);

        txtCustomerName = rootView.findViewById(R.id.txtCustomerName);
        txtCustomerCode = rootView.findViewById(R.id.txtCustomerCode);
        txtCustomerEmail = rootView.findViewById(R.id.txtCustomerEmail);
        txtCustomerPhone = rootView.findViewById(R.id.txtCustomerPhone);
        btnaddVip = rootView.findViewById(R.id.btnaddVip);
        etSearch = rootView.findViewById(R.id.etSearch);
        imgSearch = rootView.findViewById(R.id.imgSearch);
        imgBack = rootView.findViewById(R.id.imgBack);
        layoutContent = rootView.findViewById(R.id.layoutContent);
        loader=new Loader(getContext());

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });

        imgSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (etSearch.getText().toString().isEmpty())
                {
                    etSearch.setError("Field is empty");
                }
                else {
                    loader.show();
                    searchData(etSearch.getText().toString());
                }
            }
        });
        btnaddVip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addVipCustomer(customerId, SharedPrefs.getKey(getActivity(),"userId")
                        , SharedPrefs.getKey(getActivity(),"storeID"));
            }
        });
        return rootView;
    }

    private void addVipCustomer(String customerId,String userId,String storeId) {
        loader.show();
        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST,
                URLS.Companion.getADDVIP(), new Response.Listener<NetworkResponse>() {
            @Override
            public void onResponse(NetworkResponse response) {
                loader.dismiss();
                String res = new String(response.data);
                Log.v("changepassword",res);
                try {
                    JSONObject jsonObject=new JSONObject(res);
                    showMessage(jsonObject.getString("message"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loader.dismiss();
                String json = null;
                String Message;
                NetworkResponse response = error.networkResponse;
                Log.v("response",response.data+"");
            }
        }) {
            @Override
            public Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("customerId", customerId);
                params.put("userId",userId);
                params.put("storeId",storeId);

                System.out.println("object"+params+" ");
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("apikey", "12345678");
                return headers;
            }

            @Override
            protected Map<String, VolleyMultipartRequest.DataPart> getByteData() throws AuthFailureError {
                Map<String, VolleyMultipartRequest.DataPart> params = new HashMap<>();
                return params;
            }
        };
        MyApplication.getInstance().addToRequestQueue(volleyMultipartRequest);
    }

    String customerId="";
    private void searchData(String data) {
        JSONObject object = new JSONObject();
        String Url= URLS.Companion.getSEARCH()+data;
        JsonObjectRequest jsonObjectRequest = new
                JsonObjectRequest(Request.Method.GET,
                        Url ,
                        object,
                        response -> {
                            loader.dismiss();
                            Log.v("vipcustomer",response+" ");
                            try {
                                layoutContent.setVisibility(View.VISIBLE);
                                 customerId=response.getJSONObject("result").getString("customerId");
                                String customerName=response.getJSONObject("result").getString("customerName");
                                String customerEmail=response.getJSONObject("result").getString("customerEmail");
                                String customerCode=response.getJSONObject("result").getString("customerCode");
                                String customerPhone=response.getJSONObject("result").getString("customerPhone");
                                txtCustomerName.setText(customerName);
                                txtCustomerEmail.setText(customerEmail);
                                txtCustomerCode.setText(customerCode);
                                txtCustomerPhone.setText(customerPhone);

                            } catch (JSONException e) {
                                e.printStackTrace();
                                layoutContent.setVisibility(View.GONE);
                                AlertDialog alertDialog = new AlertDialog.Builder(getActivity(),R.style.DialogTheme).create();
                                alertDialog.setMessage(getString(R.string.no_result_found));
                                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, getString(R.string.ok),
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.dismiss();
                                            }
                                        });
                                alertDialog.show();
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
    public void onAttach(Context context) {
        super.onAttach(context);

    }
    private void showMessage(String message){
        android.app.AlertDialog alertDialog = new android.app.AlertDialog.Builder(getActivity(),
                R.style.DialogTheme).create();
        alertDialog.setMessage(message);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, getString(R.string.ok),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }
    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {

        void onFragmentInteraction(Uri uri);
    }

}
