/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import View.FeedView;
import javax.swing.JFrame;

/**
 *
 * @author Bastien
 */
public class FeedController {
    public FeedController(JFrame win) {
        FeedView feedView = new FeedView();
        win.setContentPane(feedView);
    }
}
