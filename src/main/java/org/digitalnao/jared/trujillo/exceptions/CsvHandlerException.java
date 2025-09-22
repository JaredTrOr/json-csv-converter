package org.digitalnao.jared.trujillo.exceptions;

/**
 * Unchecked exception for CSV handling errors.
 */
public class CsvHandlerException extends RuntimeException {
    /**
     * Creates an exception with the given message and cause.
     *
     * @param message description of the error
     * @param cause   underlying cause (may be {@code null})
     */
    public CsvHandlerException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Creates an exception with the given message.
     *
     * @param message description of the error
     */
    public CsvHandlerException(String message) {
        super(message);
    }
}
