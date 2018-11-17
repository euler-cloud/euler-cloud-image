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
package org.eulerframework.cloud.image.api;

import org.eulerframework.cloud.EulerCloudUserContext;
import org.eulerframework.cloud.config.EulerCloudConfig;
import org.eulerframework.cloud.image.conf.EulerCloudImageConfig;
import org.eulerframework.cloud.image.dto.ImageSavedInfoDTO;
import org.eulerframework.cloud.image.entity.ImageInfo;
import org.eulerframework.cloud.image.service.ImageInfoService;
import org.eulerframework.cloud.image.service.ImageSaveService;
import org.eulerframework.cloud.image.util.ImageCompressCallback;
import org.eulerframework.cloud.image.util.ImageCompressRunnable;
import org.eulerframework.cloud.image.dto.ImageCompressInfoDTO;
import org.eulerframework.common.util.io.file.SimpleFileIOUtils;
import org.eulerframework.web.core.base.controller.ApiSupportWebController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RestController
@CrossOrigin
@RequestMapping("image")
public class ImageUploadApi extends ApiSupportWebController {

    @Autowired
    private EulerCloudConfig eulerCloudConfig;

    @Autowired
    private EulerCloudImageConfig eulerCloudImageConfig;

    @Autowired
    private ImageSaveService imageSaveService;

    @Autowired
    private ImageInfoService imageInfoService;

    private final static ExecutorService EXECUTOR_SERVICE = Executors.newFixedThreadPool(2);

    @GetMapping("{imageInfoId}")
    public ImageInfo findImageInfo(@PathVariable String imageInfoId) {
        return this.imageInfoService.findImageInfo(imageInfoId);
    }

    /**
     * 上传图片, 并生成缩略图, 图片上传后会立即返回图片ID, 缩略图生成逻辑将异步进行
     *
     * @param image 待上传的图片
     * @return 图片ID, 在一段时间后才会生效
     * @throws IOException IO异常
     */
    @PostMapping
    public String uploadImage(@RequestParam MultipartFile image) throws IOException {
        String currentUserId = EulerCloudUserContext.getCurrentUserId();
        String workId = UUID.randomUUID().toString();
        String tmpPath = this.eulerCloudConfig.getTmpPath();

        String workspacePath = tmpPath + "/" + workId;
        File workspace = new File(workspacePath);

        String originalFilename = image.getOriginalFilename();
        //使用原始文件名, 这样就可以在上传文件存储应用时会连同原始文件名一同保存下来
        File src = new File(workspacePath + "/origin", originalFilename);
        //缩略图文件名为"thumb_原始文件名"
        File thumb = new File(workspacePath + "/thumb", "thumb_" + originalFilename);

        workspace.mkdirs();
        src.getParentFile().mkdir();
        thumb.getParentFile().mkdir();

        image.transferTo(src);

        ImageCompressRunnable imageCompressRunnable = new ImageCompressRunnable(
                src,
                thumb,
                ImageUploadApi.this.eulerCloudImageConfig.getThumbMaxSize(),
                ImageUploadApi.this.eulerCloudImageConfig.getThumbQuality(),
                new ImageCompressCallback() {
                    @Override
                    public void success(ImageCompressInfoDTO imageCompressInfoDTO, File src, File dest) {
                        ImageSavedInfoDTO originImageSavedInfoDTO = ImageUploadApi.this.imageSaveService.saveFile(src);
                        ImageSavedInfoDTO thumbImageSavedInfoDTO = ImageUploadApi.this.imageSaveService.saveFile(dest);

                        ImageInfo imageInfo = new ImageInfo();

                        imageInfo.setId(workId);
                        imageInfo.setFilename(originalFilename);

                        imageInfo.setOriginHeight(imageCompressInfoDTO.getHeight());
                        imageInfo.setOriginWidth(imageCompressInfoDTO.getWidth());
                        imageInfo.setOriginFileSize(imageCompressInfoDTO.getFileSize());

                        imageInfo.setThumbHeight(imageCompressInfoDTO.getCompressedHeight());
                        imageInfo.setThumbWidth(imageCompressInfoDTO.getCompressedWidth());
                        imageInfo.setThumbFileSize(imageCompressInfoDTO.getCompressedFileSize());

                        imageInfo.setOriginSavedId(originImageSavedInfoDTO.getSavedId());
                        imageInfo.setOriginUrl(originImageSavedInfoDTO.getUrl());

                        imageInfo.setThumbSavedId(thumbImageSavedInfoDTO.getSavedId());
                        imageInfo.setThumbUrl(thumbImageSavedInfoDTO.getUrl());

                        ImageUploadApi.this.imageInfoService.saveImageInfo(currentUserId, imageInfo);
                    }

                    @Override
                    public void failed(Exception e, File src, File dest) {}

                    @Override
                    public void clean(File src, File dest) {
                        //直接删除workspace
                        if (workspace.exists()) {
                            SimpleFileIOUtils.deleteFile(workspace);
                        }
                    }
                });

        EXECUTOR_SERVICE.submit(imageCompressRunnable);

        return workId;
    }
}
