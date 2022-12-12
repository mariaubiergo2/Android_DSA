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
    CheckBox remember;

    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String TEXT1 = "email";
    public static final String TEXT2 = "password";
    public static final String CHECK = "rememberBox";

    Api APIservice;
    private String text1;
    private String text2;
    private boolean checkboxOnOff;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_main);
        remember=findViewById(R.id.rememberBox);
        emailTEXTE = findViewById(R.id.correuText);
        passwordTxt = findViewById(R.id.passwordTxt);


        SharedPreferences preferences = getSharedPreferences("checkbox", MODE_PRIVATE);
        String checkbox = preferences.getString("remember","");
        if (checkbox.equals("true")){
            //Intent intent = new Intent(LogInActivity.this,GadgetActivity.class);
            //startActivity(intent);
            loadData();
            updateViews();
            doLogin(null);
        }
        else if(checkbox.equals("false")){
            Toast.makeText(this,"Please Sign In.", Toast.LENGTH_SHORT).show();

        }
        remember.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(compoundButton.isChecked()){
                    SharedPreferences preferences = getSharedPreferences("checkbox", MODE_PRIVATE);
                    SharedPreferences.Editor editor =preferences.edit();
                    editor.putString("remember","true");
                    editor.apply();
                    Toast.makeText(LogInActivity.this,"Checked", Toast.LENGTH_SHORT).show();
                }
                else if (!compoundButton.isChecked()){
                    SharedPreferences preferences = getSharedPreferences("checkbox", MODE_PRIVATE);
                    SharedPreferences.Editor editor =preferences.edit();
                    editor.putString("remember","false");
                    editor.apply();
                    Toast.makeText(LogInActivity.this,"Unchecked", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
    public void saveData() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(TEXT1, emailTEXTE.getText().toString());
        editor.putString(TEXT2, passwordTxt.getText().toString());
        editor.putBoolean(CHECK, remember.isChecked());

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
                        remember.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                saveData();
                            }
                        });

                        Intent intentRegister = new Intent(LogInActivity.this, GadgetActivity.class);
                        LogInActivity.this.startActivity(intentRegister);
                        //Snackbar snaky201 = Snackbar.make(view, "Correctly login", 3000);
                        //snaky201.show();
                        Toast.makeText(LogInActivity.this,"Correctly login", Toast.LENGTH_SHORT).show();
                        break;

                    case 409:
                        Toast.makeText(LogInActivity.this,"Wrong credentials!", Toast.LENGTH_SHORT).show();
                        //Snackbar snaky409 = Snackbar.make(view, "Wrong credentials!", 3000);
                        //snaky409.show();
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
    public void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        text1 = sharedPreferences.getString(TEXT1, "");
        text2 = sharedPreferences.getString(TEXT2, "");
        checkboxOnOff = sharedPreferences.getBoolean(CHECK, false);
    }

    public void updateViews() {
        emailTEXTE.setText(text1);
        passwordTxt.setText(text2);
        remember.setChecked(checkboxOnOff);

    }

    public void returnFunction(View view){
        Intent intentRegister = new Intent(LogInActivity.this, MainActivity.class);
        LogInActivity.this.startActivity(intentRegister);
    }

}