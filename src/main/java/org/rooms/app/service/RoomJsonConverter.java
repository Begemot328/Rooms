package org.rooms.app.service;

import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.rooms.app.controller.Controller;
import org.rooms.app.exception.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class RoomJsonConverter {
    private static final String FILENAME = "${catalina.home}/resources/countries.json";
    private static final Logger logger = LoggerFactory.getLogger(Controller.class);

    public Map<String, Boolean> read () {
        Map<String, Boolean> map = new HashMap<>();

        JSONParser jsonParser = new JSONParser();

        try (FileReader reader = new FileReader(FILENAME)) {

            //Read JSON file
            Object obj = jsonParser.parse(reader);
            JSONObject countries = (JSONObject) obj;
            logger.debug(countries.toString());

            for (String key : countries.keySet()) {
                map.put(key, countries.getBoolean(key));
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
        try (FileWriter file = new FileWriter(FILENAME)) {
            file.write(rooms);
            file.flush();

        } catch (IOException e) {
            throw new JSONException(e);
        }
    }


}
