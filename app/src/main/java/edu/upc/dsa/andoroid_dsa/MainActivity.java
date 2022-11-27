package edu.upc.dsa.andoroid_dsa;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

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

    public void btnClicked(View view){

        //if(view==registerBtn){
            Intent intentRegister = new Intent(MainActivity.this, RegisterActivity.class);
            MainActivity.this.startActivity(intentRegister);
        //}



    }




}
