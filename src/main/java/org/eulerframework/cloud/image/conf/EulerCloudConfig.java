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

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "euler")
public class EulerCloudConfig {
    private String runtimePath;
    private int thumbMaxSize = 400;
    private double thumbQuality = 0.7;

    public String getRuntimePath() {
        return runtimePath;
    }

    public void setRuntimePath(String runtimePath) {
        this.runtimePath = runtimePath;
    }

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
}
