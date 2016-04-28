/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import API.RestClient;
import Model.AllArticlesModel;
import Model.GetFeedsResponse;
import Model.UserModel;
import Model.Feed;
import Model.Feed.FeedPost;
import Model.GetArticleResponse;
import Model.GetArticleResponse.Articles;
import Model.LogoutResponse;
import View.FeedView;
import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 *
 * @author Bastien
 */
public  class FeedController implements FeedView.OnFeedViewEventRaised {
    FeedView feedView;
    JFrame window;
    
    public FeedController(JFrame win) {
        new AllArticlesModel();
        feedView = new FeedView();
        feedView.setOnFeedViewEventRaised(this);
        win.setContentPane(feedView);
        win.getContentPane().repaint();
        win.getContentPane().revalidate();
        window = win;
        getFeedForDisplay();
    }
    
    void getFeedForDisplay() {
        Call<GetFeedsResponse> call = RestClient.get(UserModel.Instance.getToken()).feeds();
        call.enqueue(new Callback<GetFeedsResponse>() {

            @Override
            public void onResponse(Call<GetFeedsResponse> call, Response<GetFeedsResponse> response) {
                if (response.body() != null) {
                    List<Feed> feedList = response.body().getFeeds();
                    if (feedList.size() != 0) {
                        for (Feed f : feedList) {
                            if (!UserModel.Instance.getUserFeeds().contains(f)) {
                                UserModel.Instance.getUserFeeds().add(f);
                            }
                         }
                        feedView.reloadFeed();
                        getAllFeed(-1, 1);
                    }
                } else {
                    System.out.println("GetFeed " + response.code());
                }
            }

            @Override
            public void onFailure(Call<GetFeedsResponse> call, Throwable t) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        });
    }
    
    @Override
    public void getAllFeed(int id, int page) {
        Call<GetArticleResponse> call;
        if (id == -1) {
            call = RestClient.get(UserModel.Instance.getToken()).getAllFeed(page);
        } else {
            call = RestClient.get(UserModel.Instance.getToken()).getAllFeedById(id, page);
        }
        
        call.enqueue(new Callback<GetArticleResponse>() {
            @Override
            public void onResponse(Call<GetArticleResponse> call, Response<GetArticleResponse> response) {
                if (response.body() != null) {
                    if (!response.body().getArticles().isEmpty()) {
                        AllArticlesModel.Instance.setAllArticles(response.body().getArticles());
                        feedView.setCurrentPage(page);
                    }
                    feedView.RefreshAndDumpArticles();
                }
            }

            @Override
            public void onFailure(Call<GetArticleResponse> call, Throwable t) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        } );

    }
    
    @Override
    public void OnAddFeedComplete(String name, String URL) {
        FeedPost p = new FeedPost(name, URL);
        
        Call<Feed> call = RestClient.get(UserModel.Instance.getToken()).addFeed(p);
        call.enqueue(new Callback<Feed>() {
            @Override
            public void onResponse(Call<Feed> call, Response<Feed> response) {
                if (response.body() != null) {
                    System.out.println(response.code());
                    if (!UserModel.Instance.getUserFeeds().contains(response.body())) {
                        UserModel.Instance.getUserFeeds().add(response.body());
                        feedView.addThisFeedToList(response.body().getName());
                    }
                } else {
                    System.out.println(response.code() + " CODE");
                }
            }

            @Override
            public void onFailure(Call<Feed> call, Throwable t) {
                System.out.println("slt");
            }
        });
    }

    @Override
    public void logout() {
        Call<LogoutResponse> call = RestClient.get(UserModel.Instance.getToken()).logout();
        call.enqueue(new Callback<LogoutResponse>() {
            @Override
            public void onResponse(Call<LogoutResponse> call, Response<LogoutResponse> response) {
                if (response.body() != null) {
                    feedView = null;
                    new LoginController(window);
                }
            }

            @Override
            public void onFailure(Call<LogoutResponse> call, Throwable t) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        });
    }
}
