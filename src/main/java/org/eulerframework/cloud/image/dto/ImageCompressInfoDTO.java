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
package org.eulerframework.cloud.image.dto;

public class ImageCompressInfoDTO {
    private int width;
    private int height;
    private long fileSize;
    private int compressedWidth;
    private int compressedHeight;
    private long compressedFileSize;

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public long getFileSize() {
        return fileSize;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }

    public int getCompressedWidth() {
        return compressedWidth;
    }

    public void setCompressedWidth(int compressedWidth) {
        this.compressedWidth = compressedWidth;
    }

    public int getCompressedHeight() {
        return compressedHeight;
    }

    public void setCompressedHeight(int compressedHeight) {
        this.compressedHeight = compressedHeight;
    }

    public long getCompressedFileSize() {
        return compressedFileSize;
    }

    public void setCompressedFileSize(long compressedFileSize) {
        this.compressedFileSize = compressedFileSize;
    }
}
