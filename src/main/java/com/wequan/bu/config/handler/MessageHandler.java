package com.wequan.bu.config.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.web.servlet.support.RequestContextUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @author ChrisChen
 */
public class MessageHandler {

    private static final Logger logger = LoggerFactory.getLogger(MessageHandler.class);

    @Autowired
    private HttpServletRequest request;
    @Autowired
    private MessageSource messageSource;
    @Autowired
    private ObjectMapper objectMapper;

    /**
     * message from messages.properties
     * @param code
     * @param params
     * @return
     */
    public String getMessage(String code, Object... params) {
        return messageSource.getMessage(code, params, RequestContextUtils.getLocale(request));
    }

    /**
     * convert object to json response
     * @param resp
     * @return
     */
    public String getResponseMessage(Object resp) {
        String msg = "";
        try {
            msg = objectMapper.writeValueAsString(resp);
        } catch (JsonProcessingException e) {
            msg = getMessage("ErrorLogCheck", resp.toString());
            logger.error("getResponseMessage()", e);
        }
        return msg;
    }

    /**
     * generate standard json response
     * @param result
     * @param statusCode
     * @param message
     * @param info
     * @return
     */
    public String getResponseMessage(boolean result, int statusCode, String message, Object info) {
        Map<String, Object> resp = new HashMap<>(4);
        resp.put("result", result);
        resp.put("statusCode", statusCode);
        resp.put("message", message);
        resp.put("info", info);
        return getResponseMessage(resp);
    }

    /**
     * generate standard json response with result=true and info=""
     * @param statusCode
     * @param message
     * @return
     */
    public String getResponseMessage(int statusCode, String message) {
        return getResponseMessage(true, statusCode, message, "");
    }

    /**
     * generate standard json response with result=true, info="" and message from {@code getMessage(String, Object...)}
     * @param statusCode
     * @param params
     * @return
     */
    public String getResponseMessage(String statusCode, Object... params) {
        return getResponseMessage(Integer.parseInt(statusCode), getMessage(statusCode, params));
    }
}
