package org.rooms.app.service;

import java.util.HashMap;
import java.util.Map;

public class Rooms {

    private final static Rooms INSTANCE = new Rooms();
    private Map<String, Boolean> rooms = new HashMap<>();

    public static Rooms getInstance() {
        return INSTANCE;
    }

    public Map<String, Boolean> getRooms() {
        return rooms;
    }

    public void setOn(String room) {
        rooms.put(room, true);
    }

    public void setOff(String room) {
        rooms.put(room, false);
    }

    public void setRoom(String room, Boolean isOn) {
        rooms.put(room, isOn);
    }

    public boolean getRoom(String room) {
        rooms.putIfAbsent(room, false);
        return rooms.get(room);
    }
}
