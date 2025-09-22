package org.digitalnao.jared.trujillo.handlers;

import org.digitalnao.jared.trujillo.interfaces.JsonHandler;

/**
 * Factory for creating {@link JsonHandler} instances.
 */
public final class JsonHandlerFactory {

    private JsonHandlerFactory() {
        // utility class; not meant to be instantiated
    }

    /**
     * Creates a default JSON handler backed by Jackson.
     *
     * @return a new {@link JsonHandler} instance
     */
    public static JsonHandler createJsonHandler() {
        return new JsonJacksonHandler();
    }
}
