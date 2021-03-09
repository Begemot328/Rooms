package org.rooms.app;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Locale;

@WebServlet(urlPatterns = "/room")
public class Controller extends HttpServlet {

    private static final String WRONG_COUNTRY = "Wrong country";

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String countryCode = "";

        try {
            countryCode = new CountryObject(getClientIp(request)).getCode();
        } catch (CountryException e) {
            request.setAttribute(RequestParameters.ERROR_MESSAGE, e.getMessage());
            request.getRequestDispatcher(JSPPages.ERROR_PAGE.getPage()).forward(request, response);
        }

        if (countryCode.toLowerCase().equals(request.getParameter(RequestParameters.COUNTRY_CODE))
                || request.getParameter(RequestParameters.AUTO) != null) {
            request.setAttribute(RequestParameters.COUNTRY_CODE, countryCode);
            request.setAttribute(RequestParameters.COUNTRY_NAME, new Locale(countryCode).getDisplayCountry());
            request.getRequestDispatcher(JSPPages.ROOM_PAGE.getPage()).forward(request, response);
        } else {
            request.setAttribute(RequestParameters.ERROR_MESSAGE, WRONG_COUNTRY);
            request.getRequestDispatcher(JSPPages.START_PAGE.getPage()).forward(request, response);
        }
    }

    @Override
    public void init() {
        String[] countryCodes = Locale.getISOCountries();
        Locale[] locales = new Locale[countryCodes.length];

        for (int i = 0; i < countryCodes.length; i++) {
            locales[i] = new Locale("", countryCodes[i]);
        }

        ServletContext context = getServletContext();
        context.setAttribute(RequestParameters.LOCALES,
                locales);
        context.setAttribute(RequestParameters.COUNTRY_NAMES,
                Locale.getISOCountries());
    }

    private static String getClientIp(HttpServletRequest request) {

        String remoteAddr = "";

        if (request != null) {
            remoteAddr = request.getHeader("X-FORWARDED-FOR");
            if (remoteAddr == null || "".equals(remoteAddr)) {
                remoteAddr = request.getRemoteAddr();
            }
        }
        return remoteAddr;
    }
}
