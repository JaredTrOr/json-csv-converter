package org.digitalnao.jared.trujillo.exceptions;

public class CsvHandlerException extends RuntimeException {
    public CsvHandlerException(String message, Throwable cause) {
        super(message, cause);
    }
    public CsvHandlerException(String message) { super(message); }
}
