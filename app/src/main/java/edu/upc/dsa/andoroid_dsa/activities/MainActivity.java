package edu.upc.dsa.andoroid_dsa.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

import edu.upc.dsa.andoroid_dsa.R;

public class MainActivity extends AppCompatActivity {

    Button registerBtn;
    Button loginBtn;
    CheckBox remember;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        registerBtn = (Button) findViewById(R.id.registerBtn);
        loginBtn = (Button) findViewById(R.id.loginBtn);
        remember=findViewById(R.id.rememberBox);

        SharedPreferences preferences = getSharedPreferences("checkbox", MODE_PRIVATE);
        String checkbox = preferences.getString("remember","");
        if (checkbox.equals("true")){
            Intent intent = new Intent(MainActivity.this,GadgetActivity.class);
            startActivity(intent);
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
                    Toast.makeText(MainActivity.this,"Checked", Toast.LENGTH_SHORT).show();
                }
                else if (!compoundButton.isChecked()){
                    SharedPreferences preferences = getSharedPreferences("checkbox", MODE_PRIVATE);
                    SharedPreferences.Editor editor =preferences.edit();
                    editor.putString("remember","false");
                    editor.apply();
                    Toast.makeText(MainActivity.this,"Unchecked", Toast.LENGTH_SHORT).show();
                }

            }
        });
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
