package com.ijinshan.sjk.config;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonObjectMapperFactory {
    private static final Logger logger = LoggerFactory.getLogger(JsonObjectMapperFactory.class);

    private static class Instance {
        // final
        private static final JsonObjectMapperFactory instance = new JsonObjectMapperFactory();
    }

    public static JsonObjectMapperFactory getInstance() {
        return Instance.instance;
    }

    private JsonObjectMapperFactory() {

    }

    private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    private final ObjectMapper objectMapper = new ObjectMapper().setDateFormat(dateFormat);

    public ObjectMapper getObjectMapper() {
        return objectMapper;
    }

}
