package com.bingbinlee.springcloud.micro;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;

/**
 * 使用可视化Hystrix Dashboard 可视化监控数据
 * Hystrix Dashboard 没有注册到Eureka中，也可注册
 * @author libingbin2015@aliyun.com
 */
@SpringBootApplication
@EnableHystrixDashboard
public class HystrixDashboardApplication {
  public static void main(String[] args) {
    SpringApplication.run(HystrixDashboardApplication.class, args);
  }
}
