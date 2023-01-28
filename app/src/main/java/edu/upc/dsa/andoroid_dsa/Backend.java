package edu.upc.dsa.andoroid_dsa;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import edu.upc.dsa.andoroid_dsa.models.GadgetName;
import edu.upc.dsa.andoroid_dsa.models.GameInfo;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Backend {

    public static List<String> loadGame(String userId) {
        List<String> info = new ArrayList<>();
        Call<List<GadgetName>> call = RetrofitClient.getInstance().getMyApi().loadGame(userId);
        try{
            List<GadgetName> gadgetNames = call.execute().body();
            for(GadgetName gadgetName : gadgetNames) {
                info.add(gadgetName.getName());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return info;
    }

    public void saveGameInfo(String idUser, String coins, String completed){
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(coins).append("/");
        stringBuffer.append(completed);
        Call<Void> call = RetrofitClient.getInstance().getMyApi().saveGame(new GameInfo(idUser, stringBuffer.toString()));
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {

            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });
    }
}
