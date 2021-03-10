package org.rooms.app.websocket;

import org.rooms.app.service.Rooms;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@ServerEndpoint("/room/{country}")
public class RoomWebSocket {

    static Logger logger = LoggerFactory.getLogger(RoomWebSocket.class);
    private static final long serialVersionUID = 1L;
    private static final Map<String,  Map<String,  Session>> sessionPool
            = Collections.synchronizedMap(new HashMap<String, Map<String,  Session>>());
    private static Map<String,  Session> sessions;

    @OnOpen
    public void onConnectionOpen(Session session, @PathParam("country") String country) {
        sessions = sessionPool.get(country);
        logger.debug("connected " + session.getId() + " to room " + country);
        if (sessions == null) {
            sessions = Collections.synchronizedMap(new HashMap<String, Session>());
        }
        sessionPool.put(country, sessions);
        sessions.put(session.getId(), session);
    }

    @OnClose
    public void onClose(Session session, @PathParam("country") String country) {
        sessions = sessionPool.get(country);
        if (sessions == null) {
            throw new RuntimeException("no such connection");
        }
        sessions.remove(session.getId());
        logger.debug("disconnected " + session.getId() + " from room " + country);
    }

    @OnMessage
    public void onMessage(Session session, @PathParam("country") String country, String isOn) {
        logger.debug(country + " message " + isOn + " from " + session.getId());

        Rooms.getInstance().setRoom(country, Boolean.valueOf(isOn));

        sessions = sessionPool.get(country);
        if (sessions == null) {
            throw new RuntimeException("no such connection");
        }
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

