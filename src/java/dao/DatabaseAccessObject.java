/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package loadimage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 *
 * @author LEGION
 */
public class DatabaseAccessObject {

    Connection con;

    public ArrayList<User> getAllUser() {
        ArrayList<User> result = new ArrayList<>();
        try {
            con = new DBContext().getConnection();
            String sql = "select * from FassengerUser";
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                result.add(new User(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getBytes(5)));
            }
        } catch (Exception e) {
            System.out.println("");
            System.out.println(e);
            System.out.println("");
        }
        return result;
    }

    public User getUser(String id) {
        ArrayList<User> result = new ArrayList<>();
        try {
            con = new DBContext().getConnection();
            String sql = "select * from FassengerUser where id =?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, id);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                result.add(new User(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getBytes(5)));
            }
        } catch (Exception e) {
            System.out.println("");
            System.out.println(e);
            System.out.println("");
        }
        return result.get(0);
    }

    public void editUser(User u) {
        try {
            con = new DBContext().getConnection();
            String sql = "update FassengerUser \n"
                    + "set UserName = ?,\n"
                    + "	Nickname = ?,\n"
                    + "	UserPassword = ?,\n"
                    + " Avatar=?\n"
                    + " where id = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, u.getName());
            pst.setString(2, u.getNickName());
            pst.setString(3, u.getPassword());
            pst.setBytes(4, u.getImage());
            pst.setString(5, u.getId());
            pst.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addUserWithoutAvatar(User u) {

    }
}
