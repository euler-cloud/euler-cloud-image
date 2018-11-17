/*
 * Copyright 2013-2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.eulerframework.cloud.image.conf;

import org.eulerframework.common.util.Assert;
import org.eulerframework.common.util.CommonUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "euler.image")
public class EulerCloudImageConfig implements InitializingBean {
    /**
     * 缩略图长边像素, 较短的边会等比例压缩
     */
    private int thumbMaxSize = 400;
    /**
     * 压缩质量, 1表示无质量损失
     */
    private double thumbQuality = 1;
    /**
     * 对象存储的URL
     */
    private String ossUrl;

    public int getThumbMaxSize() {
        return thumbMaxSize;
    }

    public void setThumbMaxSize(int thumbMaxSize) {
        this.thumbMaxSize = thumbMaxSize;
    }

    public double getThumbQuality() {
        return thumbQuality;
    }

    public void setThumbQuality(double thumbQuality) {
        this.thumbQuality = thumbQuality;
    }

    public String getOssUrl() {
        return ossUrl;
    }

    public void setOssUrl(String ossUrl) {
        this.ossUrl = CommonUtils.convertDirToUnixFormat(ossUrl, false);
    }

    @Override
    public void afterPropertiesSet() {
        Assert.hasText(this.ossUrl, "ossUrl must be set.");
    }
}
