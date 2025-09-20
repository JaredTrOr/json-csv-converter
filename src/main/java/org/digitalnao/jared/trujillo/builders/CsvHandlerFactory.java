package org.digitalnao.jared.trujillo.builders;

import org.digitalnao.jared.trujillo.handlers.CsvJacksonHandler;
import org.digitalnao.jared.trujillo.interfaces.CsvHandler;

public class CsvHandlerFactory {

    public static CsvHandler createCsvHandler() {
        return new CsvJacksonHandler();
    }

}
