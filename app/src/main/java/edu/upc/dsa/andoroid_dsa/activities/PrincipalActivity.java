package edu.upc.dsa.andoroid_dsa.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Timer;
import java.util.TimerTask;

import edu.upc.dsa.andoroid_dsa.Api;
import edu.upc.dsa.andoroid_dsa.R;
import edu.upc.dsa.andoroid_dsa.RetrofitClient;
import edu.upc.dsa.andoroid_dsa.models.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PrincipalActivity extends AppCompatActivity {

    Api APIservice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.principal_main);
        TimerTask t = new TimerTask() {
            @Override
            public void run() {
                Intent intent = new Intent(PrincipalActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        };
        Timer tiempo = new Timer();
        tiempo.schedule(t,5000);
    }


}