package edu.upc.dsa.andoroid_dsa.activities;

import static edu.upc.dsa.andoroid_dsa.activities.LogInActivity.SHARED_PREFS;
import static edu.upc.dsa.andoroid_dsa.activities.LogInActivity.TEXT1;
import static edu.upc.dsa.andoroid_dsa.activities.LogInActivity.TEXT2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.util.List;

import edu.upc.dsa.andoroid_dsa.Api;
import edu.upc.dsa.andoroid_dsa.R;
import edu.upc.dsa.andoroid_dsa.RetrofitClient;
import edu.upc.dsa.andoroid_dsa.models.Gadget;
import retrofit2.Call;

public class GadgetActivity extends AppCompatActivity {

    //TableLayout tableLayout;

    Api APIservice;

    Button logout;

    private RecyclerView recyclerViewGadgets;
    private RecyclerViewAdapter adaptadorGadgets;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gadget_shop_activity);
        //recyclerViewGadgets= new RecyclerView(this);
        recyclerViewGadgets=(RecyclerView)findViewById(R.id.recyclerGadget);
        Log.d("DDDD", ""+recyclerViewGadgets);
        recyclerViewGadgets.setLayoutManager(new LinearLayoutManager(this));
        //tableLayout = findViewById(R.id.tableLayout);
        //logout =findViewById(R.id.logOutBtn);
        APIservice = RetrofitClient.getInstance().getMyApi();
        Call<List<Gadget>> call = APIservice.getGadgets();
        try {
            adaptadorGadgets= new RecyclerViewAdapter(call.execute().body());
            //buildTable(call);
        } catch (IOException e) {
            e.printStackTrace();
        }
        recyclerViewGadgets.setAdapter(adaptadorGadgets);
    }
    public void btnClicked(View view) throws IOException {
        SharedPreferences preferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor =preferences.edit();
        editor.putString(TEXT1, "");
        editor.putString(TEXT2, "");
        editor.apply();
        Intent intent = new Intent(GadgetActivity.this, MainActivity.class);
        GadgetActivity.this.startActivity(intent);
    }

    public void returnFunction(View view){
        Intent intentRegister = new Intent(GadgetActivity.this, MainActivity.class);
        GadgetActivity.this.startActivity(intentRegister);
    }
    public void buyItem(View view){

    }

}