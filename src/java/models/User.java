/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.io.InputStream;

/**
 *
 * @author LEGION
 */
public class User {

    private String name;
    private String nickname;
    private String password;
    private InputStream avatar;

    public User() {
    }

    public User(String name) {
        this.name = name;
    }
    
    public User(String name, String nickName, String password, InputStream image) {
        this.name = name;
        this.nickname = nickName;
        this.password = password;
        this.avatar = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public InputStream getAvatar() {
        return avatar;
    }

    public void setAvatar(InputStream avatar) {
        this.avatar = avatar;
    }
}
