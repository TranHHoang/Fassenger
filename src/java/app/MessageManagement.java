package app;

import app.exception.InternalException;
import dao.DatabaseDao;
import java.util.ArrayList;
import java.util.Date;
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

    public void addMessage(Message msg) throws InternalException {
        dao.addMessage(msg);
    }

    public ArrayList<Message> getMessagesBeforeDate(int numOfMess, Date lastDate) throws InternalException {
        return dao.getMessagesBeforeDate(numOfMess, lastDate);
    }

    public Message getMessagesByDate(Date lastDate) throws InternalException {
        return dao.getMessagesByDate(lastDate);
    }
}
