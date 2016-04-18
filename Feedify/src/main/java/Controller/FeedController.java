/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import API.RestClient;
import Model.GetFeedsResponse;
import Model.UserModel;
import Model.feed;
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
public  class FeedController {
    FeedView feedView;
    
    public FeedController(JFrame win) {
        feedView = new FeedView();
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
                    List<feed> feedList = response.body().getFeeds();
                    if (feedList.size() != 0) {
                        DefaultListModel model = new DefaultListModel();
                        model.addElement("Tous");
                        for (feed f : feedList) {
                             model.addElement(f.getName());
                             System.out.println(f.getUtl());
                         }
                        feedView.initFeedListView(model);
                    }
                } else {
                    System.out.println(response.code());
                }
            }

            @Override
            public void onFailure(Call<GetFeedsResponse> call, Throwable t) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        });
    }
}
