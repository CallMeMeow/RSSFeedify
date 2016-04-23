/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.util.List;

/**
 *
 * @author Bastien
 */
public class UserModel {
    public static UserModel Instance;
    String token;
    List<Feed> userFeeds;

    public UserModel() {
        Instance = this;
    }
    
    public List<Feed> getUserFeeds() {
        return userFeeds;
    }

    public void setUserFeeds(List<Feed> userFeeds) {
        this.userFeeds = userFeeds;
    }
    
    public void setToken(String token) {
        this.token = token;
    }
    
    public String getToken() {
        return this.token;
    }
}
