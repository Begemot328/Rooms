package org.rooms.app.listener;


import org.rooms.app.controller.RequestParameters;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.util.Locale;

@WebListener
public class StartEndListener implements ServletContextListener {

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
    }

    public void contextDestroyed (ServletContextEvent ev) {
        ServletContext context = ev.getServletContext();
        context.setAttribute(RequestParameters.LOCALES,
                null);
        context.setAttribute(RequestParameters.COUNTRY_NAMES,
                null);
    }
}
