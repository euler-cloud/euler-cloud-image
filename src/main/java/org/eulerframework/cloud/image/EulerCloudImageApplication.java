package org.eulerframework.cloud.image;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class EulerCloudImageApplication {

    public static void main(String[] args) {
        SpringApplication.run(EulerCloudImageApplication.class, args);
    }
}
