/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app;

import app.exception.InternalException;
import dao.DatabaseDao;
import java.util.ArrayList;
import models.User;

/**
 *
 * @author LEGION
 */
public class UserOnlineManagement {

    DatabaseDao dao;

    public UserOnlineManagement() {
    }

    public UserOnlineManagement(DatabaseDao dao) {
        this.dao = dao;
    }

    public ArrayList<User> getAllOnlineUser() throws InternalException {
        return dao.getAllUserOnline();
    }

    public void addOnlineUser(String userName) throws InternalException {
        dao.addUserOnline(userName);
    }

    public void deleteOnlineUser(String userName) throws InternalException {
        dao.deleteUserOnline(userName);
    }

    public boolean isUserOnline(String userName) throws InternalException {
        return dao.isUserOnline(userName);
    }
}
