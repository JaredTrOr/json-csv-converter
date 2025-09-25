package org.digitalnao.jared.trujillo.interfaces;

import com.fasterxml.jackson.core.type.TypeReference;
import org.digitalnao.jared.trujillo.exceptions.JsonHandlerException;

import java.util.List;

/**
 * Contract for reading JSON from files into typed objects.
 */
public interface JsonHandler {

    /**
     * Reads a JSON object from a file into the given type.
     *
     * @param <T>      target type
     * @param filename path to the JSON file (expects {@code .json})
     * @param type     target class (non-null)
     * @return deserialized instance
     * @throws JsonHandlerException if the filename is invalid, the file is missing/unreadable/empty,
     *                              or parsing/mapping fails
     */
    <T> T fromJson(String filename, Class<T> type) throws JsonHandlerException;

    /**
     * Reads a JSON array from a file into a {@code List<T>}.
     *
     * @param <T>      element type
     * @param filename path to the JSON file (expects {@code .json})
     * @param type     element class (non-null)
     * @return list of deserialized elements
     * @throws JsonHandlerException if the filename is invalid, the file is missing/unreadable/empty,
     *                              or content is not an array / mapping fails
     */
    <T> List<T> fromJsonList(String filename, Class<T> type) throws JsonHandlerException;

    /**
     * Reads JSON from a file using a {@link TypeReference} for complex/weird or generic types
     *
     * @param <T>      target type inferred from {@code typeRef}
     * @param filename path to the JSON file (expects {@code .json})
     * @param typeRef  Jackson type reference (non-null)
     * @return deserialized value
     * @throws JsonHandlerException if the filename is invalid, the file is missing/unreadable/empty,
     *                              or parsing/mapping fails
     */
    <T> T fromJson(String filename, TypeReference<T> typeRef) throws JsonHandlerException;

    /**
     * Checks if a file is a JSON file of objects or an array
     *
     * @param filename the filename
     * @return the boolean
     * @throws JsonHandlerException the json handler exception
     */
    boolean isJsonArray(String filename) throws JsonHandlerException;
}
