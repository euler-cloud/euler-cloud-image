package org.eulerframework.cloud.image.storage;

public class LocalImageFileSaveException extends RuntimeException {
    public LocalImageFileSaveException(String message, Throwable t) {
        super(message, t);
    }
}
