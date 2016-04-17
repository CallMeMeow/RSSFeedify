/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 *
 * @author Bastien
 */
public class UserModel {
    public static UserModel Instance;
    String token;
    
    public UserModel() {
        Instance = this;
    }
    
    public void setToken(String token) {
        this.token = token;
    }
    
    public String getToken() {
        return this.token;
    }
}
