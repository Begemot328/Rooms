package org.rooms.app.filter;

import org.rooms.app.controller.Controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@WebFilter(urlPatterns = {"/*"})
public class XSSFilter implements Filter {
    public static final String BLANK = "";
    private static final Logger logger = LoggerFactory.getLogger(XSSFilter.class);

    ArrayList<Pattern> patterns = new ArrayList<>();

    public void init(FilterConfig filterConfig) throws ServletException {
        patterns.add(Pattern.compile("<script>(.*?)</script>", Pattern.CASE_INSENSITIVE));
        patterns.add(Pattern.compile("src[\r\n]*=[\r\n]*\\'(.*?)\\'",
                Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL));
        patterns.add(Pattern.compile("src[\r\n]*=[\r\n]*\\\"(.*?)\\\"",
                Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL));
        patterns.add(Pattern.compile("</script>", Pattern.CASE_INSENSITIVE));
        patterns.add(Pattern.compile("<script(.*?)>",
                Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL));
        patterns.add(Pattern.compile("eval\\((.*?)\\)",
                Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL));
        patterns.add(Pattern.compile("expression\\((.*?)\\)",
                Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL));
        patterns.add(Pattern.compile("javascript:", Pattern.CASE_INSENSITIVE));
        patterns.add(Pattern.compile("vbscript:", Pattern.CASE_INSENSITIVE));
        patterns.add(Pattern.compile("onload(.*?)=",
                Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL));
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        boolean filterWorked = false;
        HttpServletRequestSetParameterWrapper request = (new HttpServletRequestSetParameterWrapper(
                (HttpServletRequest) servletRequest));

        for (String key : servletRequest.getParameterMap().keySet()) {
            for (Pattern pattern: patterns) {
                Matcher matcher = pattern.matcher(servletRequest.getParameter(key));
                if (matcher.matches()) {
                    filterWorked = true;
                    request.setParameter(key, matcher.replaceAll(BLANK));
                    logger.warn(matcher.group(0) +  " replaced");
                }
            }
        }

        if (filterWorked) {
            filterChain.doFilter(request, servletResponse);
        } else {
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }

    @Override
    public void destroy() {

    }
}
