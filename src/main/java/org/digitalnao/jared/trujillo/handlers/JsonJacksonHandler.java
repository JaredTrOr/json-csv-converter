package org.digitalnao.jared.trujillo.handlers;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import org.digitalnao.jared.trujillo.exceptions.JsonHandlerException;
import org.digitalnao.jared.trujillo.interfaces.JsonHandler;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class JsonJacksonHandler implements JsonHandler {

    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public <T> T fromJson(String filename, Class<T> type) throws JsonHandlerException {
        try {
            return mapper.readValue(new File(filename), type);
        } catch(Exception e) {
            throw this.handleException(e);
        }
    }

    @Override
    public <T> List<T> fromJsonList(String filename, Class<T> type) throws JsonHandlerException {
        try {
            CollectionType listType = mapper.getTypeFactory().constructCollectionType(List.class, type);
            return mapper.readValue(new File(filename), listType);
        } catch(Exception e) {
            throw this.handleException(e);
        }
    }


    private JsonHandlerException handleException(Exception e) {
        if (e instanceof JsonParseException) {
            return new JsonHandlerException("Malformed JSON ", e);
        }
        if (e instanceof JsonMappingException) {
            return new JsonHandlerException("JSON structure does not match ", e);
        }
        if (e instanceof IOException) {
            return new JsonHandlerException("Could not read IOException" ,e);
        }
        return new JsonHandlerException("Unknown error while deserializing " ,e);
    }
}
