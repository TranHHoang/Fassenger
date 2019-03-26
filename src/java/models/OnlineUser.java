/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

/**
 *
 * @author TranHoang
 */
public class OnlineUser {
    private String userName;
    private boolean activated;

    public OnlineUser(String userName, boolean isActivated) {
        this.userName = userName;
        this.activated = isActivated;
    }

    public String getUserName() {
        return userName;
    }

    public boolean isActivated() {
        return activated;
    }

    public void setActivated(boolean activated) {
        this.activated = activated;
    }
    
    
}
