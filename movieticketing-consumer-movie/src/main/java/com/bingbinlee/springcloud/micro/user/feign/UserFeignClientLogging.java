package com.bingbinlee.springcloud.micro.user.feign;


import com.bingbinlee.springcloud.config.FeignLogConfiguration;
import com.bingbinlee.springcloud.micro.user.entity.User;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 服务消费者-使用属性自定义的方式配置Feign日志，指定配置类
 * @author libingbin2015@aliyun.com
 */
@FeignClient(name = "movieticketing-provider-user", configuration = FeignLogConfiguration.class)
public interface UserFeignClientLogging {
  @RequestMapping(value = "/{id}", method = RequestMethod.GET)
  User findById(@PathVariable("id") Long id);
}
