package com.wequan.bu.json;

import io.micrometer.core.instrument.util.StringUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.text.SimpleDateFormat;

/**
 * @author Zhaochao Huang
 */
public class CustomerJsonSerializer {
    ObjectMapper mapper = new ObjectMapper();
    JacksonJsonFilter jacksonFilter = new JacksonJsonFilter();

    public void filter(Class<?> clazz, String[] include, String[] filter) {
        if (clazz == null) return;
        if (include.length != 0) {
            jacksonFilter.include(clazz, include);
        }
        if (filter.length != 0) {
            jacksonFilter.filter(clazz, filter);
        }
        mapper.addMixIn(clazz, jacksonFilter.getClass());
    }

    public String toJson(Object object) throws JsonProcessingException {
        mapper.setFilterProvider(jacksonFilter);
        return mapper.writeValueAsString(object);
    }
    public void filter(JSON json) {
        this.filter(json.type(), json.include(), json.filter());
    }
}