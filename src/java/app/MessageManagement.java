/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app;

import dao.DatabaseDao;
import java.util.ArrayList;
import java.util.Date;
import models.Message;

/**
 *
 * @author LEGION
 */
public class MessageManagement {

    DatabaseDao dao;

    public MessageManagement() {
    }

    public MessageManagement(DatabaseDao dao) {
        this.dao = dao;
    }

    public void addMessage(Message msg) {
        dao.addMessage(msg);
    }

    public ArrayList<Message> getMessagesBeforeDate(int numOfMess, Date lastDate) {
        return dao.getMessagesBeforeDate(numOfMess, lastDate);
    }
}
