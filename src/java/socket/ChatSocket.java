/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package socket;

import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import javax.servlet.http.HttpSession;
import javax.websocket.EndpointConfig;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.server.ServerEndpoint;
import javax.websocket.Session;

/**
 *
 * @author TranHoang
 */
@ServerEndpoint(value = "/chatroom", configurator = GetHttpSessionConfigurator.class)
public class ChatSocket {
    
    static Set<Session> userList = Collections.synchronizedSet(new HashSet<>());

    @OnOpen
    public void onOpen(Session session, EndpointConfig config) {
        HttpSession httpSession = (HttpSession)config.getUserProperties().get(HttpSession.class.getName());
        
        if (httpSession.getAttribute("userName") != null) {
            session.getUserProperties().put("userName", httpSession.getAttribute("userName").toString());
//            session.getUserProperties().put("nickName", httpSession.getAttribute("nickName").toString());
            userList.add(session);
        }
    }

    @OnMessage
    public void onMessage(String message, Session userSession) throws IOException {
        String username = userSession.getUserProperties().get("userName").toString();
        for (Session session : userList) {
            session.getBasicRemote().sendText(username + ": " + message);
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
