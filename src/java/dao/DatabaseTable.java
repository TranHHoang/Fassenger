/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

/**
 *
 * @author TranHoang
 */
public class DatabaseTable {

    public static final String USER_TABLE = "FassengerUser";
    public static final String MESSAGE_TABLE="FassengerMessage";
    public static final String USER_ONLINE_TABLE="UserOnline";
    public static class UserTable {

        public static final String ID = "Id";
        public static final String USER_NAME = "UserName";
        public static final String NICK_NAME = "Nickname";
        public static final String PASSWORD = "UserPassword";
        public static final String AVATAR = "Avatar";
    }
    
    public static class MessageTable{
        public static final String ID = "Id";
        public static final String USER_NAME = "UserName";
        public static final String DATE_CREATED ="DateCreated";
        public static final String IMAGE_CONTENT = "ImageContent";
        public static final String TEXT_CONTENT="TextContent";
        
        public static enum ColumnOrder {
            ID, USER_NAME, DATE_CREATED, IMAGE_CONTENT, TEXT_CONTENT
        }
    }
    public static class UserOnlineTable{
        public static final String USER_NAME = "UserName";
    }
}
