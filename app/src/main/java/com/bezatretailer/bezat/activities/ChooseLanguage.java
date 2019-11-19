package com.bezatretailer.bezat.activities;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.bezatretailer.bezat.R;
import com.bezatretailer.bezat.utils.SharedPrefs;

import java.util.Locale;

public class ChooseLanguage extends AppCompatActivity implements View.OnClickListener {
TextView txtArabic,txtEnglish;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (SharedPrefs.getKey(this,"selectedlanguage").contains("ar")) {
            getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        } else {
            getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
        }
        setContentView(R.layout.activity_choose_language);

        txtArabic=findViewById(R.id.txtArabic);
        txtEnglish=findViewById(R.id.txtEnglish);

        txtEnglish.setOnClickListener(this);
        txtArabic.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.txtEnglish)
        {
            setLocale("en");
        }
        else if (view.getId() == R.id.txtArabic)
        {
            setLocale("ar");
        }

    }

    public void setLocale(String lang) {
        SharedPrefs.setKey(ChooseLanguage.this,"selectedlanguage",lang);
        Locale myLocale = new Locale(lang);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);
        startActivity(new Intent(ChooseLanguage.this, LoginActivity.class));
      finish();
    }
}
