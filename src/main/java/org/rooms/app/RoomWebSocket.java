package org.rooms.app;


import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@ServerEndpoint("/room")
public class RoomWebSocket {
    private static final long serialVersionUID = 1L;
    private static final Map<String, Session> sessions = Collections.synchronizedMap(new HashMap<String, Session>());
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
    public void onMessage(Session session, Boolean isOn) {
        this.isOn = isOn;

        for (Map.Entry<String, Session> peer : sessions.entrySet()) {
          //  if (!peer.getValue().equals(session))
                try {
                    peer.getValue().getBasicRemote().sendObject(isOn);
                } catch (IOException | EncodeException e) {
                    e.printStackTrace();
                }
         //   }
        }
    }
}
