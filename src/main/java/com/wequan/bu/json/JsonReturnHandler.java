package com.wequan.bu.json;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.method.support.ModelAndViewContainer;

public class JsonReturnHandler implements HandlerMethodReturnValueHandler{
    @Override
    public boolean supportsReturnType(MethodParameter returnType) {
        boolean hasJsonAnno= returnType.getMethodAnnotation(JSON.class) != null;
        return hasJsonAnno;
    }

    @Override
    public void handleReturnValue(Object returnValue, MethodParameter returnType, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest) throws Exception {
        mavContainer.setRequestHandled(true);

        HttpServletResponse response = webRequest.getNativeResponse(HttpServletResponse.class);
        Annotation[] annos = returnType.getMethodAnnotations();
        CustomerJsonSerializer jsonSerializer = new CustomerJsonSerializer();
        Arrays.asList(annos).forEach(a -> {
            if (a instanceof JSON) {
                JSON json = (JSON) a;
                jsonSerializer.filter(json);
            } else if (a instanceof JSONS) {
                JSONS jsons = (JSONS) a;
                Arrays.asList(jsons.value()).forEach(json -> {
                    jsonSerializer.filter(json);
                });
            }
        });

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        String json = jsonSerializer.toJson(returnValue);
        response.getWriter().write(json);
    }
}