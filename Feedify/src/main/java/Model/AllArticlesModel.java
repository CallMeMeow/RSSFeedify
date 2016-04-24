/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import Model.GetArticleResponse.Articles;
import java.util.List;

/**
 *
 * @author Bastien
 */
public class AllArticlesModel {
    public static AllArticlesModel Instance;
    public List<Articles> allArticles;

    public void setAllArticles(List<Articles> allArticles) {
        this.allArticles = allArticles;
    }

    public List<Articles> getAllArticles() {
        return allArticles;
    }
    
    public AllArticlesModel() {
        Instance = this;
    }
}
