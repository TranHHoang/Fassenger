/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package socket;

import app.MessageManagement;
import dao.DatabaseDao;
import dao.context.DBContext;
import java.io.IOException;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
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

            // Load previous history to chat
            try {
                // Put message to DAO
                DBContext context = new DBContext();
                MessageManagement mm = new MessageManagement(new DatabaseDao(context));

                List<Message> messages = mm.getMessagesBeforeDate(100, new Date());
                
                for (Message message : messages) {
                    session.getBasicRemote().sendText(message.getName() + ": " + message.getTextContent());
                }
                
                System.out.println(messages.size());
            } catch (Exception ex) {
                // Error exception
            }
        }
    }

    @OnMessage
    public void onMessage(String message, Session userSession) throws IOException {
        String userName = userSession.getUserProperties().get("userName").toString();
        String nickName = userSession.getUserProperties().get("nickName").toString();

        try {
            // Put message to DAO
            DBContext context = new DBContext();
            MessageManagement mm = new MessageManagement(new DatabaseDao(context));

            mm.addMessage(new Message(userName, new Date(), null, message));
            System.out.println(message);
        } catch (Exception ex) {
            // Error exception
        }

        for (Session session : userList) {
            session.getBasicRemote().sendText(nickName + ": " + message);
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
