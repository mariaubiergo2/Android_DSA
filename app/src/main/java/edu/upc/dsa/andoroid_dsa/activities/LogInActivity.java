package edu.upc.dsa.andoroid_dsa.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

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

    TextInputEditText emailLoginTxt;
    TextInputEditText passwordTxt;

    Api APIservice;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_main);

    }

    public void doLogin(View view){
        emailLoginTxt = (TextInputEditText) findViewById(R.id.emailLoginTxt);
        passwordTxt = (TextInputEditText) findViewById(R.id.passwordTxt);

        APIservice = RetrofitClient.getInstance().getMyApi();
        Call<User> call = APIservice.logIn(new Credentials(passwordTxt.getText().toString(), emailLoginTxt.getText().toString()));
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                switch (response.code()){
                    case 201:
                        Intent intentRegister = new Intent(LogInActivity.this, MainActivity.class);
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
            public void onFailure(Call<User> call, Throwable t) {
                Snackbar snakyfail = Snackbar.make(view, "NETWORK FAILURE", 3000);
                snakyfail.show();
            }
        });
    }

}