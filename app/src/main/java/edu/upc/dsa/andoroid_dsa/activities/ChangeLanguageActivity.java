package edu.upc.dsa.andoroid_dsa.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;

import java.util.Locale;

import edu.upc.dsa.andoroid_dsa.R;

public class ChangeLanguageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_language);
    }

    public void changeSpanish(View view){
        setLocale("es-rES");
    }
    public void changeCatalan(View view){
        setLocale("ca-rES");
    }
    public void changeEnglish(View view){
        setLocale("en");
    }

    private void setLocale(String language) {
        Resources resources = getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        Configuration configuration = resources.getConfiguration();
        configuration.locale = new Locale(language);
        resources.updateConfiguration(configuration, metrics);
        onConfigurationChanged(configuration);
        Intent intentRegister = new Intent(ChangeLanguageActivity.this, ChangeLanguageActivity.class);
        this.startActivity(intentRegister);
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfiguration) {
        super.onConfigurationChanged(newConfiguration);
        //set strings from resources
    }
    public void return_toProfile(View view){
        Intent intent = new Intent(ChangeLanguageActivity.this,YourProfileActivity.class);
        startActivity(intent);
    }
}