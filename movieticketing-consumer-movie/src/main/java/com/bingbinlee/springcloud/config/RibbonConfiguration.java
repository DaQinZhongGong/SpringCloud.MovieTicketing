package com.bingbinlee.springcloud.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RandomRule;

/**
 * 使用Java代码的方式配置Ribbon，如果使用属性自定义的方式配置Ribbon则去掉此类
 * 该类为Ribbon的配置类
 * 注意：该类不应该在主应用程序上下文的@ComponentScan 中。
 * @author libingbin2015@aliyun.com
 */
@Configuration
public class RibbonConfiguration {
  @Bean
  public IRule ribbonRule() {
    // 负载均衡规则，改为随机
    return new RandomRule();
  }
}