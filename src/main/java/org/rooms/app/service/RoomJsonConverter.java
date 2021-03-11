package org.rooms.app.service;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.rooms.app.controller.Controller;
import org.rooms.app.exception.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class RoomJsonConverter {
    private static final String FILENAME = "c:/resources/countries.json";
    private static final Logger logger = LoggerFactory.getLogger(Controller.class);

    public Map<String, Boolean> read () {
        Map<String, Boolean> map = new HashMap<>();

        JSONParser jsonParser = new JSONParser();
        logger.debug("File " + new File(FILENAME).getAbsolutePath());
        try (FileReader reader = new FileReader(FILENAME)) {

            //Read JSON file
            Object obj = jsonParser.parse(reader);
            JSONObject countries = (JSONObject) obj;

            logger.debug("file: " + countries.toString());
            Set keys = countries.keySet();
            for (Object key : keys) {
                if (key instanceof  String && countries.get(key) instanceof Boolean) {
                    map.put((String) key, (Boolean) countries.get(key));
                }
            }
            return map;
        } catch (FileNotFoundException e) {
            return map;
        } catch (ParseException | IOException e) {
            throw new JSONException(e);
        }

    }

    public void open(Rooms rooms) {
        rooms.getRooms().putAll(read());
    }

    public void save(Rooms rooms) {
        writeToFile(write(rooms.getRooms()));
    }

    public String write (Map<String, Boolean>  rooms) {

        JSONObject roomsJSON = new JSONObject();
        for (Map.Entry<String, Boolean> entry : rooms.entrySet()) {
            roomsJSON.put(entry.getKey(), entry.getValue());
        }
        logger.debug(roomsJSON.toString());
        return roomsJSON.toString();
    }

    public void writeToFile (String rooms) {
        if (!(new File(FILENAME).exists())) {
            try {
                new File(FILENAME).createNewFile();
            } catch (IOException e) {
                throw new JSONException(e);
            }
        }
        try (FileWriter file = new FileWriter(FILENAME)) {

            file.write(rooms);
            file.flush();
        } catch (IOException e) {
            throw new JSONException(e);
        }
        logger.debug(new File(FILENAME).getAbsolutePath());
    }
}
