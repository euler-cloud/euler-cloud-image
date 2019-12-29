package org.eulerframework.cloud.image.storage;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FileUtils;
import org.eulerframework.cloud.image.dto.ImageSavedInfoDTO;
import org.eulerframework.common.util.DateUtils;
import org.eulerframework.common.util.StringUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.Assert;

import javax.sql.DataSource;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Types;
import java.util.Date;
import java.util.UUID;

public class LocalImageFileStorage implements ImageFileStorage {

    private final JdbcTemplate jdbcTemplate;
    private final String localStoragePath;
    private final String imageDownloadUrl;
    private String selectLocalImageFileSql = "select id, archived_filename, archived_path_suffix, extension, file_byte_size, md5, original_filename, uploaded_at, uploaded_by from local_image_file where id = ?";
    private String insertLocalImageFileSql = "insert into local_image_file (id, archived_filename, archived_path_suffix, extension, file_byte_size, md5, original_filename, uploaded_at, uploaded_by) value (?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private String updateLocalImageFileSql = "update local_image_file set id = ?, archived_filename = ?, archived_path_suffix = ?, extension = ?, file_byte_size = ?, md5 = ?, original_filename = ?, uploaded_at = ?, uploaded_by = ? where id = ?";

    public void setSelectLocalImageFileSql(String selectLocalImageFileSql) {
        this.selectLocalImageFileSql = selectLocalImageFileSql;
    }

    public void setInsertLocalImageFileSql(String insertLocalImageFileSql) {
        this.insertLocalImageFileSql = insertLocalImageFileSql;
    }

    public void setUpdateLocalImageFileSql(String updateLocalImageFileSql) {
        this.updateLocalImageFileSql = updateLocalImageFileSql;
    }

    public LocalImageFileStorage(DataSource dataSource, String localStoragePath, String imageDownloadUrl) {
        Assert.notNull(dataSource, "'dataSource' is required");
        Assert.hasText(localStoragePath, "'localStoragePath' is required");
        Assert.hasText(imageDownloadUrl, "'imageDownloadUrl' is required");

        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.localStoragePath = localStoragePath;
        this.imageDownloadUrl = imageDownloadUrl;
    }

    public LocalImageFile loadLocalImageFile(String localImageFileId, String extensions) throws LocalImageFileNotFoundException {
        Assert.hasText(localImageFileId, "'localImageFileId' is required");
        LocalImageFile localImageFile = this.selectLocalImageFile(localImageFileId);

        if (localImageFile == null) {
            throw new LocalImageFileNotFoundException(localImageFileId, extensions);
        }

        if (StringUtils.hasText(extensions) && !extensions.equalsIgnoreCase(localImageFile.getExtension())) {
            throw new LocalImageFileNotFoundException(localImageFileId, extensions);
        }

        return localImageFile;
    }

    @Override
    public ImageSavedInfoDTO saveImageFile(String userId, File image) {
        String pathSuffix = DateUtils.formatDate(new Date(), "yyyy-MM-dd");
        File destFile = new File(this.localStoragePath + "/" + pathSuffix, UUID.randomUUID().toString());
        try {
            FileUtils.copyFile(image, destFile);
        } catch (IOException e) {
            throw new LocalImageFileSaveException(e.getMessage(), e);
        }

        String md5;
        try (InputStream inputStream = new FileInputStream(destFile)) {
            md5 = DigestUtils.md5Hex(inputStream);
        } catch (IOException e) {
            throw new LocalImageFileSaveException(e.getMessage(), e);
        }
        String originalFilename = image.getName();

        LocalImageFile localImageFile = new LocalImageFile();
        localImageFile.setId(UUID.randomUUID().toString());
        localImageFile.setArchivedFilename(destFile.getName());
        localImageFile.setArchivedPathSuffix(pathSuffix);
        localImageFile.setExtension(org.eulerframework.common.util.io.file.FileUtils.extractFileExtension(originalFilename));
        localImageFile.setFileByteSize(destFile.length());
        localImageFile.setMd5(md5);
        localImageFile.setOriginalFilename(originalFilename);
        localImageFile.setUploadedAt(new Date());
        localImageFile.setUploadedBy(userId);
        this.insertLocalImageFile(localImageFile);

        ImageSavedInfoDTO imageSavedInfoDTO = new ImageSavedInfoDTO();
        imageSavedInfoDTO.setSavedId(localImageFile.getId());
        imageSavedInfoDTO.setUrl(this.imageDownloadUrl + "/" + localImageFile.getId());
        return imageSavedInfoDTO;
    }

    private LocalImageFile selectLocalImageFile(String id) {
        return this.jdbcTemplate.query(
                this.selectLocalImageFileSql,
                new Object[]{
                        id,
                },
                new int[]{
                        Types.VARCHAR
                },
                rs -> {
                    if (!rs.next()) {
                        return null;
                    }

                    LocalImageFile localImageFile = new LocalImageFile();
                    localImageFile.setId(rs.getString("id"));
                    localImageFile.setArchivedFilename(rs.getString("archived_filename"));
                    localImageFile.setArchivedPathSuffix(rs.getString("archived_path_suffix"));
                    localImageFile.setExtension(rs.getString("extension"));
                    localImageFile.setFileByteSize(rs.getLong("file_byte_size"));
                    localImageFile.setMd5(rs.getString("md5"));
                    localImageFile.setOriginalFilename(rs.getString("original_filename"));
                    localImageFile.setUploadedAt(rs.getDate("uploaded_at"));
                    localImageFile.setUploadedBy(rs.getString("uploaded_by"));
                    return localImageFile;
                }
        );
    }

    private void updateLocalImageFile(LocalImageFile localImageFile) {
        this.jdbcTemplate.update(
                this.updateLocalImageFileSql,
                new Object[]{
                        localImageFile.getArchivedFilename(),
                        localImageFile.getArchivedPathSuffix(),
                        localImageFile.getExtension(),
                        localImageFile.getFileByteSize(),
                        localImageFile.getMd5(),
                        localImageFile.getOriginalFilename(),
                        localImageFile.getUploadedAt(),
                        localImageFile.getUploadedBy(),
                        localImageFile.getId()
                },
                new int[]{
                        Types.VARCHAR,
                        Types.VARCHAR,
                        Types.VARCHAR,
                        Types.BIGINT,
                        Types.VARCHAR,
                        Types.VARCHAR,
                        Types.TIMESTAMP,
                        Types.VARCHAR,
                        Types.VARCHAR
                }
        );
    }

    private void insertLocalImageFile(LocalImageFile localImageFile) {
        this.jdbcTemplate.update(
                this.insertLocalImageFileSql,
                new Object[]{
                        localImageFile.getId(),
                        localImageFile.getArchivedFilename(),
                        localImageFile.getArchivedPathSuffix(),
                        localImageFile.getExtension(),
                        localImageFile.getFileByteSize(),
                        localImageFile.getMd5(),
                        localImageFile.getOriginalFilename(),
                        localImageFile.getUploadedAt(),
                        localImageFile.getUploadedBy()
                },
                new int[]{
                        Types.VARCHAR,
                        Types.VARCHAR,
                        Types.VARCHAR,
                        Types.VARCHAR,
                        Types.BIGINT,
                        Types.VARCHAR,
                        Types.VARCHAR,
                        Types.TIMESTAMP,
                        Types.VARCHAR
                }
        );
    }
}
