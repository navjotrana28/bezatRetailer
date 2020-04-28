package com.bezatretailernew.bezat.fragments;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.OrientationHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.bezatretailernew.bezat.MyApplication;
import com.bezatretailernew.bezat.R;
import com.bezatretailernew.bezat.utils.Loader;
import com.bezatretailernew.bezat.utils.SharedPrefs;
import com.bezatretailernew.bezat.utils.URLS;
import com.bruce.pickerview.popwindow.DatePickerPopWin;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link TotalCoupon.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link TotalCoupon#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TotalCoupon extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    View rootView;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    RecyclerView recycleTotalCoupons;
    Loader loader;
    String currentDate;
    TextView txtDate;
    ImageView imgSearch;
    ImageView imgBack;
    String lang = "";
    SearchView searchView;
    private OnFragmentInteractionListener mListener;
//    CouponAdapter adapter;
//    private CouponResult couponResult = new CouponResult();
//    private CouponData couponData = new CouponData();
//    private List<CouponDetails> couponDetails;

    public TotalCoupon() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TotalCoupon.
     */
    // TODO: Rename and change types and number of parameters
    public static TotalCoupon newInstance(String param1, String param2) {
        TotalCoupon fragment = new TotalCoupon();
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
        if (SharedPrefs.getKey(getActivity(), "selectedlanguage").contains("ar")) {
            getActivity().getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
            lang = "_ar";
        } else {
            getActivity().getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
            lang = "";
        }
        rootView = inflater.inflate(R.layout.fragment_total_coupon, container, false);
        recycleTotalCoupons = rootView.findViewById(R.id.recycleTotalCoupons);
        txtDate = rootView.findViewById(R.id.txtDate);
        imgSearch = rootView.findViewById(R.id.imgSearch);
        imgBack = rootView.findViewById(R.id.imgBack);
        searchView = rootView.findViewById(R.id.search_view);
        loader = new Loader(getContext());
        loader.show();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM", Locale.ENGLISH);
        Date date = new Date();
        currentDate = formatter.format(date);
        txtDate.setText(currentDate);
        getTotalCoupon();
//        getTotalCoupons();

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });

        imgSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerPopWin pickerPopWin = new DatePickerPopWin.Builder(getActivity(), new DatePickerPopWin.OnDatePickedListener() {
                    @Override
                    public void onDatePickCompleted(int year, int month, int day, String dateDesc) {
//                        Toast.makeText(getActivity(), dateDesc, Toast.LENGTH_SHORT).show();
                        if ((month + 1) < 10) {
                            txtDate.setText(year + "-0" + (month));
                        } else {
                            txtDate.setText(year + "-" + (month));
                        }
                        currentDate = txtDate.getText().toString();
                        loader.show();
                        getTotalCoupon();
                    }
                }).textConfirm("Done") //text of confirm button
                        .textCancel("Cancel") //text of cancel button
                        .btnTextSize(16) // button text size
                        .viewTextSize(25) // pick view text size
                        .colorCancel(Color.parseColor("#ffffff")) //color of cancel button
                        .colorConfirm(Color.parseColor("#ffffff"))//color of confirm button
                        .minYear(1990) //min year in loop
                        .maxYear(2100) // max year in loop

                        .build();
                pickerPopWin.showPopWin(getActivity());
            }
        });
        txtDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerPopWin pickerPopWin = new DatePickerPopWin.Builder(getActivity(), new DatePickerPopWin.OnDatePickedListener() {
                    @Override
                    public void onDatePickCompleted(int year, int month, int day, String dateDesc) {
//                        Toast.makeText(getActivity(), dateDesc, Toast.LENGTH_SHORT).show();
                        if ((month + 1) < 10) {
                            txtDate.setText(year + "-0" + (month));
                        } else {
                            txtDate.setText(year + "-" + (month));
                        }
                        currentDate = txtDate.getText().toString();
                        loader.show();
                        getTotalCoupon();
                    }
                }).textConfirm("Done") //text of confirm button
                        .textCancel("Cancel") //text of cancel button
                        .btnTextSize(16) // button text size
                        .viewTextSize(25) // pick view text size
                        .colorCancel(Color.parseColor("#ffffff")) //color of cancel button
                        .colorConfirm(Color.parseColor("#ffffff"))//color of confirm button
                        .minYear(1990) //min year in loop
                        .maxYear(2100) // max year in loop

                        .build();
                pickerPopWin.showPopWin(getActivity());
            }
        });
        return rootView;
    }

//    private void getTotalCoupons() {
//        loadSeachData();
//
//    }

//    private void loadSeachData() {
//        ClientRetrofit clientRetrofit = new ClientRetrofit();
//        clientRetrofit.totalCouponsResult(SharedPrefs.getKey(getActivity(), "userId"), currentDate, new TotalCouponsCallback() {
//            @Override
//            public void onSuccess(CouponResult responseResult) {
//                loader.dismiss();
//                couponResult = responseResult;
//                adapter.setDatumList(responseResult);
//                adapter.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onFailure(Throwable e) {
//
//            }
//        });
//    }


    private void getTotalCoupon() {
        JSONObject object = new JSONObject();
        String Url = URLS.Companion.getTOTAL_COUPON() + "userId=" + SharedPrefs.getKey(getActivity(), "userId")
                + "&storeId=" + SharedPrefs.getKey(getActivity(), "storeID");
//                + "&year_month=" + currentDate;

        JsonObjectRequest jsonObjectRequest = new
                JsonObjectRequest(Request.Method.GET,
                        Url,
                        object,
                        response -> {
                            loader.dismiss();
                            try {
                                PostAdapter postAdapter = new PostAdapter(response.getJSONObject("result"));
                                StaggeredGridLayoutManager layoutManager =
                                        new StaggeredGridLayoutManager(1, OrientationHelper.VERTICAL);
                                layoutManager.setGapStrategy(
                                        StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS);
                                recycleTotalCoupons.setLayoutManager(layoutManager);
                                recycleTotalCoupons.setItemAnimator(new DefaultItemAnimator());
                                if (postAdapter != null && postAdapter.getItemCount() > 0) {
                                    recycleTotalCoupons.setAdapter(postAdapter);
                                    recycleTotalCoupons.setVisibility(View.VISIBLE);
                                } else {
                                    recycleTotalCoupons.setVisibility(View.GONE);
                                    Toast.makeText(getActivity(), "No Coupons Available", Toast.LENGTH_SHORT).show();
                                }
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

    private class PostAdapter extends RecyclerView.Adapter<PostAdapter.MyViewHolder> {
        JSONObject jsonArray;

        public PostAdapter(JSONObject array) {
            this.jsonArray = array;
        }

//        public void append(JSONArray array) {
//            try {
//                for (int i = 0; i < array.length(); i++) {
//                    this.jsonArray.put(array.get(i));
//                }
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//        }

        @Override
        public PostAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.total_coupon_item, parent, false);
            return new PostAdapter.MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(PostAdapter.MyViewHolder holder, int position) {
            try {

//                holder.availTicket.setText(jsonArray.getJSONObject(position).getString("available_tickets"));
                holder.availTicket.setText(jsonArray.getString("available_tickets"));
                Picasso.get().load(jsonArray.getString("store_logo"))
                        .into(holder.imgCoupon);


            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        @Override
        public int getItemCount() {
            return jsonArray.length() - 1;
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {

            TextView availTicket, ticketNo;
            ImageView imgCoupon;

            public MyViewHolder(View itemView) {
                super(itemView);

                availTicket = itemView.findViewById(R.id.availTicket);
                imgCoupon = itemView.findViewById(R.id.imgCoupon);

                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {


                    }
                });

            }
        }
    }
}