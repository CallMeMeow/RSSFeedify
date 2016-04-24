/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package API;

import Model.Feed;
import Model.Feed.FeedPost;
import Model.GetArticleResponse;
import Model.GetFeedsResponse;
import Model.LoginResponse;
import Model.LoginResponse.LoginPost;
import Model.RegisterResponse;
import Model.RegisterResponse.RegisterPost;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 *
 * @author Bastien
 */
public interface ApiService {
    @POST("register")
    Call<RegisterResponse>      register(@Body RegisterPost rPost);
    
    @POST("login")
    Call<LoginResponse>     login(@Body LoginPost post);
    
    @GET("feeds")
    Call<GetFeedsResponse>  feeds();
    
    @POST("feed")
    Call<Feed>  addFeed(@Body FeedPost post);
    
    @GET("feeds/articles/{id}")
    Call<GetArticleResponse>      getAllFeed(@Path("id") int page);

    @GET("feed/{feedid}/articles/{page}")
    Call<GetArticleResponse>      getAllFeedById(@Path("feedid") int id, @Path("page") int page);

}
