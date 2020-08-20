package com.wequan.bu.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class TimeConvertTool {
    private static final Logger log = LoggerFactory.getLogger(TimeConvertTool.class);

    public static ZonedDateTime convertToSystemZonedDateTime(LocalDateTime local, ZoneId zone) {
        ZonedDateTime now = ZonedDateTime.now();
        log.info(String.format("The current system time is %s", now));

        log.info("The time zone specified by client is " + zone);
        //associate local date time to a time zone
        ZonedDateTime target = local.atZone(zone);

        log.info("The zoned date time specifyied by client is " + target);

        //convert to system time zone
        ZonedDateTime result = target.withZoneSameInstant(now.getZone());

        log.info("The corresponding system zoned date time is  " + result);

        return result;
    }
}
