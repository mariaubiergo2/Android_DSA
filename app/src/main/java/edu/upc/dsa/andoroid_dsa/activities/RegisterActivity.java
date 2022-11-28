package edu.upc.dsa.andoroid_dsa.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

import edu.upc.dsa.andoroid_dsa.R;

public class RegisterActivity extends AppCompatActivity {

    TextInputEditText nameTxt;
    TextInputEditText surnameTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_main);

    }

    public void doRegister(View view){
        nameTxt = (TextInputEditText) findViewById(R.id.nameTxt);
        surnameTxt = (TextInputEditText) findViewById(R.id.surnameTxt);

    }




}