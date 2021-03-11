package org.rooms.app.service;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.rooms.app.controller.Controller;
import org.rooms.app.exception.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContext;
import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class RoomJsonConverter {
    private static final String FILENAME = "c:/resources/countries.json";
    private static final Logger logger = LoggerFactory.getLogger(Controller.class);
    private static final RoomJsonConverter INSTANCE = new RoomJsonConverter();

    private String filename;

    public static RoomJsonConverter getInstance() {
        return INSTANCE;
    }

    private  RoomJsonConverter() {}

    public Map<String, Boolean> read (String filename) {
        Map<String, Boolean> map = new HashMap<>();

        this.filename = filename;

        JSONParser jsonParser = new JSONParser();
        logger.debug("File " + new File(filename).getAbsolutePath());
        try (FileReader reader = new FileReader(filename)) {

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

    public Map<String, Boolean> read () {
        return read(FILENAME);
    }

    public void open(Rooms rooms) {
        rooms.getRooms().putAll(read());
    }

    public void open(Rooms rooms, String filename) {
        rooms.getRooms().putAll(read(filename));
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
        if (!(new File(filename).exists())) {
            try {
                new File(filename).createNewFile();
            } catch (IOException e) {
                throw new JSONException(e);
            }
        }
        try (FileWriter file = new FileWriter(filename)) {

            file.write(rooms);
            file.flush();
        } catch (IOException e) {
            throw new JSONException(e);
        }
        logger.debug(new File(filename).getAbsolutePath());
    }
}
