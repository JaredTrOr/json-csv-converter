package org.digitalnao.jared.trujillo.handlers;

import org.digitalnao.jared.trujillo.interfaces.JsonHandler;

/**
 * Factory for creating {@link JsonHandler} instances.
 */
public final class JsonHandlerFactory {


    public static <T> JsonHandler createJsonHandler(JsonHandler jsonHandler) {
        return jsonHandler;
    }

}
