package edu.upc.dsa.andoroid_dsa.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import edu.upc.dsa.andoroid_dsa.Api;
import edu.upc.dsa.andoroid_dsa.R;
import edu.upc.dsa.andoroid_dsa.RetrofitClient;
import edu.upc.dsa.andoroid_dsa.models.ChatMessage;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChatActivity extends AppCompatActivity {
    Api APIservice;

    String name;
    Integer num;

    TableLayout tableChat;
    TextInputEditText messageInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_forum_users);
        this.tableChat=(TableLayout) findViewById(R.id.layoutChats);
        this.messageInput =(TextInputEditText) findViewById(R.id.messageGamer);
        this.getVariables();
        this.num = 0;

        APIservice = RetrofitClient.getInstance().getMyApi();

        Call<List<ChatMessage>> call = APIservice.getChat(this.num);

        try {
            buildTable(call.execute().body());
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }

    public void getVariables() {
        SharedPreferences sharedPreferences = getSharedPreferences("nameForChat", Context.MODE_PRIVATE);
        this.name = sharedPreferences.getString("username", null).toString();
    }

    private void buildTable(List<ChatMessage> messages) {
        assert messages != null;

        for (ChatMessage message : messages) {
            View tableRow = LayoutInflater.from(this).inflate(R.layout.message_gamer, null, false);

            TextView playerUsername = tableRow.findViewById(R.id.gamerName);
            TextView messageSent = tableRow.findViewById(R.id.gamerMessage);

            playerUsername.setText(message.getName());
            messageSent.setText(message.getMessage());

            tableChat.addView(tableRow);
        }
        this.num += messages.size();
    }

    public void postMessage(View view) {
        if(!messageInput.getText().toString().equals("")){
            ChatMessage chatMessage =new ChatMessage(this.name,this.messageInput.getText().toString());
            Call<Void> call = APIservice.newMessage(chatMessage);
            call.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    switch (response.code()){
                        case 201:
                            Snackbar snaky201 = Snackbar.make(view, "The message has been done correctly!", 3000);
                            snaky201.show();
                            break;
                        case 403:
                            Snackbar snaky403 = Snackbar.make(view, "Database error when reporting issue", 3000);
                            snaky403.show();
                            break;
                        case 500:
                            Snackbar snaky500 = Snackbar.make(view, "The name has not been set successfully", 3000);
                            snaky500.show();
                            break;
                    }
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    Snackbar snakyfail = Snackbar.make(view, "NETWORK FAILURE", 3000);
                    snakyfail.show();
                }
            });
            ArrayList<ChatMessage> messages = new ArrayList<>();
            messages.add(new ChatMessage(this.name, messageInput.getText().toString()));
            buildTable(messages);
        }
    }

    public void updateChat(View view) {
        updateTable();
    }

    public void goBackToDashBoardActivity(View view) {
        Intent intent=new Intent(ChatActivity.this, DashBoardActivity.class);
        startActivity(intent);
    }

    private void updateTable() {
        Call<List<ChatMessage>> call = APIservice.getChat(this.num);
        try {
            buildTable(call.execute().body());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

