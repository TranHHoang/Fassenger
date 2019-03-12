/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app;

import dao.DatabaseDao;
import java.util.ArrayList;
import models.User;

/**
 *
 * @author LEGION
 */
public class UserOnlineManagement {
    DatabaseDao dao ;

    public UserOnlineManagement() {
    }

    public UserOnlineManagement(DatabaseDao dao) {
        this.dao = dao;
    }
    
    public ArrayList<User> getAllOnlineUser(){
        return dao.getAllUserOnline();
    }
    public void addOnlineUser(String userName){
        dao.addUserOnline(userName);
    }
    public void deleteOnlineUser(String userName){
        dao.deleteUserOnline(userName);
    }
    public boolean isUserOnline (String userName){
        return dao.isUserOnline(userName);
    }    
}
