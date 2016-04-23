/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import API.RestClient;
import Model.GetFeedsResponse;
import Model.UserModel;
import Model.Feed;
import Model.Feed.FeedPost;
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
    
    public FeedController(JFrame win) {
        feedView = new FeedView();
        feedView.setOnFeedViewEventRaised(this);
        win.setContentPane(feedView);
        win.getContentPane().repaint();
        win.getContentPane().revalidate();
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
                        DefaultListModel model = new DefaultListModel();
                        model.addElement("Tous");
                        for (Feed f : feedList) {
                            if (!UserModel.Instance.getUserFeeds().contains(f)) {
                                UserModel.Instance.getUserFeeds().add(f);
                            }
                            model.addElement(f.getName());
                            System.out.println(f.getUrl());
                         }
                        feedView.reloadFeedListWithNewModel(model);
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
    public void OnAddFeedComplete(String name, String URL) {
        FeedPost p = new FeedPost(name, URL);
        
        Call<Feed> call = RestClient.get(UserModel.Instance.getToken()).addFeed(p);
        call.enqueue(new Callback<Feed>() {
            @Override
            public void onResponse(Call<Feed> call, Response<Feed> response) {
                if (response.body() != null) {
                    if (!UserModel.Instance.getUserFeeds().contains(response.body())) {
                        UserModel.Instance.getUserFeeds().add(response.body());
                    }
                } else {
                    System.out.println(response.code() + "CODE");
                }
            }

            @Override
            public void onFailure(Call<Feed> call, Throwable t) {
                System.out.println("slt");
            }
        });
    }
}
