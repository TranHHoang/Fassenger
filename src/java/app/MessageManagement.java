/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app;

import dao.DatabaseDao;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import models.Message;

/**
 *
 * @author LEGION
 */
public class MessageManagement {

    private final DatabaseDao dao;
    private static MessageManagement instance;

    private MessageManagement(DatabaseDao dao) {
        this.dao = dao;
    }

    public static MessageManagement getInstance(DatabaseDao dao) {
        if (instance == null) {
            instance = new MessageManagement(dao);
        }
        return instance;
    }

    public void addMessage(Message msg) {
        dao.addMessage(msg);

    }

    public ArrayList<Message> getMessagesBeforeDate(int numOfMess, Date lastDate) {
        return dao.getMessagesBeforeDate(numOfMess, lastDate);
    }
    
    public Message getMessagesByDate(Date lastDate) {
        return dao.getMessagesByDate(lastDate);
    }
}
