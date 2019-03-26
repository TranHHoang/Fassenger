/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app;

import app.exception.InternalException;
import dao.DatabaseDao;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import models.OnlineUser;
import models.User;

/**
 *
 * @author LEGION
 */
public class UserOnlineManagement {

    DatabaseDao dao;
    private List<OnlineUser> cacheOnline;

    public UserOnlineManagement(DatabaseDao dao) throws InternalException {
        this.dao = dao;
        this.cacheOnline = dao.getAllUserOnline()
                .stream()
                .map(user -> new OnlineUser(user.getName(), true))
                .collect(Collectors.toList());
    }

    public ArrayList<User> getAllOnlineUser() throws InternalException {
        return new ArrayList<>(cacheOnline
                .stream()
                .filter(userOnline -> userOnline.isActivated())
                .map(userOnline -> new User(userOnline.getUserName()))
                .collect(Collectors.toList()));
    }

    public void addOnlineUser(String userName) throws InternalException {
        cacheOnline.add(new OnlineUser(userName, true));
        dao.addUserOnline(userName);
    }

    public void deleteOnlineUser(String userName) throws InternalException {
        cacheOnline.removeIf(user -> user.getUserName().equals(userName));
        dao.deleteUserOnline(userName);
    }

    public boolean isUserOnline(String userName) throws InternalException {
        return cacheOnline.stream().anyMatch(user -> user.getUserName().equals(userName));
    }

    public void toggleUserStatus(String userName, boolean isActivated) throws InternalException {
        cacheOnline.stream()
                .filter(onlineUser -> onlineUser.getUserName().equals(userName))
                .findFirst()
                .get().setActivated(isActivated);
        dao.toggleUserStatus(userName, isActivated);
    }
}
