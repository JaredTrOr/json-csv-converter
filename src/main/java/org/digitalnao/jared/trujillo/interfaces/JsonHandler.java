package org.digitalnao.jared.trujillo.interfaces;

import com.fasterxml.jackson.core.type.TypeReference;
import org.digitalnao.jared.trujillo.exceptions.JsonHandlerException;

import java.util.List;

public interface JsonHandler {
    <T> T fromJson(String filename, Class<T> type) throws JsonHandlerException;
    <T> List<T> fromJsonList(String filename, Class<T> type) throws JsonHandlerException;

    // for complex/weird JSON structures
    <T> T fromJson(String filename, TypeReference<T> typeRef) throws JsonHandlerException;
}
