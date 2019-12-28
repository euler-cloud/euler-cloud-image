package org.eulerframework.cloud.image;

import org.eulerframework.cloud.image.conf.EulerCloudLocalImageFileStorageProperties;
import org.eulerframework.cloud.image.storage.ImageFileStorage;
import org.eulerframework.cloud.image.storage.LocalImageFileStorage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import javax.sql.DataSource;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class EulerCloudImageApplication {

    public static void main(String[] args) {
        SpringApplication.run(EulerCloudImageApplication.class, args);
    }

//    @Bean
//    @LoadBalanced
//    public RestTemplate restTemplate() {
//        return new RestTemplate();
//    }

    @Bean
    public ImageFileStorage imageFileStorage(
            DataSource dataSource,
            EulerCloudLocalImageFileStorageProperties eulerCloudLocalImageFileStorageProperties) {
        return new LocalImageFileStorage(
                dataSource,
                eulerCloudLocalImageFileStorageProperties.getLocalStoragePath(),
                eulerCloudLocalImageFileStorageProperties.getImageDownloadUrl()
        );
    }
}
