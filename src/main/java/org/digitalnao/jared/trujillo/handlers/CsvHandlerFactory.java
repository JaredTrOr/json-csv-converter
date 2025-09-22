package org.digitalnao.jared.trujillo.handlers;

import org.digitalnao.jared.trujillo.interfaces.CsvHandler;

/**
 * Factory for creating {@link CsvHandler} instances.
 */
public final class CsvHandlerFactory {

    private CsvHandlerFactory() {
        // utility class; not meant to be instantiated
    }

    /**
     * Creates a default CSV handler backed by Jackson.
     *
     * @return a new {@link CsvHandler} instance
     */
    public static CsvHandler createCsvHandler() {
        return new CsvJacksonHandler();
    }
}
