package org.digitalnao.jared.trujillo.builders;

import org.digitalnao.jared.trujillo.handlers.JsonJacksonHandler;
import org.digitalnao.jared.trujillo.interfaces.JsonHandler;

public class JsonHandlerFactory {

    public static JsonHandler createJsonHandler() {
        return new JsonJacksonHandler();
    }
}
