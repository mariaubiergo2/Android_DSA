package edu.upc.dsa.andoroid_dsa.activities;

import static com.google.firebase.messaging.Constants.MessageNotificationKeys.TAG;
import static edu.upc.dsa.andoroid_dsa.activities.LogInActivity.SHARED_PREFS;
import static edu.upc.dsa.andoroid_dsa.activities.LogInActivity.TEXT1;
import static edu.upc.dsa.andoroid_dsa.activities.LogInActivity.TEXT2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

import edu.upc.dsa.andoroid_dsa.Api;
import edu.upc.dsa.andoroid_dsa.R;
import edu.upc.dsa.andoroid_dsa.RetrofitClient;
import edu.upc.dsa.andoroid_dsa.models.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PrincipalActivity extends AppCompatActivity {

    Api APIservice;

    private String text1;
    private String text2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.principal_main);
        loadData();
        subscribeToFirebase();

        TimerTask t = new TimerTask() {
            @Override
            public void run() {
                if(!Objects.equals(text1, "") && !Objects.equals(text2, "")) {
                    Intent intent = new Intent(PrincipalActivity.this, DashBoardActivity.class);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(PrincipalActivity.this, LogInActivity.class);
                    startActivity(intent);
                }
                finish();
            }
        };
        Timer tiempo = new Timer();
        tiempo.schedule(t,5000);
    }

    public void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        text1 = sharedPreferences.getString( TEXT1,"" );
        text2 = sharedPreferences.getString( TEXT2,"" );
    }

    public void subscribeToFirebase() {
        FirebaseMessaging.getInstance().subscribeToTopic("chat")
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        String msg = "Subscribed";
                        if (!task.isSuccessful()) {
                            msg = "Subscribe failed";
                        }
                        Log.d(TAG, msg);
                        Toast.makeText(PrincipalActivity.this, msg, Toast.LENGTH_SHORT).show();
                    }
                });
    }
}