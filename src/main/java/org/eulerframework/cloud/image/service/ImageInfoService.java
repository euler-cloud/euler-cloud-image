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
package org.eulerframework.cloud.image.service;

import org.eulerframework.cloud.image.entity.ImageInfo;
import org.eulerframework.cloud.image.repository.ImageInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class ImageInfoService {

    @Autowired
    private ImageInfoRepository imageInfoRepository;

    public void saveImageInfo(String userId, ImageInfo imageInfo) {
        imageInfo.setUploadedAt(new Date());
        imageInfo.setUploadedBy(userId);
        this.imageInfoRepository.save(imageInfo);
    }

    public ImageInfo findImageInfo(String imageInfoId) {
        return this.imageInfoRepository.findById(imageInfoId).orElse(null);
    }
}
