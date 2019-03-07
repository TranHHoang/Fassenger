package dao;

import dao.DatabaseTable.UserTable;
import dao.context.DBContext;
import models.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author LEGION
 */
public class DatabaseDao {

    private Connection connection;

    public DatabaseDao(DBContext context) throws Exception {
        connection = context.getConnection();
    }

    public List<User> getAllUser() {
        List<User> userList = new ArrayList<>();

        try {
            String sql = "select * from " + DatabaseTable.USER_TABLE;

            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                userList.add(new User(
                        rs.getString(UserTable.ID),
                        rs.getString(UserTable.USER_NAME),
                        rs.getNString(UserTable.NICK_NAME),
                        rs.getString(UserTable.PASSWORD),
                        rs.getBytes(UserTable.AVATAR)
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return userList;
    }

    public User getUserById(String id) {
        try {
            String sql = "select * from " + DatabaseTable.USER_TABLE;

            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();

            rs.next();

            return new User(
                    rs.getString(UserTable.ID),
                    rs.getString(UserTable.USER_NAME),
                    rs.getNString(UserTable.NICK_NAME),
                    rs.getString(UserTable.PASSWORD),
                    rs.getBytes(UserTable.AVATAR)
            );
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public void editUserById(User user) {
        try {
            String sql = String.format("update %s set %s = ?, %s = ?, %s = ?, %s = ? where %s = ?",
                    DatabaseTable.USER_TABLE,
                    UserTable.USER_NAME,
                    UserTable.NICK_NAME,
                    UserTable.PASSWORD,
                    UserTable.AVATAR,
                    UserTable.ID);

            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setString(1, user.getName());
            statement.setNString(2, user.getNickName());
            statement.setString(3, user.getPassword());
            statement.setBytes(4, user.getImage());
            statement.setString(5, user.getId());

            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addUser(User user) {
        try {
            String sql = String.format("insert into %s values (newid(), ?, ?, ? , ?) ", DatabaseTable.USER_TABLE);
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, user.getName());
            statement.setNString(2, user.getNickName());
            statement.setString(3, user.getPassword());
            statement.setBytes(4, user.getImage());

            System.out.println(statement.toString());

            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public User getUserByUserName(String username){
        User u = null;
        try{
            ArrayList<User> result = new ArrayList<>();
            String sql = "select * from FassengerUser where UserName = ?";
            PreparedStatement pst = connection.prepareStatement(sql);
            pst.setString(1,username);
            ResultSet rs = pst.executeQuery();
            while(rs.next()){
                result.add(new User(rs.getString(UserTable.ID) , rs.getString(UserTable.USER_NAME) ,rs.getString(UserTable.NICK_NAME) , rs.getString(UserTable.PASSWORD) , rs.getBytes(UserTable.AVATAR)));
            }
            u = result.get(0);
        }catch(Exception e){
            System.out.println(e);
        }
        return u;
    }
    
}
