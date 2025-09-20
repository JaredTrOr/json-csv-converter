package org.digitalnao.jared.trujillo.interfaces;

import org.digitalnao.jared.trujillo.exceptions.CsvHandlerException;

import java.util.List;

public interface CsvHandler {
    <T> void writeToCsv(T object, Class<T> type, String filename) throws CsvHandlerException;
    <T> void writeToCsv(List<T> object, Class<T> type, String filename) throws CsvHandlerException;

}
