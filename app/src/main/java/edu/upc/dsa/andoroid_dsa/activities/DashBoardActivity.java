package edu.upc.dsa.andoroid_dsa.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import edu.upc.dsa.andoroid_dsa.R;

public class DashBoardActivity extends AppCompatActivity implements View.OnClickListener {
    public CardView yourProfile, gadgetShop, logOut;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard_main);
        yourProfile=(CardView) findViewById(R.id.profiles);
        gadgetShop=(CardView) findViewById(R.id.gadgetCard);
        logOut=(CardView) findViewById(R.id.logOutCard);
        yourProfile.setOnClickListener(this);
        gadgetShop.setOnClickListener(this);
        logOut.setOnClickListener(this);
    }
    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        Intent i;
        switch(view.getId()){
            case R.id.profiles:
                i=new Intent(this,YourProfileActivity.class);
                startActivity(i);
                break;
            case R.id.gadgetCard:
                i=new Intent(this,GadgetActivity.class);
                startActivity(i);
                break;
            case R.id.logOutCard:
                i=new Intent(this, PrincipalActivity.class);
                startActivity(i);
                break;
        }

    }
}
