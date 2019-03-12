package dao;

import dao.DatabaseTable.MessageTable;
import dao.DatabaseTable.UserTable;
import dao.context.DBContext;
import models.User;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import models.Message;

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

    public void editUserByName(User user) {
        try {
            String sql = String.format("update %s set %s = ?, %s = ?, %s = ? where %s = ?",
                    DatabaseTable.USER_TABLE,
                    UserTable.NICK_NAME,
                    UserTable.PASSWORD,
                    UserTable.AVATAR,
                    UserTable.USER_NAME);

            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setNString(1, user.getNickName());
            statement.setString(2, user.getPassword());
            statement.setBytes(3, user.getImage());
            statement.setString(4, user.getName());

            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addUser(User user) {
        try {
            String sql = String.format("insert into %s values (NEWID(), ?, ?, ?, ?) ", DatabaseTable.USER_TABLE);
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

    public User getUserByName(String name) {
        try {
            String sql = "select * from " + DatabaseTable.USER_TABLE + " where " + UserTable.USER_NAME + " = ?";

            PreparedStatement pst = connection.prepareStatement(sql);
            pst.setString(1, name);

            ResultSet rs = pst.executeQuery();
            rs.next();

            return new User(
                    rs.getString(UserTable.USER_NAME),
                    rs.getString(UserTable.NICK_NAME),
                    rs.getString(UserTable.PASSWORD),
                    rs.getBytes(UserTable.AVATAR));

        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }

    /* sql query for Fassenger Message table*/
    public void addMessage(Message msg) {
        try {
            String sql = "insert into " + DatabaseTable.MESSAGE_TABLE + " values (NEWID(), ?, ?,?,?)";
            PreparedStatement pst = connection.prepareStatement(sql);
            pst.setString(MessageTable.ColumnOrder.USER_NAME.ordinal(), msg.getName());
            pst.setDate(MessageTable.ColumnOrder.DATE_CREATED.ordinal(), new java.sql.Date(msg.getDateCreated().getTime()));
            pst.setBytes(MessageTable.ColumnOrder.IMAGE_CONTENT.ordinal(), msg.getImageContent());
            pst.setString(MessageTable.ColumnOrder.TEXT_CONTENT.ordinal(), msg.getTextContent());
            pst.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Message> getMessagesBeforeDate(int numberOfMess, java.util.Date lastDate) {
        ArrayList<Message> result = new ArrayList<>();
        try {
            String sql = "select top (?) * from " + DatabaseTable.MESSAGE_TABLE
                    + " where " + DatabaseTable.MessageTable.DATE_CREATED + " < ?";

            PreparedStatement pst = connection.prepareStatement(sql);
            pst.setInt(1, numberOfMess);
            pst.setDate(2, new java.sql.Date(lastDate.getTime()));
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                result.add(new Message(rs.getString(DatabaseTable.MessageTable.USER_NAME),
                        new java.util.Date(rs.getDate(DatabaseTable.MessageTable.DATE_CREATED).getTime()),
                        rs.getBytes(DatabaseTable.MessageTable.IMAGE_CONTENT), 
                        rs.getString(DatabaseTable.MessageTable.TEXT_CONTENT)));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
