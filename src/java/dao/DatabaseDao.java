package dao;

import app.exception.InternalException;
import dao.DatabaseTable.MessageTable;
import dao.DatabaseTable.UserTable;
import dao.context.DBContext;
import java.io.ByteArrayInputStream;
import models.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import models.Message;

/**
 *
 * @author LEGION
 */
public class DatabaseDao {

    private final Connection connection;

    private static DatabaseDao instance;

    private DatabaseDao(DBContext context) throws InternalException {
        connection = context.getConnection();
    }

    public static DatabaseDao getInstance(DBContext context) throws InternalException {
        if (instance == null) {
            instance = new DatabaseDao(context);
        }
        return instance;
    }

    public List<User> getAllUser() throws InternalException {
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
                        rs.getBinaryStream(UserTable.AVATAR)
                ));
            }
        } catch (SQLException e) {
            throw new InternalException(e.getMessage());
        }

        return userList;
    }

    public void editUserByName(User user) throws InternalException {
        try {
            String sql = String.format("update %s set %s = ?, %s = ?, %s = ? where %s = ?",
                    DatabaseTable.USER_TABLE,
                    UserTable.NICK_NAME,
                    UserTable.PASSWORD,
                    UserTable.AVATAR,
                    UserTable.USER_NAME);

            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setNString(1, user.getNickname());
            statement.setString(2, user.getPassword());
            statement.setBinaryStream(3, user.getAvatar());
            statement.setString(4, user.getName());

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new InternalException(e.getMessage());
        }
    }

    public void addUser(User user) throws InternalException {
        try {
            String sql = String.format("insert into %s values (NEWID(), ?, ?, ?, ?) ", DatabaseTable.USER_TABLE);
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, user.getName());
            statement.setNString(2, user.getNickname());
            statement.setString(3, user.getPassword());
            statement.setBinaryStream(4, user.getAvatar());

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new InternalException(e.getMessage());
        }
    }

    public User getUserByName(String name) throws InternalException {
        try {
            String sql = "select * from " + DatabaseTable.USER_TABLE + " where " + UserTable.USER_NAME + " = ?";

            PreparedStatement pst = connection.prepareStatement(sql);
            pst.setString(1, name);

            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                return new User(
                        rs.getString(UserTable.USER_NAME),
                        rs.getString(UserTable.NICK_NAME),
                        rs.getString(UserTable.PASSWORD),
                        rs.getBinaryStream(UserTable.AVATAR));
            }

        } catch (SQLException e) {
            throw new InternalException(e.getMessage());
        }

        return null;
    }

    /* sql query for Fassenger Message table*/
    public void addMessage(Message msg) throws InternalException {
        try {
            String sql = "insert into " + DatabaseTable.MESSAGE_TABLE + " values (NEWID(), ?, ?, ?, ?)";

            PreparedStatement pst = connection.prepareStatement(sql);

            pst.setString(MessageTable.ColumnOrder.USER_NAME.ordinal(), msg.getName());
            pst.setTimestamp(MessageTable.ColumnOrder.DATE_CREATED.ordinal(), new Timestamp(msg.getDateCreated().getTime()));
            pst.setBinaryStream(MessageTable.ColumnOrder.IMAGE_CONTENT.ordinal(), msg.getImageContent());
            pst.setString(MessageTable.ColumnOrder.TEXT_CONTENT.ordinal(), msg.getTextContent());

            pst.executeUpdate();
        } catch (SQLException e) {
            throw new InternalException(e.getMessage());
        }
    }

    public ArrayList<Message> getAllMessages() throws InternalException {
        ArrayList<Message> result = new ArrayList<>();
        try {
            String sql = "select * from " + DatabaseTable.MESSAGE_TABLE
                    + " order by " + DatabaseTable.MessageTable.DATE_CREATED;

            PreparedStatement pst = connection.prepareStatement(sql);

            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                result.add(new Message(rs.getString(DatabaseTable.MessageTable.USER_NAME),
                        new java.util.Date(rs.getTimestamp(DatabaseTable.MessageTable.DATE_CREATED).getTime()),
                        rs.getBinaryStream(DatabaseTable.MessageTable.IMAGE_CONTENT),
                        rs.getString(DatabaseTable.MessageTable.TEXT_CONTENT)));
            }
        } catch (SQLException e) {
            throw new InternalException(e.getMessage());
        }
        return result;
    }

    public Message getMessagesByDate(Date lastDate) throws InternalException {
        try {
            String sql = "select * from " + DatabaseTable.MESSAGE_TABLE
                    + " where " + DatabaseTable.MessageTable.DATE_CREATED + "= ?";

            PreparedStatement pst = connection.prepareStatement(sql);
            pst.setString(1, new Timestamp(lastDate.getTime()).toString());
            System.out.println(new Timestamp(lastDate.getTime()));

            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                byte[] image = rs.getBytes(DatabaseTable.MessageTable.IMAGE_CONTENT);

                return new Message(rs.getString(DatabaseTable.MessageTable.USER_NAME),
                        new java.util.Date(rs.getTimestamp(DatabaseTable.MessageTable.DATE_CREATED).getTime()),
                        new ByteArrayInputStream(image),
                        rs.getString(DatabaseTable.MessageTable.TEXT_CONTENT));
            }

        } catch (SQLException e) {
            throw new InternalException(e.getMessage());
        }

        return null;
    }

    /*the following code is for user online table*/
    public ArrayList<User> getAllUserOnline() throws InternalException {
        ArrayList<User> users = new ArrayList<>();
        try {
            String sql = "select " + DatabaseTable.UserOnlineTable.USER_NAME
                    + " from " + DatabaseTable.USER_ONLINE_TABLE + " where Activated = 1";
            PreparedStatement pst = connection.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                users.add(new User(rs.getString(1)));
            }
        } catch (SQLException e) {
            throw new InternalException(e.getMessage());
        }
        return users;
    }

    public void addUserOnline(String userName) throws InternalException {
        try {
            String sql = "insert into " + DatabaseTable.USER_ONLINE_TABLE + " values (?, ?)";
            PreparedStatement pst = connection.prepareStatement(sql);
            pst.setString(1, userName);
            pst.setBoolean(2, true);
            pst.executeUpdate();
        } catch (SQLException e) {
            throw new InternalException(e.getMessage());
        }
    }

    public void toggleUserStatus(String userName, boolean isActivated) throws InternalException {
        try {
            String sql = "update " + DatabaseTable.USER_ONLINE_TABLE + " set "
                    + DatabaseTable.UserOnlineTable.ACTIVATED + " = ? where " + DatabaseTable.UserOnlineTable.USER_NAME + " = ?";
            PreparedStatement pst = connection.prepareStatement(sql);
            pst.setBoolean(1, isActivated);
            pst.setString(2, userName);
            pst.executeUpdate();
        } catch (SQLException e) {
            throw new InternalException(e.getMessage());
        }
    }

    public void deleteUserOnline(String userName) throws InternalException {
        try {
            String sql = "delete " + DatabaseTable.USER_ONLINE_TABLE
                    + " where " + DatabaseTable.UserOnlineTable.USER_NAME + " =  ? ";
            PreparedStatement pst = connection.prepareStatement(sql);
            pst.setString(1, userName);
            pst.executeUpdate();
        } catch (SQLException e) {
            throw new InternalException(e.getMessage());
        }
    }

    public boolean isUserOnline(String name) throws InternalException {
        ArrayList<User> users = new ArrayList<>();
        try {

            String sql = "select * from " + DatabaseTable.USER_ONLINE_TABLE
                    + " where " + DatabaseTable.UserOnlineTable.USER_NAME + "= ?";
            PreparedStatement pst = connection.prepareStatement(sql);
            pst.setString(1, name);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                users.add(new User(rs.getString(DatabaseTable.UserOnlineTable.USER_NAME)));
            }
        } catch (SQLException e) {
            throw new InternalException(e.getMessage());
        }
        if (users.size() > 0) {
            return true;
        }
        return false;
    }
}
