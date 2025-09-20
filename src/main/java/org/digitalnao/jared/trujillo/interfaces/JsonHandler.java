package org.digitalnao.jared.trujillo.interfaces;

import org.digitalnao.jared.trujillo.exceptions.JsonHandlerException;

import java.util.List;

public interface JsonHandler {
    <T> T fromJson(String filename, Class<T> type) throws JsonHandlerException;
    <T> List<T> fromJsonList(String filename, Class<T> type) throws JsonHandlerException;
}
