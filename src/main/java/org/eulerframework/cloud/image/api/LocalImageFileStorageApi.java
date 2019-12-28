package org.eulerframework.cloud.image.api;

import org.eulerframework.cloud.image.conf.EulerCloudLocalImageFileStorageProperties;
import org.eulerframework.cloud.image.storage.LocalImageFile;
import org.eulerframework.cloud.image.storage.LocalImageFileNotFoundException;
import org.eulerframework.cloud.image.storage.LocalImageFileStorage;
import org.eulerframework.common.util.io.file.FileUtils;
import org.eulerframework.web.core.base.controller.ApiSupportWebController;
import org.eulerframework.web.core.base.response.ErrorResponse;
import org.eulerframework.web.core.exception.web.SystemWebError;
import org.eulerframework.web.core.exception.web.WebException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

@RestController
@CrossOrigin
@RequestMapping("image/local")
public class LocalImageFileStorageApi extends ApiSupportWebController {

    @Autowired(required = false)
    private LocalImageFileStorage localImageFileStorage;
    @Autowired
    private EulerCloudLocalImageFileStorageProperties eulerCloudLocalImageFileStorageProperties;

    @GetMapping("{localImageFileId}")
    public void downloadLocalImageFile(@PathVariable String localImageFileId) throws LocalImageFileNotFoundException, IOException {
        String extensions = FileUtils.extractFileExtension(localImageFileId);
        localImageFileId = FileUtils.extractFileNameWithoutExtension(localImageFileId);
        LocalImageFile localImageFile = this.localImageFileStorage.loadLocalImageFile(localImageFileId, extensions);

        String localImageFilePath = this.eulerCloudLocalImageFileStorageProperties.getLocalStoragePath();

        if(localImageFile.getArchivedPathSuffix() != null)
            localImageFilePath = localImageFilePath + "/" + localImageFile.getArchivedPathSuffix();

        File file = new File(localImageFilePath, localImageFile.getArchivedFilename());
        try {
            this.writeFile(localImageFile.getOriginalFilename(), file);
        } catch (FileNotFoundException e) {
            throw new LocalImageFileNotFoundException(localImageFileId, extensions);
        }
    }

    /**
     * 用于在程序发生{@link LocalImageFileNotFoundException}异常时统一返回错误信息
     *
     * @return 包含错误信息的Ajax响应体
     */
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(LocalImageFileNotFoundException.class)
    public Object localImageFileNotFoundException(LocalImageFileNotFoundException e) {
        return new ErrorResponse(new WebException(e.getMessage(), SystemWebError.RESOURCE_NOT_FOUND, e));
    }
}
