package org.digitalnao.jared.trujillo.interfaces;

import org.digitalnao.jared.trujillo.exceptions.CsvHandlerException;

import java.util.List;

/**
 * Contract for writing objects and lists to CSV files.
 */
public interface CsvHandler {

    /**
     * Writes a single object to a CSV file with a header row.
     *
     * @param <T>      object type
     * @param object   non-null instance to serialize
     * @param type     class used to derive the CSV schema (non-null)
     * @param filename base filename (the implementation appends {@code .csv})
     * @throws CsvHandlerException if arguments are invalid, serialization fails, or an I/O error occurs
     */
    <T> void writeToCsv(T object, Class<T> type, String filename) throws CsvHandlerException;

    /**
     * Writes a list of objects to a CSV file with a header row.
     *
     * @param <T>      element type
     * @param object   non-null, non-empty list of elements to serialize
     * @param type     class used to derive the CSV schema (non-null)
     * @param filename base filename (the implementation appends {@code .csv})
     * @throws CsvHandlerException if arguments are invalid, serialization fails, or an I/O error occurs
     */
    <T> void writeToCsv(List<T> object, Class<T> type, String filename) throws CsvHandlerException;
}
