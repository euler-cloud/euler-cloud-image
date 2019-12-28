package org.eulerframework.cloud.image.storage;

import java.io.Serializable;
import java.util.Date;

public class LocalImageFile implements Serializable {

    private String id;
    private String originalFilename;
    private String archivedPathSuffix;
    private String archivedFilename;
    private String extension;
    private String md5;
    private Long fileByteSize;
    private Date uploadedAt;
    private String uploadedBy;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOriginalFilename() {
        return originalFilename;
    }

    public void setOriginalFilename(String originalFilename) {
        this.originalFilename = originalFilename;
    }

    public String getArchivedPathSuffix() {
        return archivedPathSuffix;
    }

    public void setArchivedPathSuffix(String archivedPathSuffix) {
        this.archivedPathSuffix = archivedPathSuffix;
    }

    public String getArchivedFilename() {
        return archivedFilename;
    }

    public void setArchivedFilename(String archivedFilename) {
        this.archivedFilename = archivedFilename;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public Long getFileByteSize() {
        return fileByteSize;
    }

    public void setFileByteSize(Long fileByteSize) {
        this.fileByteSize = fileByteSize;
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
}
