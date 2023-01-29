package edu.upc.dsa.andoroid_dsa.activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Calendar;

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
    EditText birthdayTxt;
    TextInputEditText emailTxt;
    TextInputEditText passwordRegisterTxt;
    DatePickerDialog picker;

    Api APIservice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_main);


        birthdayTxt = findViewById(R.id.birthdayTxt);
        birthdayTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar calendar = Calendar.getInstance();
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);

                picker = new DatePickerDialog(RegisterActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                        birthdayTxt.setText(dayOfMonth+"/"+month+"/"+year);
                    }
                },year, month, day);
                picker.show();
            }
        });

    }

    public void doRegister(View view){
        nameTxt = findViewById(R.id.nameTxt);
        surnameTxt = findViewById(R.id.surnameTxt);
        emailTxt = findViewById(R.id.emailTxt);
        passwordRegisterTxt = findViewById(R.id.passwordRegisterTxt);
        String defaultProfilePicture="https://i.pinimg.com/236x/e9/57/2a/e9572a70726980ed5445c02e1058760b.jpg";
        User user = new User(nameTxt.getText().toString(), surnameTxt.getText().toString(), birthdayTxt.getText().toString(), emailTxt.getText().toString(), passwordRegisterTxt.getText().toString(),50, 1, defaultProfilePicture);

        APIservice = RetrofitClient.getInstance().getMyApi();
        Call<User> call = APIservice.createUser(user);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                switch (response.code()){
                    case 201:
                        Intent intentRegister = new Intent(RegisterActivity.this, LogInActivity.class);
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

    public void returnFunction(View view){
        Intent intentRegister = new Intent(RegisterActivity.this, LogInActivity.class);
        RegisterActivity.this.startActivity(intentRegister);
    }
}