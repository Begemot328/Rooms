package org.rooms.app.listener;


import org.rooms.app.controller.Controller;
import org.rooms.app.controller.RequestParameters;
import org.rooms.app.service.RoomJsonConverter;
import org.rooms.app.service.Rooms;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.util.Locale;

@WebListener
public class StartEndListener implements ServletContextListener {

    private static final Logger logger = LoggerFactory.getLogger(StartEndListener.class);

    public void contextInitialized (ServletContextEvent ev) {
        ServletContext context = ev.getServletContext();
        String[] countryCodes = Locale.getISOCountries();
        Locale[] locales = new Locale[countryCodes.length];

        for (int i = 0; i < countryCodes.length; i++) {
            locales[i] = new Locale("", countryCodes[i]);
        }

        context.setAttribute(RequestParameters.LOCALES,
                locales);
        context.setAttribute(RequestParameters.COUNTRY_NAMES,
                Locale.getISOCountries());

        new RoomJsonConverter().open(Rooms.getInstance());
    }

    public void contextDestroyed (ServletContextEvent ev) {
        ServletContext context = ev.getServletContext();
        context.setAttribute(RequestParameters.LOCALES,
                null);
        context.setAttribute(RequestParameters.COUNTRY_NAMES,
                null);
    }
}
