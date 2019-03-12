/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package socket;

import app.MessageManagement;
import app.UserManagement;
import com.sun.org.apache.xml.internal.security.utils.Base64;
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

/**
 *
 * @author TranHoang
 */
@ServerEndpoint(value = "/chatroom", configurator = GetHttpSessionConfigurator.class)
public class ChatSocket {

    static Set<Session> userList = Collections.synchronizedSet(new HashSet<>());

    @OnOpen
    public void onOpen(Session session, EndpointConfig config) {
        HttpSession httpSession = (HttpSession) config.getUserProperties().get(HttpSession.class.getName());

        if (httpSession.getAttribute("userName") != null) {
            session.getUserProperties().put("userName", httpSession.getAttribute("userName").toString());
            session.getUserProperties().put("nickName", httpSession.getAttribute("nickName").toString());
            userList.add(session);

            String userName = httpSession.getAttribute("userName").toString();

            // Load previous history to chat
            try {
                // Put message to DAO
                DBContext context = new DBContext();
                MessageManagement mm = new MessageManagement(new DatabaseDao(context));

                List<Message> messages = mm.getMessagesBeforeDate(100, new Date());

                for (Message message : messages) {
                    session.getBasicRemote().sendText(createMessageObj(message, message.getName().equals(userName)).toString());
                }

                System.out.println(messages.size());
            } catch (Exception ex) {
                // Error exception
                ex.printStackTrace();
            }
        }
    }

    private JsonObject createMessageObj(Message msg, boolean isSender) throws Exception {
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

        byte[] ava = new byte[0];

        if (!isSender) {
            DBContext context = new DBContext();
            UserManagement um = new UserManagement(new DatabaseDao(context));
            ava = um.getUserByName(msg.getName()).getImage();
        }

        return Json.createObjectBuilder()
                .add("isSender", isSender)
                .add("user", msg.getName())
                .add("avatar", Base64.encode(ava))
                .add("date", new SimpleDateFormat(datePattern).format(msg.getDateCreated()))
                .add("image", Base64.encode(msg.getImageContent() == null ? new byte[0] : msg.getImageContent()))
                .add("text", msg.getTextContent())
                .build();
    }

    @OnMessage
    public void onMessage(String message, Session userSession) throws IOException {
        String userName = userSession.getUserProperties().get("userName").toString();
        String nickName = userSession.getUserProperties().get("nickName").toString();

        Message messageObj = new Message(userName, new Date(), null, message);

        try {
            // Put message to DAO
            DBContext context = new DBContext();
            MessageManagement mm = new MessageManagement(new DatabaseDao(context));

            mm.addMessage(messageObj);

            for (Session session : userList) {
                session.getBasicRemote().sendText(createMessageObj(messageObj,
                        session.getUserProperties().get("userName").equals(userName)).toString());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @OnClose
    public void onClose(Session session) throws IOException {
        userList.remove(session);

        for (Session s : userList) {
            s.getBasicRemote().sendText(session.getUserProperties().get("userName") + " offline");
        }
    }

    @OnError
    public void onError(Throwable t) {
        t.printStackTrace();
    }
}
