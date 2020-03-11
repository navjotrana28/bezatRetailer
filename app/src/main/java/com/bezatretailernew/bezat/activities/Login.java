package com.bezatretailernew.bezat.activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.bezatretailernew.bezat.MyApplication;
import com.bezatretailernew.bezat.R;
import com.bezatretailernew.bezat.utils.Loader;
import com.bezatretailernew.bezat.utils.SharedPrefs;
import com.bezatretailernew.bezat.utils.URLS;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class Login extends AppCompatActivity {
        Button btnLogin;
        TextView tvSignUp,txtForgot;
        EditText etEmail,etPassword;
        Context context=Login.this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (SharedPrefs.getKey(this,"selectedlanguage").contains("ar")) {
            getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
            setLocale("ar");
        } else {
            getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
        }
        setContentView(R.layout.activity_login);
        etEmail =findViewById(R.id.etEmail);
        etPassword =findViewById(R.id.etPassword);
        tvSignUp = findViewById(R.id.tvSignUp);
        txtForgot =  findViewById(R.id.txtForgot);
        btnLogin=findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(etEmail.getText().toString().isEmpty()){
                    etEmail.setError(getString(R.string.empty_email));
                }
                else if (etPassword.getText().toString().isEmpty())
                {
                    etPassword.setError(getString(R.string.empty_password));
                }
                else {
                    doLogIn(etEmail.getText().toString(),etPassword.getText().toString());
                }
            }
        });
        tvSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Login.this,RegistrationActivity.class));
            }
        });

        txtForgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Login.this,ForgotPassword.class));
            }
        });
    }

    private void doLogIn(String email, String password) {
        Loader loader=new Loader(context);
        loader.show();
        JSONObject object = new JSONObject();
        try {
            object.put("device_id","device_id");
            object.put("email",email);
            object.put("password",password);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String Url= URLS.Companion.getLOGIN_URL();

        JsonObjectRequest jsonObjectRequest = new
                JsonObjectRequest(Request.Method.GET,
                        Url ,
                        object,
                        response -> {
                            loader.dismiss();
                            Log.v("favouriteresponse",response+" ");
                            try {
                                if (response.getString("status").equalsIgnoreCase("success"))
                                {

                                }
                                else if (response.getString("status").equalsIgnoreCase("success"))
                                {

                                }
                                else if (response.getString("status").equalsIgnoreCase("success"))
                                {

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
        jsonObjectRequest.setShouldCache(true);
        MyApplication.getInstance().addToRequestQueue(jsonObjectRequest);
    }

    public void setLocale(String lang) {

        Locale myLocale = new Locale(lang);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);

    }
    private void showMessage(String message){
        android.app.AlertDialog alertDialog = new android.app.AlertDialog.Builder(Login.this,
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
}
