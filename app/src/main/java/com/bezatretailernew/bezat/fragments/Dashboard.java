package com.bezatretailernew.bezat.fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.DimenRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.bezatretailernew.bezat.ClientRetrofit;
import com.bezatretailernew.bezat.MyApplication;
import com.bezatretailernew.bezat.R;
import com.bezatretailernew.bezat.activities.LoginActivity;
import com.bezatretailernew.bezat.adapter.SliderAdapter;
import com.bezatretailernew.bezat.interfaces.GetOfferCodeCallback;
import com.bezatretailernew.bezat.interfaces.LogoutCallback;
import com.bezatretailernew.bezat.models.DashBoardItem;
import com.bezatretailernew.bezat.models.LogoutResponse;
import com.bezatretailernew.bezat.models.getOfferCodes.GetOfferCodesResponse;
import com.bezatretailernew.bezat.utils.SharedPrefs;
import com.bezatretailernew.bezat.utils.URLS;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Dashboard.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Dashboard#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Dashboard extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    JSONArray jsonArray;
    List<String> iconName;
    RecyclerView recycle;
    ViewPager viewPager;
    TabLayout indicator;
    String language = "";
    List<DashBoardItem> dashBoardItem;
    View rootView;
    String lang = "";
    String retailerId;

    public Dashboard() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static Dashboard newInstance(String param1, String param2) {
        Dashboard fragment = new Dashboard();
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
        retailerId = SharedPrefs.getKey(getActivity(), "storeID");
        if (SharedPrefs.getKey(getActivity(), "selectedlanguage").contains("ar")) {
            getActivity().getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
            lang = "_ar";
            setLocale("ar");
            language = "ar";
        } else {
            getActivity().getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
            lang = "";
            setLocale("en");
            language = "en";
        }

        Log.d("---yo---",SharedPrefs.getKey(getActivity(), "storeID"));

        rootView = inflater.inflate(R.layout.fragment_dashboard, container, false);
        initViewPager();
        setDashboardData();
        getProfile();
        return rootView;
    }

    private void initViewPager() {

        viewPager = rootView.findViewById(R.id.viewPager);
        indicator = rootView.findViewById(R.id.indicator);
        getUserBanner();
    }

    private void getUserBanner() {

        JSONObject object = new JSONObject();
        JsonObjectRequest jsonObjectRequest = new
                JsonObjectRequest(Request.Method.GET,
                        URLS.Companion.getUSER_BANNER(),
                        object,
                        response -> {
                            try {

                                jsonArray = response.getJSONArray("result");
                                viewPager.setAdapter(new SliderAdapter(getActivity(), jsonArray));
                                indicator.setupWithViewPager(viewPager, false);

                                Timer timer = new Timer();
                                timer.scheduleAtFixedRate(new SliderTimer(), 4000, 6000);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        },
                        error -> {
                            Log.v("error", error.getMessage() + " ");


                        }) {
                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        HashMap<String, String> headers = new HashMap<String, String>();
                        headers.put("apikey", "12345678");
                        return headers;
                    }
                };

        MyApplication.getInstance().addToRequestQueue(jsonObjectRequest);
    }

    private class SliderTimer extends TimerTask {

        @Override
        public void run() {
            try {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (viewPager.getCurrentItem() < jsonArray.length() - 1) {
                            viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
                        } else {
                            viewPager.setCurrentItem(0);
                        }
                    }
                });
            } catch (NullPointerException ne) {
                ne.printStackTrace();
            }
        }
    }

    private void setDashboardData() {

        recycle = rootView.findViewById(R.id.recycle);
        dashBoardItem = dashBoardItem = new ArrayList<>();
        dashBoardItem.add(new DashBoardItem(
                R.drawable.total_coupons,
                getString(R.string.total_coupon) + ""
        ));
        dashBoardItem.add(new DashBoardItem(
                R.drawable.qr_code,
                getString(R.string.scan_coupon) + ""
        ));

        dashBoardItem.add(new DashBoardItem(
                R.drawable.vip_logo1,
                getString(R.string.vip_customers) + ""
        ));
        dashBoardItem.add(new DashBoardItem(
                R.drawable.fav_offers,
                getString(R.string.offers) + ""
        ));
        dashBoardItem.add(new DashBoardItem(
                R.drawable.prize2,
                getString(R.string.prizes) + ""
        ));
        dashBoardItem.add(new DashBoardItem(
                R.drawable.get_packages,
                getString(R.string.packages) + ""
        ));
        dashBoardItem.add(new DashBoardItem(
                R.drawable.redeem_offer,
                getString(R.string.redeem_offer) + ""
        ));

        dashBoardItem.add(new DashBoardItem(
                R.drawable.feedback,
                getString(R.string.get_feedback) + ""
        ));
//        dashBoardItem.add(new DashBoardItem(
//                R.drawable.createoffer,
//                getString(R.string.add_offer)+""
//        ));
//        dashBoardItem.add(new DashBoardItem(
//                R.drawable.purchase,
//                getString(R.string.purchase)+""
//        ));

        dashBoardItem.add(new DashBoardItem(
                R.drawable.logout,
                getString(R.string.sign_out) + ""
        ));

        PostAdapter postAdapter = new PostAdapter(dashBoardItem);

        recycle.setNestedScrollingEnabled(false);
        recycle.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        ItemOffsetDecoration itemDecoration = new ItemOffsetDecoration(getActivity(), R.dimen.gap);
        recycle.addItemDecoration(itemDecoration);
        recycle.setItemAnimator(new DefaultItemAnimator());
        if (postAdapter != null && postAdapter.getItemCount() > 0) {

            recycle.setAdapter(postAdapter);
        }


    }

    public class ItemOffsetDecoration extends RecyclerView.ItemDecoration {

        private int mItemOffset;

        public ItemOffsetDecoration(int itemOffset) {
            mItemOffset = itemOffset;
        }

        public ItemOffsetDecoration(@NonNull Context context, @DimenRes int itemOffsetId) {
            this(context.getResources().getDimensionPixelSize(itemOffsetId));
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
                                   RecyclerView.State state) {
            super.getItemOffsets(outRect, view, parent, state);
            outRect.set(mItemOffset, mItemOffset, mItemOffset, mItemOffset);
        }
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
        List<DashBoardItem> dashBoardItems;

        public PostAdapter(List<DashBoardItem> dashBoardItems) {
            this.dashBoardItems = dashBoardItems;
        }


        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.row_dashboard_item, parent, false);
            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            try {

                holder.text.setText(dashBoardItems.get(position).getName() + " ");
//                    holder.image.setBackground(ContextCompat.getDrawable(getActivity(),
//                            dashBoardItems.get(position).getDrawable()));
                holder.image.setImageDrawable(ContextCompat.getDrawable(getActivity(),
                        dashBoardItems.get(position).getDrawable()));

            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        @Override
        public int getItemCount() {
            return dashBoardItems.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {

            TextView text;
            ImageView image;

            public MyViewHolder(View itemView) {
                super(itemView);

                text = itemView.findViewById(R.id.text);
                image = itemView.findViewById(R.id.image);


                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (dashBoardItems.get(getAdapterPosition())
                                .getName().equalsIgnoreCase(getString(R.string.sign_out))) {
                            new AlertDialog.Builder(getActivity(), R.style.DialogTheme)
                                    .setMessage(getString(R.string.logout_confirm))

                                    // Specifying a listener allows you to take an action before dismissing the dialog.
                                    // The dialog is automatically dismissed when a dialog button is clicked.
                                    .setPositiveButton(getString(R.string.yes_label), new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            ClientRetrofit retrofit = new ClientRetrofit();
                                            retrofit.logOutAPi(SharedPrefs.getKey(getActivity(), "userId"), new LogoutCallback() {
                                                @Override
                                                public void onSuccess(LogoutResponse responseResult) {
                                                    SharedPrefs.deleteSharedPrefs(getActivity());
                                                    setLocale(language);
                                                    startActivity(new Intent(getActivity(), LoginActivity.class));
                                                    getActivity().finish();
                                                }

                                                @Override
                                                public void onFailure(Throwable e) {
                                                    Toast.makeText(getContext(), getString(R.string.someting_wrong), Toast.LENGTH_SHORT).show();
                                                }
                                            });

                                        }
                                    })

                                    // A null listener allows the button to dismiss the dialog and take no further action.
                                    .setNegativeButton(getString(R.string.no_label), null)
                                    .setIcon(android.R.drawable.ic_dialog_alert)
                                    .show();

                        } else if (dashBoardItems.get(getAdapterPosition())
                                .getName().equalsIgnoreCase(getString(R.string.total_coupon))) {
                            FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                            ft.replace(R.id.container, new TotalCoupon());
                            ft.addToBackStack(null);
                            ft.commit();

                        } else if (dashBoardItems.get(getAdapterPosition())
                                .getName().equalsIgnoreCase(getString(R.string.scan_coupon))) {
                            FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                            ft.replace(R.id.container, new ScanCoupon());
                            ft.addToBackStack(null);
                            ft.commit();

                        } else if (dashBoardItems.get(getAdapterPosition())
                                .getName().equalsIgnoreCase(getString(R.string.prizes))) {
                            FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                            ft.replace(R.id.container, new Prizes());
                            ft.addToBackStack(null);
                            ft.commit();

                        } else if (dashBoardItems.get(getAdapterPosition())
                                .getName().equalsIgnoreCase(getString(R.string.vip_customers))) {
                            FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                            ft.replace(R.id.container, new vipcustomer());
                            ft.addToBackStack(null);
                            ft.commit();

                        } else if (dashBoardItems.get(getAdapterPosition())
                                .getName().equalsIgnoreCase(getString(R.string.offers))) {

                            FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                            ft.replace(R.id.container, new Offers());
                            ft.addToBackStack(null);
                            ft.commit();
                        } else if (dashBoardItems.get(getAdapterPosition())
                                .getName().equalsIgnoreCase(getString(R.string.add_offer))) {
                            Intent i = new Intent(Intent.ACTION_VIEW);
                            i.setData(Uri.parse("http://bezatapp.com/manage_App"));
                            startActivity(i);

                        } else if (dashBoardItems.get(getAdapterPosition())
                                .getName().equalsIgnoreCase(getString(R.string.get_feedback))) {
                            FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                            ft.replace(R.id.container, new Feedback());
                            ft.addToBackStack(null);
                            ft.commit();
//                            Intent i = new Intent(Intent.ACTION_VIEW);
//                            i.setData(Uri.parse("http://bezatapp.com/manage_App"));
//                            startActivity(i);

                        } else if (dashBoardItems.get(getAdapterPosition())
                                .getName().equalsIgnoreCase(getString(R.string.packages))) {
                            FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                            ft.replace(R.id.container, new PurchaseCoupons());
                            ft.addToBackStack(null);
                            ft.commit();
//                            Intent i = new Intent(Intent.ACTION_VIEW);
//                            i.setData(Uri.parse("http://bezatapp.com/manage_App"));
//                            startActivity(i);

                        } else if (dashBoardItems.get(getAdapterPosition()).getName().equalsIgnoreCase(getString(R.string.redeem_offer))) {

                            ClientRetrofit clientRetrofit = new ClientRetrofit();
                            clientRetrofit.getOfferCodes(retailerId, new GetOfferCodeCallback() {
                                @Override
                                public void onSuccess(GetOfferCodesResponse responseResult) {
                                    if (responseResult.getStatus().equals("error")) {
                                        Toast.makeText(getActivity().getBaseContext(), responseResult.getMsg(), Toast.LENGTH_SHORT).show();
                                    } else {
                                        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                                        ft.replace(R.id.container, new RedeemOffer());
                                        ft.addToBackStack(null);
                                        ft.commit();
                                    }

                                }

                                @Override
                                public void onFailure(Throwable e) {

                                }
                            });


//                            Intent i = new Intent(Intent.ACTION_VIEW);
//                            i.setData(Uri.parse("http://bezatapp.com/manage_App"));
//                            startActivity(i);

                        } else if (dashBoardItems.get(getAdapterPosition())
                                .getName().equalsIgnoreCase(getString(R.string.purchase))) {
                            Intent i = new Intent(Intent.ACTION_VIEW);
                            i.setData(Uri.parse("http://bezatapp.com/manage_App"));
                            startActivity(i);

                        }


                    }
                });

            }
        }
    }

    private void getProfile() {

        JSONObject object = new JSONObject();
        String vipUrl = URLS.Companion.getUSER_PROFILE()
                + "userId=" + SharedPrefs.getKey(getActivity(), "userId");
        Log.v("profile", vipUrl + " ");
        JsonObjectRequest jsonObjectRequest = new
                JsonObjectRequest(Request.Method.GET,
                        vipUrl,
                        object,
                        response -> {

                            Log.v("NotificationResponse", response + " ");
                            try {
                                SharedPrefs.setKey(getActivity(), "userId", response.getString("userID"));
                                JSONObject userInfo = response.getJSONObject("userInfo");
                                String user_code = userInfo.getString("user_code");
                                SharedPrefs.setKey(getActivity(), "user_code", user_code);
                                String user_name = userInfo.getString("user_name");
                                SharedPrefs.setKey(getActivity(), "user_name", user_name);
                                String user_type = userInfo.getString("user_type");
                                SharedPrefs.setKey(getActivity(), "user_type", user_type);
                                String email = userInfo.getString("email");
                                SharedPrefs.setKey(getActivity(), "email", email);
                                String phone_code = userInfo.getString("phone_code");
                                SharedPrefs.setKey(getActivity(), "phone_code", phone_code);
                                String phone = userInfo.getString("phone");
                                SharedPrefs.setKey(getActivity(), "phone", phone);
                                String push_notification_status = userInfo.getString("push_notification_status");
                                SharedPrefs.setKey(getActivity(), "push_notification_status", push_notification_status);
                                String image = userInfo.getString("image");
                                SharedPrefs.setKey(getActivity(), "image", image);
                                String address = userInfo.getString("address");
                                SharedPrefs.setKey(getActivity(), "address", address);
                                String country_id = userInfo.getString("country_id");
                                SharedPrefs.setKey(getActivity(), "country_id", country_id);
                                String country = userInfo.getString("country" + lang);
                                SharedPrefs.setKey(getActivity(), "country", country);
                                String language_id = userInfo.getString("language_id");
                                SharedPrefs.setKey(getActivity(), "language_id", language_id);
                                String language_name = userInfo.getString("language_name");
                                SharedPrefs.setKey(getActivity(), "language_name", language_name);
                                String country_ar = userInfo.getString("country_ar");
                                SharedPrefs.setKey(getActivity(), "country_ar", country_ar);
                                String gender = userInfo.getString("gender");
                                SharedPrefs.setKey(getActivity(), "gender", gender);
                                String dob = userInfo.getString("dob");
                                SharedPrefs.setKey(getActivity(), "dob", dob);

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        },
                        error -> {

                            Log.v("NotificationError", error.toString() + " ");
                        }) {
                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        HashMap<String, String> headers = new HashMap<String, String>();
                        headers.put("apikey", "12345678");
                        return headers;
                    }
                };

        MyApplication.getInstance().addToRequestQueue(jsonObjectRequest);
    }

    public void setLocale(String lang) {
        SharedPrefs.setKey(getActivity(), "selectedlanguage", lang);
        Locale myLocale = new Locale(lang);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);
    }

}
