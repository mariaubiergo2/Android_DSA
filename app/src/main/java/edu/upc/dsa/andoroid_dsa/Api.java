package edu.upc.dsa.andoroid_dsa;

import edu.upc.dsa.andoroid_dsa.models.Credentials;
import edu.upc.dsa.andoroid_dsa.models.User;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface Api {
    @POST("shop/user/register")
    Call<User> createUser(@Body User user);

    @POST("shop/user/login")
    Call<User> logIn(@Body Credentials credentials);
}
