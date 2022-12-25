package edu.upc.dsa.andoroid_dsa.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;

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

    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String TEXT1 = "email";
    public static final String TEXT2 = "password";

    Api APIservice;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_main);
        emailTEXTE = findViewById(R.id.correuText);
        passwordTxt = findViewById(R.id.passwordTxt);

        Toast.makeText(this,"Please Sign In.", Toast.LENGTH_SHORT).show();
    }
    public void saveData() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(TEXT1, emailTEXTE.getText().toString());
        editor.putString(TEXT2, passwordTxt.getText().toString());

        editor.apply();

        Toast.makeText(this, "Data saved", Toast.LENGTH_SHORT).show();
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
                        saveData();

                        Intent intentRegister = new Intent(LogInActivity.this, DashBoardActivity.class);
                        LogInActivity.this.startActivity(intentRegister);
                        Toast.makeText(LogInActivity.this,"Correctly login", Toast.LENGTH_SHORT).show();
                        break;

                    case 409:
                        Toast.makeText(LogInActivity.this,"Wrong credentials!", Toast.LENGTH_SHORT).show();
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
}