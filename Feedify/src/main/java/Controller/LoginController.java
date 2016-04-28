/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.RegisterResponse;
import Model.RegisterResponse.RegisterPost;
import API.RestClient;
import Model.GetUserResponse;
import Model.LoginResponse;
import Model.LoginResponse.LoginPost;
import Model.UserModel;
import View.FeedView;
import View.LoginView;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 *
 * @author Bastien
 */
public class LoginController implements LoginView.OnConnectButtonClick {
    JFrame window;
    
    public LoginController(JFrame win) {
        LoginView logView = new LoginView();
        logView.setOnConnectButtonClick(this);
        win.setContentPane(logView);
        win.getContentPane().revalidate();
        win.getContentPane().repaint();
        window = win;
    }
    
    
    //CallBack Method
    @Override
    public void applyRegister(String login, String Pwd) {
        RegisterPost p = new RegisterPost(login, Pwd);
        
        Call<RegisterResponse> call = RestClient.get().register(p);
        call.enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                if (response.body() != null) {
                    JOptionPane.showMessageDialog(null, "Votre compte a bien été crée", "Register", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "Votre compte existe déja ou vous avez entrer de mauvais paramètres", "Register", JOptionPane.ERROR_MESSAGE);
                }
            }

            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t) {
                JOptionPane.showMessageDialog(null, "La requete Register à échouer", "Register", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
    

    @Override
    public void applyLogin(String login, String pwd) {
        LoginPost p = new LoginPost(login, pwd);
        
        Call<LoginResponse> call = RestClient.get().login(p);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.body() != null) {
                    UserModel user = new UserModel();
                    user.Instance.setToken(response.body().getToken());
                    user.Instance.setLogin(login);
                    getThisUser(login);
                } else {
                    JOptionPane.showMessageDialog(null, "Mauvais nom de compte ou mot de passe", "Connection", JOptionPane.ERROR_MESSAGE);
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                JOptionPane.showMessageDialog(null, "La requete de connection à échouer", "Connection", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
    
    void getThisUser(String userName) {
        Call<GetUserResponse> call = RestClient.get(UserModel.Instance.getToken()).getUser(userName);
        call.enqueue(new Callback<GetUserResponse>() {
            @Override
            public void onResponse(Call<GetUserResponse> call, Response<GetUserResponse> response) {
                if (response.body() != null) {
                    if (response.body().getType().equals("admin")) {
                        UserModel.Instance.setIsAdmin(true);
                    } else {
                        UserModel.Instance.setIsAdmin(false);
                    }
                    FeedController FeedVC = new FeedController(window);
                }
            }

            @Override
            public void onFailure(Call<GetUserResponse> call, Throwable t) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        });
    }
    
}
