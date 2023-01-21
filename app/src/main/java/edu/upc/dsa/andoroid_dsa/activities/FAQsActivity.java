package edu.upc.dsa.andoroid_dsa.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.util.List;

import edu.upc.dsa.andoroid_dsa.Api;
import edu.upc.dsa.andoroid_dsa.R;
import edu.upc.dsa.andoroid_dsa.RetrofitClient;
import edu.upc.dsa.andoroid_dsa.models.FAQ;
import retrofit2.Call;

public class FAQsActivity extends AppCompatActivity implements RecycleClickViewListener {

    Api APIservice;

    private RecyclerView recyclerViewFAQs;
    private RecyclerViewAdapterFAQ adapterFAQs;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.facs2_activity);

        recyclerViewFAQs = (RecyclerView) findViewById(R.id.recyclerFAQ);
        recyclerViewFAQs.setLayoutManager(new LinearLayoutManager(this));

        APIservice = RetrofitClient.getInstance().getMyApi();
        Call<List<FAQ>> call = APIservice.getFAQs();

        try {
            adapterFAQs = new RecyclerViewAdapterFAQ(call.execute().body(), this);
        } catch (IOException e) {
            e.printStackTrace();
        }
        recyclerViewFAQs.setAdapter(adapterFAQs);
    }

    @Override
    public void recyclerViewListClicked(int position) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }

    public void returnFunction(View view) {
        Intent intent=new Intent(FAQsActivity.this, DashBoardActivity.class);
        startActivity(intent);
    }
}