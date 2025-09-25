package org.digitalnao.jared.trujillo.interfaces;
import org.digitalnao.jared.trujillo.exceptions.JsonHandlerException;

import java.util.List;

/**
 * Contract for reading JSON from files into typed objects.
 */
public interface JsonHandler {
    <T> T fromJson(String filename) throws JsonHandlerException;
    <T> List<T> fromJsonList(String filename) throws JsonHandlerException;

}
