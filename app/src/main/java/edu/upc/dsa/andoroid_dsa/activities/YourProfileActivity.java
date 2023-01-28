package edu.upc.dsa.andoroid_dsa.activities;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.io.IOException;
import java.util.List;

import edu.upc.dsa.andoroid_dsa.Api;
import edu.upc.dsa.andoroid_dsa.R;
import edu.upc.dsa.andoroid_dsa.RetrofitClient;
import edu.upc.dsa.andoroid_dsa.extras.CircleTransform;
import edu.upc.dsa.andoroid_dsa.models.Gadget;
import edu.upc.dsa.andoroid_dsa.models.ProfilePicture;
import edu.upc.dsa.andoroid_dsa.models.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class YourProfileActivity extends AppCompatActivity{
    public String idUser;
    public String username;
    public String surname;
    public String birthday;
    public String email;
    public String password;
    public String coins;
    public String urlPicture;
    public Uri updatedPicture;

    public ImageView profilePicture;
    public List<Gadget> gadgetsOfTheUser;
    private RecyclerView recyclerViewGadgets;
    private RecyclerViewAdapter adapterGadgets;
    Api APIservice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.your_profile_main);
        this.getVariables();
        this.updateLabels();

        this.getUserIdFromDashboard();

        APIservice = RetrofitClient.getInstance().getMyApi();
        Call<List<Gadget>> call = APIservice.purchasedGadgets(this.idUser);

        try {
            this.gadgetsOfTheUser=call.execute().body();

            //adapterGadgets = new RecyclerViewAdapter(call.execute().body(), this);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
        }

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 0);
        }

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 0);
        }

    }
    public void updateLabels(){
        String updateUsername =getString(R.string.updating_username);
        updateUsername=this.username;
        EditText editorUsername = (EditText) findViewById (R.id.user_name);
        editorUsername.setText(updateUsername);
        String updateSurname =getString(R.string.updating_surname);
        updateSurname=this.surname;
        EditText editorSurname = (EditText) findViewById (R.id.sur_name);
        editorSurname.setText(updateSurname);
        String updateBirthday =getString(R.string.updating_birthday);
        updateBirthday=this.birthday;
        EditText editorBirthday = (EditText) findViewById (R.id.birth_day);
        editorBirthday.setText(updateBirthday);
        String updateEmail =getString(R.string.updating_email);
        updateEmail=this.email;
        EditText editorEmail = (EditText) findViewById (R.id.e_mail);
        editorEmail.setText(updateEmail);
        String updatePassword =getString(R.string.updating_password);
        updatePassword=this.password;
        EditText editorPassword = (EditText) findViewById (R.id.pass_word);
        editorPassword.setText(updatePassword);
        String update_coins= getString(R.string.updating_coins);
        update_coins=this.coins;
        EditText editor_coins = (EditText) findViewById(R.id.co_ins);
        editor_coins.setText(update_coins);
        this.profilePicture=findViewById(R.id.yourProfilePicture);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1) {
            Picasso.get().load(this.urlPicture).resize(500,500)
                    .transform(new CircleTransform())
                    .placeholder(R.drawable.castillo)
                    .memoryPolicy(MemoryPolicy.NO_CACHE )
                    .networkPolicy(NetworkPolicy.NO_CACHE)
                    .error(R.drawable.castillo).into(profilePicture);
        }

    }

    public void getVariables() {
        SharedPreferences sharedPreferences = getSharedPreferences("userInformation", Context.MODE_PRIVATE);
        this.username = sharedPreferences.getString("username", null).toString();
        this.surname = sharedPreferences.getString("surname", null).toString();
        this.birthday = sharedPreferences.getString("birthday", null).toString();
        this.email = sharedPreferences.getString("email", null).toString();
        this.password = sharedPreferences.getString("password", null).toString();
        this.coins=sharedPreferences.getString("coins",null).toString();
        this.urlPicture=sharedPreferences.getString("profilePicture",null).toString();
    }

    public void Return(View view){
        Intent intentRegister = new Intent(YourProfileActivity.this, DashBoardActivity.class);
        YourProfileActivity.this.startActivity(intentRegister);
    }
    public void getUserIdFromDashboard(){
        SharedPreferences sharedPreferences = getSharedPreferences("userId", Context.MODE_PRIVATE);
        this.idUser = sharedPreferences.getString("userId", null).toString();
    }

    public void openGadgetsOfUser(View view){
        if(this.gadgetsOfTheUser==null){
            Toast.makeText(YourProfileActivity.this,"You have not bought any gadgets, go to the shop!", Toast.LENGTH_SHORT).show();
            return;
        }
        Intent intent =new Intent(YourProfileActivity.this, GadgetsOfTheUser.class);
        this.saveUserIdAndName(this.idUser,this.username, this.email);
        this.startActivity(intent);
    }
    public void changeLanguageOpen(View view){
        Intent intent = new Intent(YourProfileActivity.this,ChangeLanguageActivity.class);
        this.saveUserIdAndName(this.idUser,this.username, this.email);
        startActivity(intent);
    }
    public void openUpdateOfUser(View view){
        Intent intent=new Intent(YourProfileActivity.this, UpdateOfUserInformation.class);
        this.saveUserIdAndName(this.idUser,this.username,this.email);
        startActivity(intent);

    }
    public void saveUserIdAndName(String userId, String username, String email){
        SharedPreferences sharedPreferences= getSharedPreferences("userIdAndInformation", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor =sharedPreferences.edit();
        editor.putString("userId", userId);
        editor.putString("name",username);
        editor.putString("email",email);
        Log.i("SAVING: ",userId);
        Log.i("SAVING: ",username);
        editor.apply();
    }
    public void editImage(View view){
        loadImage();

    }
    private void loadImage(){
        Intent intent=new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        String[] mimeTypes = {"image/jpeg", "image/png"};
        intent.putExtra(Intent.EXTRA_MIME_TYPES,mimeTypes);
        startActivityForResult(intent,2);

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if(resultCode==RESULT_OK){
            this.updatedPicture=data.getData();
            //profilePicture.setImageURI(this.updatedPicture);
            Picasso.get().load(this.updatedPicture).resize(500,500)
                    .transform(new CircleTransform())
                    .placeholder(R.drawable.castillo)
                    .memoryPolicy(MemoryPolicy.NO_CACHE )
                    .networkPolicy(NetworkPolicy.NO_CACHE)
                    .error(R.drawable.castillo).into(profilePicture);
            this.updateProfilePicture();

        }
    }
    public void updateProfilePicture(){
        String urlUpdated=this.updatedPicture.toString();
        APIservice = RetrofitClient.getInstance().getMyApi();
        ProfilePicture updatedPicture=new ProfilePicture(this.idUser,urlUpdated);
        Call<Void> call = APIservice.updateProfilePicture(updatedPicture);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                switch (response.code()){
                    case 201:
                        Toast.makeText(YourProfileActivity.this,"The profile picture has been correctly updated!", Toast.LENGTH_SHORT).show();
                        break;
                    case 404:
                        Toast.makeText(YourProfileActivity.this,"Your profile picture has not been correctly saved!", Toast.LENGTH_SHORT).show();
                        break;
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });
    }
}
