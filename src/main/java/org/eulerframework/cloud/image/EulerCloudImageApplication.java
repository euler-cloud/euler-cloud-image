package org.eulerframework.cloud.image;

import org.eulerframework.cloud.EnableEulerCloud;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableEulerCloud
public class EulerCloudImageApplication {

    public static void main(String[] args) {
        SpringApplication.run(EulerCloudImageApplication.class, args);
    }
}
