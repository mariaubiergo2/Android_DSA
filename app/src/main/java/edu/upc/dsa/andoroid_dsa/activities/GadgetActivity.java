package edu.upc.dsa.andoroid_dsa.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

import java.io.IOException;
import java.util.List;

import edu.upc.dsa.andoroid_dsa.Api;
import edu.upc.dsa.andoroid_dsa.R;
import edu.upc.dsa.andoroid_dsa.RetrofitClient;
import edu.upc.dsa.andoroid_dsa.models.Gadget;
import edu.upc.dsa.andoroid_dsa.models.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GadgetActivity extends AppCompatActivity {

    TableLayout tableLayout;

    Api APIservice;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gadget_list_main);
        tableLayout = findViewById(R.id.tableLayout);

        APIservice = RetrofitClient.getInstance().getMyApi();
        Call<List<Gadget>> call = APIservice.getGadgets();
        try {
            buildTable(call);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressLint("SetTextI18n")
    private void buildTable(Call<List<Gadget>> call) throws IOException {
        List<Gadget> gadgets = call.execute().body();
        assert gadgets != null;

        for (Gadget gadget : gadgets) {
            View tableRow = LayoutInflater.from(this).inflate(R.layout.table_gadget_item, null, false);

            TextView gadgetId = tableRow.findViewById(R.id.gadgetId);
            TextView gadgetCost = tableRow.findViewById(R.id.gadgetCost);
            TextView gadgetDescription = tableRow.findViewById(R.id.gadgetDescription);
            TextView gadgetUnityShape = tableRow.findViewById(R.id.gadgetUnityShape);

            gadgetId.setText(gadget.getId());
            gadgetCost.setText(Integer.toString(gadget.getCost()));
            gadgetDescription.setText(gadget.getDescription());
            gadgetUnityShape.setText(gadget.getUnityShape());

            tableLayout.addView(tableRow);
        }
    }
}