package com.bingbinlee.springcloud.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import feign.Logger;

/**
 * 编写Feign配置类
 * @author libingbin2015@aliyun.com
 */
@Configuration
public class FeignLogConfiguration {
  @Bean
  Logger.Level feignLoggerLevel() {
    return Logger.Level.FULL;
  }
}
