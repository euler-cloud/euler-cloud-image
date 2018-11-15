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
package org.eulerframework.cloud.image.util;

import org.eulerframework.cloud.image.vo.ImageInfoDTO;
import org.eulerframework.common.base.log.LogSupport;

import java.io.File;
import java.io.IOException;

public class ImageCompressRunnable extends LogSupport implements Runnable {
    private File src;
    private File dest;
    private int maxSize;
    private double quality;
    private ImageCompressCallback imageCompressCallback;

    public ImageCompressRunnable(File src, File dest, int maxSize, double quality, ImageCompressCallback imageCompressCallback) {
        this.src = src;
        this.dest = dest;
        this.maxSize = maxSize;
        this.quality = quality;
        this.imageCompressCallback = imageCompressCallback;
    }

    @Override
    public void run() {
        try {
            ImageInfoDTO imageInfoDTO = ImageUtils.compressImage(this.src, this.dest, this.maxSize, this.quality);
            this.imageCompressCallback.success(imageInfoDTO, this.src, this.dest);
        } catch (IOException e) {
            this.logger.error(e.getMessage(), e);
            this.imageCompressCallback.failed(e, this.src, this.dest);
        } finally {
            this.imageCompressCallback.clean(this.src, this.dest);
        }
    }
}
