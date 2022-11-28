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
import edu.upc.dsa.andoroid_dsa.models.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    TextInputEditText nameTxt;
    TextInputEditText surnameTxt;

    Api APIservice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_main);

    }

    public void doRegister(View view){
        nameTxt = (TextInputEditText) findViewById(R.id.nameTxt);
        surnameTxt = (TextInputEditText) findViewById(R.id.surnameTxt);

        APIservice = RetrofitClient.getInstance().getMyApi();
        Call<User> call = APIservice.createUser(new User("Allba","Cognom" ,"kj", "nou@gmail.com", "1234"));
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                switch (response.code()){
                    case 201:
                        Intent intentRegister = new Intent(RegisterActivity.this, MainActivity.class);
                        RegisterActivity.this.startActivity(intentRegister);
                        break;

                    case 409:
                        Snackbar snaky409 = Snackbar.make(view, "This user already exists!", 3000);
                        snaky409.show();
                        break;

                    case 500:
                        Snackbar snaky500 = Snackbar.make(view, "Empty register...", 3000);
                        snaky500.show();
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