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
package org.eulerframework.cloud.image.util;

import org.eulerframework.cloud.image.dto.ImageCompressInfoDTO;

import java.io.File;

public interface ImageCompressCallback {
    void success(ImageCompressInfoDTO imageCompressInfoDTO, File src, File dest);

    void failed(Exception e, File src, File dest);

    void clean(File src, File dest);
}
