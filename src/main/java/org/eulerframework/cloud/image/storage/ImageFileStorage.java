package org.eulerframework.cloud.image.storage;

import org.eulerframework.cloud.image.dto.ImageSavedInfoDTO;

import java.io.File;

public interface ImageFileStorage {
    ImageSavedInfoDTO saveImageFile(String userId, File image);
}
