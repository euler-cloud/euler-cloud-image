package org.eulerframework.cloud.image.storage;

public class LocalImageFileNotFoundException extends Exception {
    public LocalImageFileNotFoundException(String archivedFileId, String extensions) {
        super(String.format("Local image file %s is not exists.", archivedFileId + (extensions == null ? "" : extensions)));
    }
}
