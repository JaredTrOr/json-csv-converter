package org.digitalnao.jared.trujillo.exceptions;

/**
 * Unchecked exception for JSON read/parse/mapping failures in this library.
 */
public class JsonHandlerException extends RuntimeException {
    /**
     * Creates an exception with the given message.
     * @param message description of the error
     */
    public JsonHandlerException(String message) { super(message); }

    /**
     * Creates an exception with the given message and cause.
     * @param message description of the error
     * @param cause underlying cause (may be {@code null})
     */
    public JsonHandlerException(String message, Throwable cause) { super(message, cause); }
}

