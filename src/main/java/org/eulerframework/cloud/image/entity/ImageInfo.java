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
package org.eulerframework.cloud.image.entity;

import org.eulerframework.web.core.base.entity.NonIDEntity;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class ImageInfo extends NonIDEntity<ImageInfo, String> {
    @Id
    private String id;
    private String filename;
    private Integer originWidth;
    private Integer originHeight;
    private Long originFileSize;
    private String originSavedId;
    private String originUrl;
    private Integer thumbWidth;
    private Integer thumbHeight;
    private Long thumbFileSize;
    private String thumbSavedId;
    private String thumbUrl;
    private Date uploadedAt;
    private String uploadedBy;

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public Integer getOriginWidth() {
        return originWidth;
    }

    public void setOriginWidth(Integer originWidth) {
        this.originWidth = originWidth;
    }

    public Integer getOriginHeight() {
        return originHeight;
    }

    public void setOriginHeight(Integer originHeight) {
        this.originHeight = originHeight;
    }

    public Long getOriginFileSize() {
        return originFileSize;
    }

    public void setOriginFileSize(Long originFileSize) {
        this.originFileSize = originFileSize;
    }

    public String getOriginSavedId() {
        return originSavedId;
    }

    public void setOriginSavedId(String originSavedId) {
        this.originSavedId = originSavedId;
    }

    public String getOriginUrl() {
        return originUrl;
    }

    public void setOriginUrl(String originUrl) {
        this.originUrl = originUrl;
    }

    public Integer getThumbWidth() {
        return thumbWidth;
    }

    public void setThumbWidth(Integer thumbWidth) {
        this.thumbWidth = thumbWidth;
    }

    public Integer getThumbHeight() {
        return thumbHeight;
    }

    public void setThumbHeight(Integer thumbHeight) {
        this.thumbHeight = thumbHeight;
    }

    public Long getThumbFileSize() {
        return thumbFileSize;
    }

    public void setThumbFileSize(Long thumbFileSize) {
        this.thumbFileSize = thumbFileSize;
    }

    public String getThumbSavedId() {
        return thumbSavedId;
    }

    public void setThumbSavedId(String thumbSavedId) {
        this.thumbSavedId = thumbSavedId;
    }

    public String getThumbUrl() {
        return thumbUrl;
    }

    public void setThumbUrl(String thumbUrl) {
        this.thumbUrl = thumbUrl;
    }

    public Date getUploadedAt() {
        return uploadedAt;
    }

    public void setUploadedAt(Date uploadedAt) {
        this.uploadedAt = uploadedAt;
    }

    public String getUploadedBy() {
        return uploadedBy;
    }

    public void setUploadedBy(String uploadedBy) {
        this.uploadedBy = uploadedBy;
    }

    @Override
    public int compareTo(ImageInfo obj) {
        if (this == obj)
            return 0;

        if(obj == null)
            return 1;

        if (getClass() != obj.getClass())
            throw new RuntimeException("两比较对象类型不一致");

        if(this.getId() == null)
            return 1;

        if(obj.getId() == null)
            return -1;

        return this.getId().compareTo(obj.getId());
    }
}
