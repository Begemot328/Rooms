package org.rooms.app.controller;

import org.rooms.app.exception.CountryException;
import org.rooms.app.service.CountryObject;
import org.rooms.app.service.Rooms;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/room")
public class Controller extends HttpServlet {

    private static final String WRONG_COUNTRY = "Wrong country";
    private static final Logger logger = LoggerFactory.getLogger(Controller.class);

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String countryCode = "";
        String countryName = "";

        try {
            CountryObject country = new CountryObject(getClientIp(request));
            countryCode = country.getCode();
            countryName = country.getName();
        } catch (CountryException e) {
            request.setAttribute(RequestParameters.ERROR_MESSAGE, e.getMessage());
            request.getRequestDispatcher(JSPPages.ERROR_PAGE.getPage()).forward(request, response);
        }

        if (request.getParameter(RequestParameters.LOCAL) != null) {
            request.setAttribute(RequestParameters.COUNTRY_CODE, CountryObject.LOCALHOST_CODE);
            request.setAttribute(RequestParameters.COUNTRY_NAME, CountryObject.LOCALHOST_NAME);
            request.setAttribute(RequestParameters.STATUS, Rooms.getInstance().getRoom(countryCode));
            logger.debug(Rooms.getInstance().getRoom(countryCode) + "");
            request.getRequestDispatcher(JSPPages.ROOM_PAGE.getPage()).forward(request, response);
        } else if (countryCode.toLowerCase().equals(request.getParameter(RequestParameters.COUNTRY_CODE))
                || request.getParameter(RequestParameters.AUTO) != null) {
            request.setAttribute(RequestParameters.COUNTRY_CODE, countryCode);
            request.setAttribute(RequestParameters.COUNTRY_NAME, countryName);
            request.setAttribute(RequestParameters.STATUS, Rooms.getInstance().getRoom(countryCode));
            request.getRequestDispatcher(JSPPages.ROOM_PAGE.getPage()).forward(request, response);
        } else {
            request.setAttribute(RequestParameters.ERROR_MESSAGE, WRONG_COUNTRY);
            request.getRequestDispatcher(JSPPages.START_PAGE.getPage()).forward(request, response);
        }
    }

    @Override
    public void init() {
    }

    private static String getClientIp(HttpServletRequest request) {

        String remoteAddr = "";

        if (request != null) {
            remoteAddr = request.getHeader("X-FORWARDED-FOR");
            if (remoteAddr == null || "".equals(remoteAddr)) {
                remoteAddr = request.getRemoteAddr();
            }
        }
        logger.debug(remoteAddr);
        return remoteAddr;
    }
}
