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

import org.eulerframework.web.core.base.entity.BaseEntity;
import org.eulerframework.web.core.base.entity.UUIDEntity;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class ImageInfo implements BaseEntity<ImageInfo, String> {
    @Id
    private String id;
    private Integer width;
    private Integer height;
    private Long size;
    private String originalFilename;
    private String originUrl;
    private String thumbUrl;

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public String getOriginalFilename() {
        return originalFilename;
    }

    public void setOriginalFilename(String originalFilename) {
        this.originalFilename = originalFilename;
    }

    public String getOriginUrl() {
        return originUrl;
    }

    public void setOriginUrl(String originUrl) {
        this.originUrl = originUrl;
    }

    public String getThumbUrl() {
        return thumbUrl;
    }

    public void setThumbUrl(String thumbUrl) {
        this.thumbUrl = thumbUrl;
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

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((this.getId() == null) ? 0 : this.getId().hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        UUIDEntity<?> other = (UUIDEntity<?>) obj;
        if (this.getId() == null) {
            if (other.getId() != null)
                return false;
        } else if (!this.getId().equals(other.getId()))
            return false;
        return true;
    }
}
