package org.digitalnao.jared.trujillo.handlers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.exc.InvalidDefinitionException;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import org.digitalnao.jared.trujillo.exceptions.CsvHandlerException;
import org.digitalnao.jared.trujillo.exceptions.JsonHandlerException;
import org.digitalnao.jared.trujillo.interfaces.CsvHandler;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class CsvJacksonHandler implements CsvHandler {

    CsvMapper csvMapper = new CsvMapper();

    @Override
    public <T> void writeToCsv(T object, Class<T> type, String filename) throws CsvHandlerException {
        validateFilename(filename);
        validateType(type);
        validateObject(object);

        try {
            CsvSchema schema = csvMapper.schemaFor(type).withHeader();
            String csv = csvMapper.writer(schema).writeValueAsString(object);
            Files.writeString(Path.of(filename + ".csv"), csv);
            System.out.println("CSV file generated successfully");
        } catch (Exception e) {
            throw this.handleException(e);
        }
    }

    @Override
    public <T> void writeToCsv(List<T> list, Class<T> type, String filename) throws CsvHandlerException {
        validateFilename(filename);
        validateType(type);
        validateList(list);

        try {
            CsvSchema schema = csvMapper.schemaFor(type).withHeader();
            String csv = csvMapper.writer(schema).writeValueAsString(list);
            Files.writeString(Path.of(filename + ".csv"), csv);
            System.out.println("CSV list generated successfully");
        } catch (Exception e) {
            throw this.handleException(e);
        }
    }

    private CsvHandlerException handleException(Exception e) {
        String message = e.getMessage();

        if (e instanceof InvalidDefinitionException) {
            return new CsvHandlerException("Invalid object definition for CSV: ", e);
        }
        if (e instanceof MismatchedInputException) {
            return new CsvHandlerException("CSV data doesn't match expected structure: ", e);
        }
        if (e instanceof JsonProcessingException) {
            return new CsvHandlerException("CSV processing error "+ message, e);
        }

        if (e instanceof FileNotFoundException) {
            return new CsvHandlerException("CSV file not found: " + message, e);
        }
        if (e instanceof IOException) {
            return new CsvHandlerException("I/O error accessing CSV file: " + message, e);
        }

        if (e instanceof IllegalArgumentException) {
            return new CsvHandlerException("Invalid argument: " + message, e);
        }

        return new CsvHandlerException("Unexpected error processing CSV: " + message, e);
    }

    private void validateFilename(String filename) {
        if (filename == null || filename.isBlank()) {
            throw new JsonHandlerException("The filename cannot be null neither blank");
        }
    }

    private <T> void validateType(Class<T> type) {
        if (type == null) {
            throw new CsvHandlerException("The type parameter cannot be null.");
        }
    }

    private <T> void validateObject(T object) {
        if (object == null) {
            throw new CsvHandlerException("The object to write cannot be null.");
        }
    }

    private <T> void validateList(List<T> list) {
        if (list == null || list.isEmpty()) {
            throw new CsvHandlerException("The list cannot be null or empty.");
        }
    }
}
