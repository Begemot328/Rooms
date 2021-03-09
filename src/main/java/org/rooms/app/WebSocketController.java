package org.rooms.app;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@ServerEndpoint("/room/{country}")
public class WebSocketController {

    private static final long serialVersionUID = 1L;
    private static final Map<String, RoomWebSocket> socketPool
            = Collections.synchronizedMap(new HashMap<String, RoomWebSocket>());

    @OnOpen
    public void onConnectionOpen(Session session, @PathParam("country") String countryCode) {
        System.out.println("conn");
        getSocket(countryCode).onConnectionOpen(session);
    }

    @OnClose
    public void onClose(Session session, @PathParam("country") String countryCode) {
        getSocket(countryCode).onClose(session);
    }

    @OnMessage
    public void onMessage(Session session, String isOn, @PathParam("country") String countryCode) {
        getSocket(countryCode).onMessage(session, isOn);
        System.out.println(isOn);
    }

    private RoomWebSocket getSocket(String countryCode) {
        RoomWebSocket socket = socketPool.get(countryCode);

        if (socket == null) {
            socket = new RoomWebSocket();
            socketPool.put(countryCode, socket);
        }
        return  socket;
    }

}
