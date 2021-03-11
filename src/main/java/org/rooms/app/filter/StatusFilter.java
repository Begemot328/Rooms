package org.rooms.app.filter;

import org.rooms.app.controller.JSPPages;
import org.rooms.app.controller.RequestParameters;
import org.rooms.app.exception.CountryException;
import org.rooms.app.service.CountryObject;
import org.rooms.app.util.IPcheck;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(urlPatterns = {"/room/*"})
public class StatusFilter implements Filter {

    private static final String WRONG_COUNTRY = "Wrong country!";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
            throws IOException, ServletException {
        String countryCode = "";
        String countryName = "";

        if (request.getParameter(RequestParameters.LOCAL) != null) {
            filterChain.doFilter(request, response);
        } else {
            CountryObject country = null;
            try {
                country = new CountryObject(IPcheck.getClientIp((HttpServletRequest) request));
            } catch (CountryException e) {
                request.setAttribute(RequestParameters.ERROR_MESSAGE, e.getMessage());
                request.getRequestDispatcher(JSPPages.ERROR_PAGE.getPage()).forward(request, response);
                return;
            }
            countryCode = country.getCode();

            if(!request.getParameter(RequestParameters.COUNTRY_CODE).equalsIgnoreCase(countryCode)) {
                request.setAttribute(RequestParameters.ERROR_MESSAGE, WRONG_COUNTRY);
                request.getRequestDispatcher(JSPPages.ERROR_PAGE.getPage()).forward(request, response);
            } else {
                filterChain.doFilter(request,response);
            }
        }
    }

    @Override
    public void destroy() {

    }
}
