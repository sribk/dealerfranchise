package com.sri.dealerfranchise.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class JsonMarshaller {

    private Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    @Autowired
    private ObjectMapper objectMapper;


    public String marshal(Object object) {
        String json = "";

        try {
            json = objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            logger.warn(e.getMessage(), e);
        }

        return json;
    }

    public Object unmarshal(String json, Class<?> clazz) {
        Object object = null;

        try {
            object = objectMapper.readValue(json, clazz);
        } catch (IOException e) {
            logger.warn(e.getMessage(), e);
        }

        return object;
    }

    List<Object> unmarshalList(String json, Class<?> clazz) {
        List<Object> objects = new ArrayList<>();

        CollectionType type = objectMapper.getTypeFactory().constructCollectionType(List.class, clazz);
        try {
            objects = objectMapper.readValue(json, type);
        } catch (IOException e) {
            logger.warn(e.getMessage(), e);
        }

        return objects;
    }

}
