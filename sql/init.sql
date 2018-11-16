CREATE DATABASE euler_cloud_image
  DEFAULT CHARACTER SET utf8mb4
  DEFAULT COLLATE utf8mb4_bin;

USE euler_cloud_image;

CREATE TABLE image_info
(
  id               VARCHAR(36)   NOT NULL
  COMMENT '图片ID',
  filename         VARCHAR(2000) NOT NULL
  COMMENT '图片原始文件名',
  origin_width     INT           NOT NULL
  COMMENT '原图宽度',
  origin_height    INT           NOT NULL
  COMMENT '原图高度',
  origin_file_size BIGINT        NOT NULL
  COMMENT '原图文件大小',
  origin_saved_id  VARCHAR(100)  NOT NULL
  COMMENT '原图保存ID',
  origin_url       VARCHAR(2000) NOT NULL
  COMMENT '原图下载地址',
  thumb_width      INT           NOT NULL
  COMMENT '缩略图宽度',
  thumb_height     INT           NOT NULL
  COMMENT '缩略图高度',
  thumb_file_size BIGINT        NOT NULL
  COMMENT '缩略图文件大小',
  thumb_saved_id   VARCHAR(100)  NOT NULL
  COMMENT '缩略图保存ID',
  thumb_url        VARCHAR(2000) NOT NULL
  COMMENT '缩略图下载地址',
  uploaded_at      DATETIME(3)   NOT NULL
  COMMENT '上传时刻',
  uploaded_by      VARCHAR(255)  NOT NULL
  COMMENT '上传用户ID',
  PRIMARY KEY (id)
)
  ENGINE = InnoDB
  DEFAULT CHARACTER SET utf8mb4
  DEFAULT COLLATE utf8mb4_bin
  COMMENT = '图片信息表';