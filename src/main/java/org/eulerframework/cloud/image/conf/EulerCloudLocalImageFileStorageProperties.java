package org.eulerframework.cloud.image.conf;

import org.eulerframework.boot.autoconfigure.property.EulerApplicationProperties;
import org.eulerframework.common.util.Assert;
import org.eulerframework.common.util.StringUtils;
import org.eulerframework.web.config.ConfigUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.nio.file.FileSystemException;

@Configuration
@ConfigurationProperties(prefix = "euler.cloud.local-image-file-storage")
public class EulerCloudLocalImageFileStorageProperties implements InitializingBean {

    @Autowired
    private EulerApplicationProperties eulerApplicationProperties;

    private String localStoragePath;
    private String imageDownloadUrl;

    public String getLocalStoragePath() {
        return localStoragePath;
    }

    public void setLocalStoragePath(String localStoragePath) {
        this.localStoragePath = localStoragePath;
    }

    public String getImageDownloadUrl() {
        return imageDownloadUrl;
    }

    public void setImageDownloadUrl(String imageDownloadUrl) {
        this.imageDownloadUrl = imageDownloadUrl;
    }

    @Override
    public void afterPropertiesSet() throws FileSystemException {
        Assert.hasText(this.imageDownloadUrl, "imageDownloadUrl must be set.");
        this.localStoragePath = ConfigUtils.handleApplicationPath(
                this.localStoragePath,
                () -> this.eulerApplicationProperties.getRuntimePath() + "/file/image",
                "euler.cloud.local-image-file-storage.local-storage-path",
                true);
    }
}
