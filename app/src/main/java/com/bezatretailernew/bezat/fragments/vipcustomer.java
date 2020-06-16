package com.bezatretailernew.bezat.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.bezatretailernew.bezat.ClientRetrofit;
import com.bezatretailernew.bezat.MyApplication;
import com.bezatretailernew.bezat.R;
import com.bezatretailernew.bezat.adapter.VipAdapter;
import com.bezatretailernew.bezat.interfaces.VipListResponse;
import com.bezatretailernew.bezat.models.vip_lists.VipResult;
import com.bezatretailernew.bezat.utils.Loader;
import com.bezatretailernew.bezat.utils.SharedPrefs;
import com.bezatretailernew.bezat.utils.URLS;
import com.bezatretailernew.bezat.utils.VolleyMultipartRequest;
import com.squareup.picasso.Picasso;

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
    LinearLayout layoutContent, vip_list_linear;
    ImageView imgSearch, imgBack,imgFront;
    TextView txtCustomerName, txtCustomerCode, txtCustomerEmail, txtCustomerPhone;
    EditText etSearch;
    Loader loader;
    private VipAdapter adapter;
    GridLayoutManager layoutManager;
    private RecyclerView recyclerView;
    String lang="a";
    SearchView searchView;


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
        container.setClickable(true);
        rootView = inflater.inflate(R.layout.fragment_vipcustomer, container, false);

        if (SharedPrefs.getKey(getActivity().getBaseContext(), "selectedlanguage").contains("ar")) {
            lang = "a";
        } else {
            lang = "e";
        }

        txtCustomerName = rootView.findViewById(R.id.txtCustomerName);
        txtCustomerCode = rootView.findViewById(R.id.txtCustomerCode);
        txtCustomerEmail = rootView.findViewById(R.id.txtCustomerEmail);
        txtCustomerPhone = rootView.findViewById(R.id.txtCustomerPhone);
        if(lang.equals("a")){
            txtCustomerName.setGravity(Gravity.RIGHT);
            txtCustomerCode.setGravity(Gravity.RIGHT);
            txtCustomerEmail.setGravity(Gravity.RIGHT);
            txtCustomerPhone.setGravity(Gravity.RIGHT);
        }else{
            txtCustomerName.setGravity(Gravity.LEFT);
            txtCustomerCode.setGravity(Gravity.LEFT);
            txtCustomerEmail.setGravity(Gravity.LEFT);
            txtCustomerPhone.setGravity(Gravity.LEFT);
        }
        imgFront = rootView.findViewById(R.id.imgFront);
        String path = SharedPrefs.getKey(getActivity(), "image");
        if (path.equals("")) {
            Picasso.get().load(R.drawable.maleicon).into(imgFront);

        } else {
            Picasso.get().load(path).into(imgFront);
        }
        btnaddVip = rootView.findViewById(R.id.btnaddVip);
        searchView = rootView.findViewById(R.id.search_total_coupons);
        EditText searchEditText = ((EditText)((SearchView)rootView.findViewById(R.id.search_total_coupons)).findViewById(((SearchView)rootView.findViewById(R.id.search_total_coupons)).getContext().getResources().getIdentifier("android:id/search_src_text", null, null)));
        searchEditText.setTextColor(getResources().getColor(R.color.black));
        searchEditText.setHintTextColor(getResources().getColor(R.color.black));
        imgSearch = rootView.findViewById(R.id.imgSearch);
        imgBack = rootView.findViewById(R.id.imgBack);
        layoutContent = rootView.findViewById(R.id.layoutContent);
        vip_list_linear = rootView.findViewById(R.id.vip_list_linear);
        recyclerView = rootView.findViewById(R.id.recycler_view_01);
        loader = new Loader(getContext());
        loader.show();
        initViews();

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.d("---test---",query);
                loader.show();
                searchData(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return true;
            }

        });

        /*searchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (searchView.getQuery().toString().isEmpty()) {
                    searchEditText.setError("Field is empty");
                } else {
                    loader.show();
                    searchData(searchView.getQuery().toString());
                }
            }
        });*/

        btnaddVip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addVipCustomer(customerId, SharedPrefs.getKey(getActivity(), "userId")
                        , SharedPrefs.getKey(getActivity(), "storeID"));
            }
        });
        return rootView;
    }

    private void initViews() {
        getVIPLists();
    }

    private void getVIPLists() {
        ClientRetrofit clientRetrofit = new ClientRetrofit();
        clientRetrofit.vipListData(SharedPrefs.getKey(getActivity(), "userId"), new VipListResponse() {
            @Override
            public void onSuccess(VipResult response) {
                if (response.getResult().size() > 0) {
                    adapter = new VipAdapter(getActivity().getBaseContext(),lang);
                    layoutManager = new GridLayoutManager(getActivity(), 2);
                    adapter.setData(response);
                    recyclerView.setLayoutManager(layoutManager);
                    recyclerView.setItemAnimator(new DefaultItemAnimator());
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    vip_list_linear.setVisibility(View.VISIBLE);
                    loader.dismiss();
                } else {
                    loader.dismiss();
                    Toast.makeText(getActivity(), getString(R.string.no_vip_list_found), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Throwable e) {

            }
        });
    }


    private void addVipCustomer(String customerId, String userId, String storeId) {
        loader.show();
        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST,
                URLS.Companion.getADDVIP(), new Response.Listener<NetworkResponse>() {
            @Override
            public void onResponse(NetworkResponse response) {
                loader.dismiss();
                String res = new String(response.data);
                Log.v("changepassword", res);
                try {
                    JSONObject jsonObject = new JSONObject(res);
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
                Log.v("response", response.data + "");
            }
        }) {
            @Override
            public Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("customerId", customerId);
                params.put("userId", userId);
                params.put("storeId", storeId);
                System.out.println("object" + params + " ");
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

    String customerId = "";

    private void searchData(String data) {
        Log.d("---test---","Search clicked");
        JSONObject object = new JSONObject();
        String Url = URLS.Companion.getSEARCH() + data;
        JsonObjectRequest jsonObjectRequest = new
                JsonObjectRequest(Request.Method.GET, Url, object,
                        response -> {
                            loader.dismiss();
                            Log.v("vipcustomer", response + " ");
                            try {
                                layoutContent.setVisibility(View.VISIBLE);
                                btnaddVip.setVisibility(View.VISIBLE);
                                customerId = response.getJSONObject("result").getString("customerId");
                                String customerName = response.getJSONObject("result").getString("customerName");
                                String customerEmail = response.getJSONObject("result").getString("customerEmail");
                                String customerCode = response.getJSONObject("result").getString("customerCode");
                                String customerPhone = response.getJSONObject("result").getString("customerPhone");
                                txtCustomerName.setText(customerName);
                                txtCustomerEmail.setText(customerEmail);
                                txtCustomerCode.setText(customerCode);
                                txtCustomerPhone.setText(customerPhone);
                                try {
                                    String path = response.getJSONObject("result").getString("customerImage");
                                    Picasso.get().load(path).into(imgFront);
                                } catch (Exception e) {
                                    Log.d("", e.toString());
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                                layoutContent.setVisibility(View.GONE);
                                btnaddVip.setVisibility(View.GONE);
                                AlertDialog alertDialog = new AlertDialog.Builder(getActivity(), R.style.DialogTheme).create();
                                alertDialog.setMessage(getString(R.string.no_result_found));
                                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, getString(R.string.ok),
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.dismiss();
                                                vip_list_linear.setVisibility(View.VISIBLE);
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

    private void showMessage(String message) {
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
