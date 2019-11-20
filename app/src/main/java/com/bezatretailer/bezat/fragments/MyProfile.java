package com.bezatretailer.bezat.fragments;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.OrientationHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.bezatretailer.bezat.MyApplication;
import com.bezatretailer.bezat.R;
import com.bezatretailer.bezat.utils.Loader;
import com.bezatretailer.bezat.utils.SharedPrefs;
import com.bezatretailer.bezat.utils.URLS;
import com.bezatretailer.bezat.utils.VolleyMultipartRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MyProfile.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MyProfile#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MyProfile extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    View rootView;
    private OnFragmentInteractionListener mListener;
    private ImageView imgProfile;
    private EditText etName;
    private EditText etAddress;
    private EditText etPhone;
    Loader loader;
    private TextView etCountry, etDob, etGender;
    private String phone_code;
    PostAdapter postAdapter;

    private final int PICK_IMAGE_CAMERA = 1, PICK_IMAGE_GALLERY = 2;

    public MyProfile() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MyProfile.
     */
    // TODO: Rename and change types and number of parameters
    public static MyProfile newInstance(String param1, String param2) {
        MyProfile fragment = new MyProfile();
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
        rootView = inflater.inflate(R.layout.fargment_profile, container, false);
        loader = new Loader(getContext());
        imgProfile = rootView.findViewById(R.id.imgProfile);
        etName = rootView.findViewById(R.id.etName);
        EditText etEmail = rootView.findViewById(R.id.etEmail);
        etCountry = rootView.findViewById(R.id.etCountry);
        etAddress = rootView.findViewById(R.id.etAddress);
        etGender = rootView.findViewById(R.id.etGender);
        etDob = rootView.findViewById(R.id.etDob);
        etPhone = rootView.findViewById(R.id.etPhone);
        TextView txtSave = rootView.findViewById(R.id.txtSave);
        ConstraintLayout editButtonLayout = rootView.findViewById(R.id.edit_profile);

        etDob.setOnClickListener(this);
        etCountry.setOnClickListener(this);
        phone_code = SharedPrefs.getKey(getActivity(), "phone_code");
        etName.setText(SharedPrefs.getKey(getActivity(), "user_name"));
        etEmail.setText(SharedPrefs.getKey(getActivity(), "email"));
        etCountry.setText(SharedPrefs.getKey(getActivity(), "country"));
        etAddress.setText(SharedPrefs.getKey(getActivity(), "address"));
        etGender.setText(SharedPrefs.getKey(getActivity(), "gender"));
        etDob.setText(SharedPrefs.getKey(getActivity(), "dob"));
        etPhone.setText(SharedPrefs.getKey(getActivity(), "phone"));

//        if (SharedPrefs.getKey(getActivity(),"gender").equalsIgnoreCase("Male"))
//        {
//            imgProfile.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.profile));
//        }
//        else {
//            imgProfile.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.ic_femaleicon));
//        }
        etGender.setOnClickListener(this);
        txtSave.setOnClickListener(this);

        loader = new Loader(getContext());

        getCountryList();
//        loader= new Loader(getContext());
//        loader.show();
//        getProfile();


        onCLickEditImage(editButtonLayout);
        return rootView;
    }

    private void onCLickEditImage(ConstraintLayout editButtonLayout) {
        editButtonLayout.setOnClickListener(v -> selectImage());
    }


    private void selectImage() {
        try {
            PackageManager pm = getActivity().getPackageManager();
            int hasPerm = pm.checkPermission(Manifest.permission.CAMERA, getActivity().getPackageName());
            if (hasPerm == PackageManager.PERMISSION_GRANTED) {
                final CharSequence[] options = {"Take Photo", "Choose From Gallery", "Cancel"};
                AlertDialog.Builder builder = new AlertDialog.Builder(
                        getActivity());
                builder.setTitle("Select Option:");
                builder.setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {
                        if (options[item].equals("Take Photo")) {
                            dialog.dismiss();
                            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            startActivityForResult(intent, PICK_IMAGE_CAMERA);

                        } else if (options[item].equals("Choose From Gallery")) {
                            dialog.dismiss();
                            Intent pickPhoto = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            startActivityForResult(pickPhoto, PICK_IMAGE_GALLERY);

                        } else if (options[item].equals("Cancel")) {
                            dialog.dismiss();
                        }
                    }
                });
                builder.show();
            } else
                Toast.makeText(getActivity(), "Camera Permission error", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(getActivity(), "Camera Permission error", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bitmap bitmap;
        if (requestCode == PICK_IMAGE_CAMERA) {
            try {
                bitmap = (Bitmap) data.getExtras().get("data");
                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 50, bytes);
                Log.e("Activity", "Pick from Camera::>>> ");

                int currentBitmapWidth = bitmap.getWidth();
                int currentBitmapHeight = bitmap.getHeight();

                int ivWidth = imgProfile.getWidth();
                int newHeight = (int) Math.floor((double) currentBitmapHeight * ((double) ivWidth / (double) currentBitmapWidth));

                Bitmap newbitMap = Bitmap.createScaledBitmap(bitmap, ivWidth, newHeight, true);

                imgProfile.setImageBitmap(newbitMap);

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (requestCode == PICK_IMAGE_GALLERY) {
            Uri selectedImage = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), selectedImage);
                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 50, bytes);
                Log.e("Activity", "Pick from Gallery::>>> ");
                imgProfile.setImageBitmap(bitmap);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void getCountryList() {
        loader.show();
        JSONObject object = new JSONObject();
        String Url = URLS.Companion.getGET_COUNTRY();

        JsonObjectRequest jsonObjectRequest = new
                JsonObjectRequest(Request.Method.GET,
                        Url,
                        object,
                        response -> {
                            loader.dismiss();
                            try {
                                postAdapter = new PostAdapter(response.getJSONArray("result"));
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

        MyApplication.getInstance().addToRequestQueue(jsonObjectRequest);
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
                            loader.dismiss();
                            Log.v("NotificationResponse", response + " ");
                            try {
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
                                String country = userInfo.getString("country");
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

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        },
                        error -> {
                            loader.dismiss();
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

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.txtSave) {
            loader.show();
            updateProfile(SharedPrefs.getKey(getActivity(), "userId"),
                    etName.getText().toString(),
                    phone_code,
                    etPhone.getText().toString(),
                    etAddress.getText().toString(),
                    etGender.getText().toString(),
                    etDob.getText().toString(),
                    SharedPrefs.getKey(getActivity(), "country_id"),
                    SharedPrefs.getKey(getActivity(), "image")
            );
        }
        if (view.getId() == R.id.etDob) {
            final Calendar c = Calendar.getInstance();
            int mYear = c.get(Calendar.YEAR);
            int mMonth = c.get(Calendar.MONTH);
            int mDay = c.get(Calendar.DAY_OF_MONTH);


            DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {
                            if ((monthOfYear + 1) < 10) {
                                etDob.setText(year + "-" + "0" + (monthOfYear + 1) + "-" + dayOfMonth);
                            } else if (dayOfMonth < 10) {
                                etDob.setText(year + "-" + "0" + (monthOfYear + 1) + "-" + dayOfMonth);
                            } else {
                                etDob.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                            }

                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
        }
        if (view.getId() == R.id.etGender) {

            Dialog dialog = new Dialog(getActivity(), R.style.Theme_AppCompat_Light_Dialog);
            dialog.setContentView(R.layout.gender_dialog);
            dialog.show();
            TextView txtFemale = (TextView) dialog.findViewById(R.id.txtFemale);
            TextView txtMale = (TextView) dialog.findViewById(R.id.txtMale);

            txtFemale.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    etGender.setText("Female");
                    dialog.dismiss();
                }
            });
            txtMale.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    etGender.setText("Male");
                    dialog.dismiss();
                }
            });

        }
        if (view.getId() == R.id.etCountry) {
            countryDialog = new Dialog(getActivity());
            countryDialog.setContentView(R.layout.country_dialog);
            countryDialog.show();
            RecyclerView recCountry = countryDialog.findViewById(R.id.recCountry);
            StaggeredGridLayoutManager layoutManager =
                    new StaggeredGridLayoutManager(1, OrientationHelper.VERTICAL);
            layoutManager.setGapStrategy(
                    StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS);
            recCountry.setLayoutManager(layoutManager);
            recCountry.setItemAnimator(new DefaultItemAnimator());
            if (postAdapter != null && postAdapter.getItemCount() > 0) {
                recCountry.setAdapter(postAdapter);
            }
        }
    }

    Dialog countryDialog;

    private void updateProfile(String userId, String user_name,
                               String phone_code, String phone,
                               String addres, String gender,
                               String dob, String country_id, String image) {

        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, URLS.Companion.getPROFILE_Edit(), new Response.Listener<NetworkResponse>() {
            @Override
            public void onResponse(NetworkResponse response) {
                loader.dismiss();
                String res = new String(response.data);
                if (gender.equalsIgnoreCase("Male")) {
                    imgProfile.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.profile));
                } else {
                    imgProfile.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.ic_femaleicon));
                }
                Log.v("responseprofile", res + "");
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
                params.put("userId", userId);
                params.put("user_name", user_name);
                params.put("phone_code", phone_code);
                params.put("phone", phone);
                params.put("addres", addres);
                params.put("gender", gender);
                params.put("dob", dob);
                params.put("country_id", country_id);
                params.put("image", image);
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
        JSONArray jsonArray;

        public PostAdapter(JSONArray array) {
            this.jsonArray = array;
        }

        public void append(JSONArray array) {
            try {
                for (int i = 0; i < array.length(); i++) {
                    this.jsonArray.put(array.get(i));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.country_list, parent, false);
            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            try {
                holder.txtCountry.setText(jsonArray.getJSONObject(position).getString("name"));

            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        @Override
        public int getItemCount() {
            return jsonArray.length();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {
            TextView txtCountry;

            public MyViewHolder(View itemView) {
                super(itemView);
                txtCountry = itemView.findViewById(R.id.txtCountry);
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        try {
                            phone_code = jsonArray.getJSONObject(getAdapterPosition()).getString("phone_code");
                            etCountry.setText(jsonArray.getJSONObject(getAdapterPosition()).getString("name"));
                            SharedPrefs.setKey(getActivity(), "country_id",
                                    jsonArray.getJSONObject(getAdapterPosition()).getString("country_id"));
                            countryDialog.dismiss();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });

            }
        }
    }
}
