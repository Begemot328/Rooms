package org.rooms.app.exception;

public class JSONException extends ProjectRuntimeException{

    public JSONException(String message) {
        super(message);
    }

    public JSONException(String message, Exception e) {
        super(message, e);
    }

    public JSONException(Exception e) {
        super(e);
    }
}
