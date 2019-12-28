package org.eulerframework.cloud.image;

import org.eulerframework.cloud.image.conf.EulerCloudLocalImageFileStorageProperties;
import org.eulerframework.cloud.image.storage.ImageFileStorage;
import org.eulerframework.cloud.image.storage.LocalImageFileStorage;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.ShallowEtagHeaderFilter;

import javax.sql.DataSource;

@Configuration
@ConditionalOnMissingBean(ImageFileStorage.class)
public class LocalImageFileStorageConfig {
    @Bean
    public LocalImageFileStorage imageFileStorage(
            DataSource dataSource,
            EulerCloudLocalImageFileStorageProperties eulerCloudLocalImageFileStorageProperties) {
        return new LocalImageFileStorage(
                dataSource,
                eulerCloudLocalImageFileStorageProperties.getLocalStoragePath(),
                eulerCloudLocalImageFileStorageProperties.getImageDownloadUrl()
        );
    }

    @Bean
    public FilterRegistrationBean<ShallowEtagHeaderFilter> shallowEtagHeaderFilterFilterRegistrationBean() {
        FilterRegistrationBean<ShallowEtagHeaderFilter> filterRegistrationBean = new FilterRegistrationBean<>();
        filterRegistrationBean.setName("eTagFilter");
        filterRegistrationBean.setFilter(new ShallowEtagHeaderFilter());
        filterRegistrationBean.addUrlPatterns("/archived/*");
        return filterRegistrationBean;
    }
}
