package org.rooms.app;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@ServerEndpoint("/room/{country}")
public class RoomWebSocket {
    private static final long serialVersionUID = 1L;
    private static final Map<String,  Map<String,  Session>> sessionPool
            = Collections.synchronizedMap(new HashMap<String, Map<String,  Session>>());
    private static Map<String,  Session> sessions;
    private Boolean isOn = false;

    @OnOpen
    public void onConnectionOpen(Session session, @PathParam("country") String country) {
        sessions = sessionPool.get(country);
        if (sessions == null) {
            sessions = Collections.synchronizedMap(new HashMap<String, Session>());
        }
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

