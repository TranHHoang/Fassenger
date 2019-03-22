package socket;

import app.MessageManagement;
import app.UserOnlineManagement;
import app.exception.InternalException;
import dao.DatabaseDao;
import dao.context.DBContext;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.json.Json;
import javax.json.JsonObject;
import javax.servlet.http.HttpSession;
import javax.websocket.EndpointConfig;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.server.ServerEndpoint;
import javax.websocket.Session;
import models.Message;
import models.User;

/**
 *
 * @author TranHoang
 */
@ServerEndpoint(value = "/chatroom", configurator = GetHttpSessionConfigurator.class)
public class ChatSocket {

    public static final String TYPE_MESSAGE = "message";
    public static final String TYPE_TYPING = "typing";
    public static final String TYPE_STATUS = "status";

    private static Set<Session> userList = Collections.synchronizedSet(new HashSet<>());

    @OnOpen
    public void onOpen(Session session, EndpointConfig config) throws InternalException {
        HttpSession httpSession = (HttpSession) config.getUserProperties().get(HttpSession.class.getName());

        if (httpSession.getAttribute("userName") != null) {
            session.getUserProperties().put("userName", httpSession.getAttribute("userName").toString());
            session.getUserProperties().put("nickName", httpSession.getAttribute("nickName").toString());
            userList.add(session);

            String userName = httpSession.getAttribute("userName").toString();

            // Load previous history to chat
            try {
                // Load message from DAO
                MessageManagement mm = MessageManagement.getInstance(DatabaseDao.getInstance(DBContext.getInstance()));
                List<Message> messages = mm.getMessagesBeforeDate(4, new Date());

                for (Message message : messages) {
                    System.out.println(message);
                    if (message.getImageContent() == null) {
                        session.getBasicRemote().sendText(createMessageObj(message, message.getName().equals(userName)).toString());

                    } else {
                        session.getBasicRemote().sendText(createImageObj(message, message.getName().equals(userName)).toString());
                    }
                }

                UserOnlineManagement uom = new UserOnlineManagement(DatabaseDao.getInstance(DBContext.getInstance()));
                List<User> usersOnline = uom.getAllOnlineUser();

                System.out.println(usersOnline.size());

                // Broadcast user online to other users
                for (Session userSession : userList) {
                    userSession.getBasicRemote().sendText(createClearObj().toString());

                    for (User onlineUser : usersOnline) {
                        userSession.getBasicRemote().sendText(createStatusObj(onlineUser).toString());
                    }
                }

            } catch (Exception ex) {
                // Error exception
                throw new InternalException(ex.getMessage());
            }
        }
    }

    public static JsonObject createClearObj() {
        return Json.createObjectBuilder()
                .add("type", "clear")
                .build();
    }

    public static JsonObject createStatusObj(User user) {
        return Json.createObjectBuilder()
                .add("type", TYPE_STATUS)
                .add("user", user.getName())
                .build();
    }

    private JsonObject createMessageObj(Message msg, boolean isSender) {
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        cal1.setTime(new Date());
        cal2.setTime(msg.getDateCreated());
        boolean sameDay = cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR)
                && cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR);

        String datePattern = "hh:mm a";
        if (!sameDay) {
            datePattern = "dd/MM/yy 'at' " + datePattern;
        }

        return Json.createObjectBuilder()
                .add("type", TYPE_MESSAGE)
                .add("isSender", isSender)
                .add("user", msg.getName())
                .add("date", new SimpleDateFormat(datePattern).format(msg.getDateCreated()))
                .add("text", msg.getTextContent())
                .build();
    }

    private JsonObject createImageObj(Message msg, boolean isSender) throws Exception {
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        cal1.setTime(new Date());
        cal2.setTime(msg.getDateCreated());
        boolean sameDay = cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR)
                && cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR);

        String datePattern = "hh:mm a";
        if (!sameDay) {
            datePattern = "dd/MM/yy 'at' " + datePattern;
        }

        return Json.createObjectBuilder()
                .add("type", TYPE_MESSAGE)
                .add("isSender", isSender)
                .add("user", msg.getName())
                .add("date", new SimpleDateFormat(datePattern).format(msg.getDateCreated()))
                .add("image", msg.getTextContent()) // images/user_18_3_2019_9_29_29_299
                .build();
    }

    @OnMessage
    public void onMessage(String message, Session userSession) throws InternalException {
        String userName = userSession.getUserProperties().get("userName").toString();
        String nickName = userSession.getUserProperties().get("nickName").toString();
        // hello -> message hello
        // image 1_dDad -> 1_adasda
        Message messageObj = new Message(userName, new Date(), null, message.substring(message.indexOf(" ") + 1));

        try {
            // Put message to DAO
            MessageManagement mm = MessageManagement.getInstance(DatabaseDao.getInstance(DBContext.getInstance()));

            if (message.split(" ")[0].equals("message")) {
                mm.addMessage(messageObj);
            }

            for (Session session : userList) {
                if (message.split(" ")[0].equals("message")) {
                    session.getBasicRemote().sendText(createMessageObj(messageObj,
                            session.getUserProperties().get("userName").equals(userName)).toString());
                } else {
                    session.getBasicRemote().sendText(createImageObj(messageObj,
                            session.getUserProperties().get("userName").equals(userName)).toString());
                }
            }
        } catch (Exception ex) {
            throw new InternalException(ex.getMessage());
        }
    }

    @OnClose
    public void onClose(Session session) throws InternalException {
        String userName = session.getUserProperties().get("userName").toString();

        UserOnlineManagement uom;
        try {
            uom = new UserOnlineManagement(DatabaseDao.getInstance(DBContext.getInstance()));

            if (!uom.isUserOnline(userName)) {
                // Broadcast user online to other users
                for (Session userSession : userList) {
                    userSession.getBasicRemote().sendText(createClearObj().toString());
                    for (User onlineUser : uom.getAllOnlineUser()) {
                        userSession.getBasicRemote().sendText(createStatusObj(onlineUser).toString());
                    }
                }
            }
        } catch (InternalException | IOException ex) {
            throw new InternalException(ex.getMessage());
        }

        userList.remove(session);
    }

    @OnError
    public void onError(Throwable t) {
        t.printStackTrace();
    }
}
