package app;

import app.exception.InternalException;
import dao.DatabaseDao;
import java.util.List;
import models.User;

public class UserManagement {

    DatabaseDao dao;

    public UserManagement(DatabaseDao dao) {
        this.dao = dao;
    }

    public List<User> getUsers() throws InternalException {
        return dao.getAllUser();
    }

    public User getUserByName(String name) throws InternalException {
        return dao.getUserByName(name);
    }

    public void addUser(User u) throws InternalException {
        dao.addUser(u);
    }

    public void editUserByName(User user) throws InternalException {
        dao.editUserByName(user);
    }
}
