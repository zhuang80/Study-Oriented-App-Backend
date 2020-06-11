package com.wequan.bu.config.handler;

import com.wequan.bu.exception.NotImplementedException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

/**
 * @author ChrisChen
 */
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({NotImplementedException.class, IllegalArgumentException.class, NullPointerException.class, BadSqlGrammarException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Map<String, String> internalServerErrorExceptionHandler(RuntimeException exception) {
        Map<String, String> map = new HashMap<>(1);
        map.put("message", exception.getMessage());
        return map;
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
        if (body == null) {
            Map<String, Object> resp = new HashMap<>(3);
            resp.put("statusCode", status.value());
            resp.put("exception", ex.getClass().toString());
            resp.put("message", ex.getMessage());
            body = resp;
        }
        return super.handleExceptionInternal(ex, body, headers, status, request);
    }
}
