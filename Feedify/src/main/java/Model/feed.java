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
public class feed {
    private int id;
    private String name;
    private String url;
    private Boolean refresh_error;

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUtl(String url) {
        this.url = url;
    }

    public void setRefresh_error(Boolean refresh_error) {
        this.refresh_error = refresh_error;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getUtl() {
        return url;
    }

    public Boolean getRefresh_error() {
        return refresh_error;
    }   
}
