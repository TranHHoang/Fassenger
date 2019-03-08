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
@ServerEndpoint("/chatroom")
public class ChatSocket {
    
    static Set<Session> users = Collections.synchronizedSet(new HashSet<>());

    @OnOpen
    public void onOpen(Session session) {
        users.add(session);
    }

    @OnMessage
    public void onMessage(String message, Session userSession) throws IOException {
        String username = (String) userSession.getUserProperties().get("userName");
        if (username == null) {
            userSession.getUserProperties().put("userName", message);
            userSession.getBasicRemote().sendText("System: you are connected as " + message);
        } else {
            for (Session session : users) {
                session.getBasicRemote().sendText(username + ": " + message);
            }
        }
    }

    @OnClose
    public void onClose(Session session) throws IOException {
        users.remove(session);
        for (Session s : users) {
            s.getBasicRemote().sendText(session.getUserProperties().get("userName") + " offline");
        }
    }

    @OnError
    public void onError(Throwable t) {
        t.printStackTrace();
    }
}
