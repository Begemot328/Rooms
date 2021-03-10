package org.rooms.app;

/**
 * Class JSP pages names
 *
 * @author Yury Zmushko
 * @version 1.0
 */
public enum JSPPages {
    ERROR_PAGE("WEB-INF/jsp/error.jsp"),
    START_PAGE("WEB-INF/jsp/index.jsp"),
    ROOM_PAGE("WEB-INF/jsp/room.jsp");

    private final String page;

    /**
     * Constructor
     *
     * @param page - page to forward.
     */
    JSPPages (String page) {
        this.page = page;
    }

    public String getPage() {
        return this.page;
    }
}
