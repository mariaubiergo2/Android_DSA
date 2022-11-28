package edu.upc.dsa.andoroid_dsa.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

import edu.upc.dsa.andoroid_dsa.Api;
import edu.upc.dsa.andoroid_dsa.R;
import edu.upc.dsa.andoroid_dsa.RetrofitClient;
import edu.upc.dsa.andoroid_dsa.models.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    Button registerBtn;
    Button loginBtn;
    Button listBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        registerBtn = (Button) findViewById(R.id.registerBtn);
        loginBtn = (Button) findViewById(R.id.loginBtn);
        listBtn = (Button) findViewById(R.id.listBtn);
    }

    public void btnClicked(View view) throws IOException {
        if(view==registerBtn){
            Intent intentRegister = new Intent(MainActivity.this, RegisterActivity.class);
            MainActivity.this.startActivity(intentRegister);
        }
        if(view==loginBtn){
            Intent intentRegister = new Intent(MainActivity.this, LogInActivity.class);
            MainActivity.this.startActivity(intentRegister);
        }
    }
}
