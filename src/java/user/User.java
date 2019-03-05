/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package loadimage;

/**
 *
 * @author LEGION
 */
public class User {

    private String id;
    private String name;
    private String nickname;
    private String password;
    private byte[] avatar;

    public User() {
    }

    public User(String id, String name, String nickName, String password, byte[] image) {
        this.id = id;
        this.name = name;
        this.nickname = nickName;
        this.password = password;
        this.avatar = image;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNickName() {
        return nickname;
    }

    public void setNickName(String nickName) {
        this.nickname = nickName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public byte[] getImage() {
        return avatar;
    }

    public void setImage(byte[] image) {
        this.avatar = image;
    }

    @Override
    public String toString() {
        String s = "";
        for (byte b : avatar) {
            s += b + ",";
        }
        return s;
    }

}
