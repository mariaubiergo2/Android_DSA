package edu.upc.dsa.andoroid_dsa.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.List;

import edu.upc.dsa.andoroid_dsa.Api;
import edu.upc.dsa.andoroid_dsa.R;
import edu.upc.dsa.andoroid_dsa.RetrofitClient;
import edu.upc.dsa.andoroid_dsa.extras.CircleTransform;
import edu.upc.dsa.andoroid_dsa.models.ChatMessage;
import edu.upc.dsa.andoroid_dsa.models.User;
import edu.upc.dsa.andoroid_dsa.models.UserId;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RankingActivity extends AppCompatActivity implements RecycleClickViewListener{
    Api APIservice;
    TableLayout tableRanking;

    Button logout;

    EditText firstWinnerEdit, secondWinnerEdit, thirdWinnerEdit;
    ImageView firstWinner, secondWinner, thirdWinner;
    String userId;
    List<User> rankingOfUsers;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ranking_profile);

        APIservice = RetrofitClient.getInstance().getMyApi();
        Call<List<User>> call = APIservice.rankingOfUsers();
        try {
            this.rankingOfUsers=call.execute().body();

        } catch (IOException e) {
            e.printStackTrace();
        }
        this.setWinnerImages(this.rankingOfUsers);
        this.tableRanking=(TableLayout) findViewById(R.id.layoutRanking);
        buildTable(this.rankingOfUsers);

    }

    public void setWinnerImages(List<User> rankingOfUsers) {
        firstWinner=(ImageView) findViewById(R.id.FirstWinnerProfile);
        secondWinner=(ImageView) findViewById(R.id.SecondWinnerProfile);
        thirdWinner=(ImageView) findViewById(R.id.ThirdWinnerProfile);
        firstWinnerEdit=(EditText) findViewById(R.id.textEditTop1);
        secondWinnerEdit=(EditText) findViewById(R.id.textEditTop2);
        thirdWinnerEdit=(EditText) findViewById(R.id.textEditTop3);

        Picasso.get().load(this.rankingOfUsers.get(1).getProfilePicture()).resize(500,500)
                .transform(new CircleTransform())
                .placeholder(R.drawable.castillo)
                .memoryPolicy(MemoryPolicy.NO_CACHE )
                .networkPolicy(NetworkPolicy.NO_CACHE)
                .error(R.drawable.castillo).into(firstWinner);

        Picasso.get().load(this.rankingOfUsers.get(0).getProfilePicture()).resize(500,500)
                .transform(new CircleTransform())
                .placeholder(R.drawable.castillo)
                .memoryPolicy(MemoryPolicy.NO_CACHE )
                .networkPolicy(NetworkPolicy.NO_CACHE)
                .error(R.drawable.castillo).into(secondWinner);

        Picasso.get().load(this.rankingOfUsers.get(2).getProfilePicture()).resize(500,500)
                .transform(new CircleTransform())
                .placeholder(R.drawable.castillo)
                .memoryPolicy(MemoryPolicy.NO_CACHE )
                .networkPolicy(NetworkPolicy.NO_CACHE)
                .error(R.drawable.castillo).into(thirdWinner);

        String updateTop1 =getString(R.string.updating_top1);
        updateTop1 =this.rankingOfUsers.get(1).getName();
        firstWinnerEdit.setText(updateTop1);
        String updateTop2 =getString(R.string.updating_top2);
        updateTop2 =this.rankingOfUsers.get(0).getName();
        secondWinnerEdit.setText(updateTop2);
        String updateTop3 =getString(R.string.updating_top3);
        updateTop3 =this.rankingOfUsers.get(2).getName();
        thirdWinnerEdit.setText(updateTop3);
    }

    @Override
    public void recyclerViewListClicked(int position) {



    }
    public void goBackToDashBoardActivity(View view) {
        Intent intent=new Intent(RankingActivity.this, DashBoardActivity.class);
        startActivity(intent);
    }
    private void buildTable(List<User> rankingOfUsers) {
        assert rankingOfUsers!= null;

        for (User user : rankingOfUsers) {
            View tableRow = LayoutInflater.from(this).inflate(R.layout.ranking_row, null, false);

            TextView playerUsername = tableRow.findViewById(R.id.userGamers);
            TextView userExperience = tableRow.findViewById(R.id.userExperience);
            ImageView imageRanking = tableRow.findViewById(R.id.userImage);

            playerUsername.setText(user.getName());
            userExperience.setText(String.valueOf(user.getExperience()));
            Picasso.get().load(user.getProfilePicture()).resize(25,25)
                    .transform(new CircleTransform())
                    .placeholder(R.drawable.castillo)
                    .memoryPolicy(MemoryPolicy.NO_CACHE )
                    .networkPolicy(NetworkPolicy.NO_CACHE)
                    .error(R.drawable.castillo).into(imageRanking);

            tableRanking.addView(tableRow);
        }

    }


}
