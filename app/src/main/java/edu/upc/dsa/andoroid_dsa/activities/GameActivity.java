package edu.upc.dsa.andoroid_dsa.activities;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;

import com.unity3d.player.UnityPlayer;
import com.unity3d.player.UnityPlayerActivity;

import java.util.List;

import edu.upc.dsa.andoroid_dsa.Backend;

public class GameActivity extends UnityPlayerActivity {
    String idUser;
    List<String> info;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle content = getIntent().getExtras();
        if(content != null){
            this.idUser = content.getString("idUser");
        }
        info = Backend.loadGame(idUser);

        loadGame();
    }

    public void loadGame(){
        UnityPlayer.UnitySendMessage("Player(Clone)", "SetPlayerId", idUser);
        for(String element : info){
            UnityPlayer.UnitySendMessage("Player(Clone)", "SetElement", element);
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mUnityPlayer.configurationChanged(newConfig);
    }

    public void finishActivity()
    {
        Intent intent=new Intent(UnityPlayer.currentActivity, DashBoardActivity.class);
        UnityPlayer.currentActivity.startActivity(intent);
        UnityPlayer.currentActivity.finish();
    }
}
