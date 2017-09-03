package com.bingbinlee.springcloud.micro.config;

import com.bingbinlee.springcloud.config.RibbonConfiguration;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.context.annotation.Configuration;



/**
 * 使用Java代码的方式配置Ribbon，如果使用属性自定义的方式配置Ribbon则去掉此类
 *
 * 使用RibbonClient，为特定name的Ribbon Client自定义配置.
 * 使用@RibbonClient的configuration属性，指定Ribbon的配置类.
 *
 * @author libingbin2015@aliyun.com
 */
@Configuration
@RibbonClient(name = "movieticketing-provider-user", configuration = RibbonConfiguration.class)
public class TestConfiguration {
}