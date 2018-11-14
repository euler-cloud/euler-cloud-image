/*
 * Copyright 2013-${YEAR} the original author or authors.
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
package org.eulerframework.cloud.image;

import net.coobird.thumbnailator.Thumbnails;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Image {
    private final static double MAX_SIZE = 200;
    public static void main(String[] args) throws IOException {
//        BufferedImage bufferedImage = Thumbnails.of("/Users/cFrost/Desktop/aaa.jpg").asBufferedImage();
//
//        int width = bufferedImage.getWidth();
//        int height = bufferedImage.getHeight();
//        double scale;
//
//        if(width < height) {
//            scale = MAX_SIZE / height;
//        } else {
//            scale = MAX_SIZE / width;
//        }

        File src = new File("/Users/cFrost/Desktop/aaa.jpg");
        File dest = new File("/Users/cFrost/Desktop/a1.jpg");

//        Thumbnails.of(src)
//                .size(200, 200)
//                .outputQuality(1)
//                .toFile(dest);

        System.out.println(Thumbnails.of(src).asBufferedImage().getHeight());
    }
}
