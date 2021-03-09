package org.rooms.app;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@ServerEndpoint("/room")
public class RoomWebSocket {
    private static final long serialVersionUID = 1L;
    private static Map<String, Session> sessions
            =  Collections.synchronizedMap(new HashMap<String, Session>());
    private Boolean isOn = false;

    @OnOpen
    public void onConnectionOpen(Session session) {
        sessions.put(String.valueOf(session.getId()), session);
    }

    @OnClose
    public void onClose(Session session) {
        sessions.remove(session.getId());
    }

    @OnMessage
    public void onMessage(Session session, String isOn) {
        System.out.println(isOn);
        this.isOn = Boolean.valueOf(isOn);

        for (Map.Entry<String, Session> peer : sessions.entrySet()) {
            if (!peer.getValue().equals(session)) {
                try {
                    peer.getValue().getBasicRemote().sendText(isOn);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

