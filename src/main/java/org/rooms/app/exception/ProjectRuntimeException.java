package org.rooms.app.exception;

public class ProjectRuntimeException extends RuntimeException{

    /**
     * Constructor
     *
     * @param message the message
     */
    public ProjectRuntimeException(String message) {
        super(message);
    }

    /**
     * Constructor
     *
     * @param message the message
     * @param e the e
     */
    public ProjectRuntimeException(String message, Exception e) {
        super(message + e.getLocalizedMessage());
        this.setStackTrace (e.getStackTrace());
    }

    /**
     * Constructor
     *
     * @param e the e
     */
    public ProjectRuntimeException(Exception e) {
        super(e.getClass().getSimpleName() + ": " +  e.getLocalizedMessage());
        this.setStackTrace (e.getStackTrace());
    }
}
