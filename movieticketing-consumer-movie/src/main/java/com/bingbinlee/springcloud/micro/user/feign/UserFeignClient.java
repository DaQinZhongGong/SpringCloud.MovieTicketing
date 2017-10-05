package com.bingbinlee.springcloud.micro.user.feign;


import com.bingbinlee.springcloud.micro.user.entity.User;
import feign.hystrix.FallbackFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 服务消费者-Feign接口
 * 使用@FeignClient的fallback属性指定回退类
 * 很多场景需要了解退回原因，此时可使用注解@FeignClient的fallbackFactory属性
 * @author libingbin2015@aliyun.com
 */
//想要禁用Hystrix,引入这个配置即可@FeignClient(name = "movieticketing-provider-user", configuration = FeignDisableHystrixConfiguration.class)
//@FeignClient(name = "movieticketing-provider-user", fallback = FeignClientFallback.class)
@FeignClient(name = "movieticketing-provider-user", fallbackFactory = FeignClientFallbackFactory.class)
public interface UserFeignClient {
  @RequestMapping(value = "/{id}", method = RequestMethod.GET)
  User findById(@PathVariable("id") Long id);

  // 该请求不会成功
  @RequestMapping(value = "/get", method = RequestMethod.GET)
  User get0(User user);

  @RequestMapping(value = "/get", method = RequestMethod.GET)
  User get1(@RequestParam("id") Long id, @RequestParam("username") String username);

  @RequestMapping(value = "/get", method = RequestMethod.GET)
  User get2(@RequestParam Map<String, Object> map);

  @RequestMapping(value = "/post", method = RequestMethod.POST)
  User post(@RequestBody User user);
}

/**
 * 回退类FeignClientFallback需实现Feign Client接口
 * FeignClientFallback也可以是public class，没有区别
 * @author libingbin2015@aliyun.com
 *//*
@Component
class FeignClientFallback implements UserFeignClient {
  @Override
  public User findById(Long id) {
    User user = new User();
    user.setId(-1L);
    user.setUsername("默认用户");
    return user;
  }

  @Override
  public User get0(User user) {
    return null;
  }

  @Override
  public User get1(Long id, String username) {
    return null;
  }

  @Override
  public User get2(Map<String, Object> map) {
    return null;
  }

  @Override
  public User post(User user) {
    return null;
  }
}*/

/**
 * UserFeignClient的fallbackFactory类，该类需实现FallbackFactory接口，并覆写create方法
 * The fallback factory must produce instances of fallback classes that
 * implement the interface annotated by {@link FeignClient}.
 * @author libingbin2015@aliyun.com
 */
@Component
class FeignClientFallbackFactory implements FallbackFactory<UserFeignClient> {
  private static final Logger LOGGER = LoggerFactory.getLogger(FeignClientFallbackFactory.class);

  @Override
  public UserFeignClient create(Throwable cause) {
    return new UserFeignClient() {
      @Override
      public User findById(Long id) {
        // 日志最好放在各个fallback方法中，而不要直接放在create方法中。
        // 否则在引用启动时，就会打印该日志。
        // 详见https://github.com/spring-cloud/spring-cloud-netflix/issues/1471
        FeignClientFallbackFactory.LOGGER.info("fallback; reason was:", cause);
        User user = new User();
        user.setId(-1L);
        user.setUsername("默认用户");
        return user;
      }

      @Override
      public User get0(User user) {
        return null;
      }

      @Override
      public User get1(Long id, String username) {
        return null;
      }

      @Override
      public User get2(Map<String, Object> map) {
        return null;
      }

      @Override
      public User post(User user) {
        return null;
      }
    };
  }
}