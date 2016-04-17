/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.RegisterResponse;
import Model.RegisterResponse.RegisterPost;
import API.RestClient;
import Model.LoginResponse;
import Model.LoginResponse.LoginPost;
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
    public LoginController(JFrame win) {
        LoginView logView = new LoginView();
        logView.setOnConnectButtonClick(this);
        win.setContentPane(logView);
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
                    System.out.println(response.body().getToken());
                    JOptionPane.showMessageDialog(null, "Connection réussie", "Connection", JOptionPane.INFORMATION_MESSAGE);
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
}
