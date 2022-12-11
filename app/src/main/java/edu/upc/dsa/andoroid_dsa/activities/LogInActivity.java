package edu.upc.dsa.andoroid_dsa.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

import edu.upc.dsa.andoroid_dsa.Api;
import edu.upc.dsa.andoroid_dsa.R;
import edu.upc.dsa.andoroid_dsa.RetrofitClient;
import edu.upc.dsa.andoroid_dsa.models.Credentials;
import edu.upc.dsa.andoroid_dsa.models.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LogInActivity extends AppCompatActivity {

    TextInputEditText emailTEXTE;
    TextInputEditText passwordTxt;

    Api APIservice;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_main);

    }

    public void doLogin(View view){
        emailTEXTE = findViewById(R.id.correuText);
        passwordTxt = findViewById(R.id.passwordTxt);

        APIservice = RetrofitClient.getInstance().getMyApi();

        Log.i("PROBLEMA", "els textbox:");
        Log.i("PROBLEMA", emailTEXTE.getText().toString());
        Log.i("PROBLEMA", passwordTxt.getText().toString());

        Credentials credentials = new Credentials(emailTEXTE.getText().toString(), passwordTxt.getText().toString());
        Call<Credentials> call = APIservice.logIn(credentials);

        Log.i("PROBLEMA", "les credentials:");
        Log.i("PROBLEMA", credentials.getEmail());
        Log.i("PROBLEMA", credentials.getPassword());

        call.enqueue(new Callback<Credentials>() {
            @Override
            public void onResponse(Call<Credentials> call, Response<Credentials> response) {
                switch (response.code()){
                    case 201:
                        Intent intentRegister = new Intent(LogInActivity.this, GadgetActivity.class);
                        LogInActivity.this.startActivity(intentRegister);
                        Snackbar snaky201 = Snackbar.make(view, "Correctly login", 3000);
                        snaky201.show();
                        break;

                    case 409:
                        Snackbar snaky409 = Snackbar.make(view, "Wrong credentials!", 3000);
                        snaky409.show();
                        break;
                }
            }

            @Override
            public void onFailure(Call<Credentials> call, Throwable t) {
                Snackbar snakyfail = Snackbar.make(view, "NETWORK FAILURE", 3000);
                snakyfail.show();
            }

        });
    }

    public void returnFunction(View view){
        Intent intentRegister = new Intent(LogInActivity.this, MainActivity.class);
        LogInActivity.this.startActivity(intentRegister);
    }

}