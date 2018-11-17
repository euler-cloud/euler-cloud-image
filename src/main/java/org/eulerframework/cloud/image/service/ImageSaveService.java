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

import org.eulerframework.cloud.image.conf.EulerCloudImageConfig;
import org.eulerframework.cloud.image.dto.ImageSavedInfoDTO;
import org.eulerframework.cloud.image.remote.RemoteServices;
import org.eulerframework.cloud.image.remote.vo.ArchivedFileVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.File;

@Service
public class ImageSaveService {

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private EulerCloudImageConfig eulerCloudImageConfig;

    public ImageSavedInfoDTO saveFile(File file) {
        MultiValueMap<String, Object> param = new LinkedMultiValueMap<>();
        param.add("file", new FileSystemResource(file));

        ArchivedFileVO archivedFileVO = this.restTemplate.postForObject(
                RemoteServices.EULER_CLOUD_FILE + RemoteServices.EULER_CLOUD_FILE_ARCHIVED_FILE_API,
                param,
                ArchivedFileVO.class);

        ImageSavedInfoDTO imageSavedInfoDTO = new ImageSavedInfoDTO();
        imageSavedInfoDTO.setSavedId(archivedFileVO.getId());
        imageSavedInfoDTO.setUrl(this.eulerCloudImageConfig.getOssUrl() + "/" + archivedFileVO.getId());
        return imageSavedInfoDTO;
    }
}
