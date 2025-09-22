package org.digitalnao.jared.trujillo.handlers;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
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
        this.validateFilename(filename);
        this.validateType(type);
        File file = this.validateFileInput(filename);
        try {
            return mapper.readValue(file, type);
        } catch(Exception e) {
            throw this.handleException(e);
        }
    }

    @Override
    public <T> List<T> fromJsonList(String filename, Class<T> type) throws JsonHandlerException {
        this.validateFilename(filename);
        this.validateType(type);
        File file = this.validateFileInput(filename);
        try {
            CollectionType listType = mapper.getTypeFactory().constructCollectionType(List.class, type);
            return mapper.readValue(file, listType);
        } catch(Exception e) {
            throw this.handleException(e);
        }
    }

    // Function for weird json data format
    @Override
    public <T> T fromJson(String filename, TypeReference<T> typeRef) throws JsonHandlerException {
        this.validateFilename(filename);
        this.validateTypeReference(typeRef);
        File file = this.validateFileInput(filename);
        try {
            return mapper.readValue(file, typeRef);
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

    private void validateFilename(String filename) {
        if (filename == null || filename.isBlank()) {
            throw new JsonHandlerException("The filename cannot be null neither blank");
        }
        if (!filename.toLowerCase().endsWith(".json")) {
            throw new JsonHandlerException("The filename needs to have the extension .json: " + filename);
        }
    }

    private <T> void validateType(Class<T> type) {
        if (type == null) {
            throw new JsonHandlerException("The parameter 'type' cannot be null.");
        }
    }

    private <T> void validateTypeReference(TypeReference<T> typeRef) {
        if (typeRef == null) {
            throw new JsonHandlerException("The parameter 'typeRef' cannot be null.");
        }
    }

    private File validateFileInput(String filename) {
        File file = new File(filename);
        if (!file.exists()) {
            throw new JsonHandlerException("File not found: " + filename);
        }
        if (!file.isFile()) {
            throw new JsonHandlerException("The path is not a regular file: " + filename);
        }
        if (!file.canRead()) {
            throw new JsonHandlerException("Read permission denied for file: " + filename);
        }
        if (file.length() == 0) {
            throw new JsonHandlerException("The file is empty: " + filename);
        }

        return file;
    }

}
