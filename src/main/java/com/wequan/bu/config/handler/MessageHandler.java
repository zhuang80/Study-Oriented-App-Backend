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
    private static final String DEFAULT_SUCCESS_MESSAGE = "success";

    @Autowired
    private HttpServletRequest request;
    @Autowired
    private MessageSource messageSource;
    @Autowired
    private ObjectMapper objectMapper;

    /**
     * message from messages.properties
     * @param code code from messages.properties
     * @param params escape for "
     * @return message
     */
    private String getMessage(String code, Object... params) {
        return messageSource.getMessage(code, params, RequestContextUtils.getLocale(request));
    }

    /**
     * convert object to json response
     * @param resp response object
     * @return json string
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
     * generate standard successful json response with data
     * @return successful json string
     */
    public String getSuccessResponseMessage(Object data) {
        Map<String, Object> resp = new HashMap<>(3);
        resp.put("statusCode", "200");
        resp.put("message", DEFAULT_SUCCESS_MESSAGE);
        resp.put("data", data);
        return getResponseMessage(resp);
    }

    /**
     * generate standard successful json response without data
     * @return successful json string
     */
    public String getSuccessResponseMessage() {
        Map<String, Object> resp = new HashMap<>(2);
        resp.put("statusCode", "200");
        resp.put("message", DEFAULT_SUCCESS_MESSAGE);
        return getResponseMessage(resp);
    }

    /**
     * generate standard fail json response with message from {@code getMessage(String, Object...)}
     * @param statusCode statusCode from messages.properties
     * @param params escape for "
     * @return fail json string
     */
    public String getFailResponseMessage(String statusCode, Object... params) {
        Map<String, Object> resp = new HashMap<>(2);
        resp.put("statusCode", statusCode);
        resp.put("message", getMessage(statusCode, params));
        return getResponseMessage(resp);
    }
}
