package org.rooms.app.controller;

import org.rooms.app.exception.CountryException;
import org.rooms.app.exception.JSONException;
import org.rooms.app.service.CountryObject;
import org.rooms.app.service.RoomJsonConverter;
import org.rooms.app.service.Rooms;
import org.rooms.app.util.IPcheck;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

@WebServlet(urlPatterns = "/rooms")
public class Controller extends HttpServlet {

    private static final String WRONG_COUNTRY = "Wrong country";
    private static final Logger logger = LoggerFactory.getLogger(Controller.class);

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String countryCode = "";
        String countryName = "";



        if (request.getParameter(RequestParameters.LOCAL) != null) {
            request.setAttribute(RequestParameters.COUNTRY_CODE, CountryObject.LOCALHOST_CODE);
            request.setAttribute(RequestParameters.COUNTRY_NAME, CountryObject.LOCALHOST_NAME);
            request.setAttribute(RequestParameters.STATUS, Rooms.getInstance().getRoom(countryCode));
            logger.debug(Rooms.getInstance().getRoom(countryCode) + "");
            request.getRequestDispatcher(JSPPages.ROOM_PAGE.getPage()).forward(request, response);
        } else if (request.getParameter(RequestParameters.AUTO) != null) {
            try {
                CountryObject country = new CountryObject(IPcheck.getClientIp(request));
                countryCode = country.getCode();
                countryName = country.getName();
            } catch (CountryException e) {
                request.setAttribute(RequestParameters.ERROR_MESSAGE, e.getMessage());
                request.getRequestDispatcher(JSPPages.ERROR_PAGE.getPage()).forward(request, response);
            }

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
    public void destroy() {
        new RoomJsonConverter().save(Rooms.getInstance());
        logger.debug("destroy");
    }

    @Override
    public void init() {

    }
}
