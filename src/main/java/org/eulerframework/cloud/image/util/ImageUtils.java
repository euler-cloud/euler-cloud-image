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

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGEncodeParam;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
import net.coobird.thumbnailator.Thumbnails;
import org.eulerframework.cloud.image.dto.ImageCompressInfoDTO;
import org.eulerframework.common.util.Assert;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class ImageUtils {

    public static ImageCompressInfoDTO compressImage(File src, File dest, int maxSize, double quality) throws IOException {
        Assert.notNull(src, "src file argument must not be null");
        Assert.notNull(dest, "dest file argument must not be null");
        Assert.isTrue(src.exists(), "src file must exists");
        Assert.isTrue(src.isFile(), "src file must is a file");
        Assert.isFalse(dest.exists(), "dest file must not exists");

        BufferedImage bufferedImage = ImageIO.read(src);
        Thumbnails.Builder<BufferedImage> bufferedImageBuilder = Thumbnails.of(bufferedImage).size(maxSize, maxSize).outputQuality(quality);
        ImageCompressInfoDTO imageCompressInfoDTO = new ImageCompressInfoDTO();
        imageCompressInfoDTO.setHeight(bufferedImage.getHeight());
        imageCompressInfoDTO.setWidth(bufferedImage.getWidth());
        imageCompressInfoDTO.setFileSize(src.length());

        BufferedImage bufferedCompressedImage = bufferedImageBuilder.asBufferedImage();
        imageCompressInfoDTO.setCompressedHeight(bufferedCompressedImage.getHeight());
        imageCompressInfoDTO.setCompressedWidth(bufferedCompressedImage.getWidth());

        bufferedImageBuilder.toFile(dest);
        imageCompressInfoDTO.setCompressedFileSize(dest.length());

        return imageCompressInfoDTO;
    }
}
