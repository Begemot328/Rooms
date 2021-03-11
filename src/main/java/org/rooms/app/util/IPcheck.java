package org.rooms.app.util;

import org.rooms.app.controller.Controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;

public class IPcheck {
    private static final Logger logger = LoggerFactory.getLogger(IPcheck.class);

    public static String getClientIp(HttpServletRequest request) {
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
