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

    private ArrayList<Message> cache;
    private final DatabaseDao dao;
    private static MessageManagement instance;

    private MessageManagement(DatabaseDao dao) throws InternalException {
        this.dao = dao;
        cache = dao.getAllMessages();
    }

    public static MessageManagement getInstance(DatabaseDao dao) throws InternalException {
        if (instance == null) {
            instance = new MessageManagement(dao);
        }
        return instance;
    }

    public void addMessage(Message msg) throws InternalException {
        dao.addMessage(msg);
        cache.add(msg);
    }

    public ArrayList<Message> getMessagesBeforeDate(int numOfMess, Date lastDate) throws InternalException {
//        return dao.getMessagesBeforeDate(numOfMess, lastDate); 
        ArrayList<Message> loadList = new ArrayList<>();
        for (int i = cache.size() - 1; i >= 0; i--) {
            System.out.println(cache.get(i));
            if (cache.get(i).getDateCreated().compareTo(lastDate) < 0) {
                loadList.add(0, cache.get(i));
                numOfMess--;
            }
            if (numOfMess <= 0) {
                break;
            }
        }
        return loadList;
    }

    public Message getMessagesByDate(Date lastDate) throws InternalException {
        return dao.getMessagesByDate(lastDate);
    }
}
