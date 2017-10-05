package com.bingbinlee.springcloud.micro.user.controller;


import com.bingbinlee.springcloud.micro.user.entity.User;
import com.bingbinlee.springcloud.micro.user.feign.UserFeignClient;
import com.google.common.collect.Maps;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

/**
 * 服务消费者
 * @author libingbin2015@aliyun.com
 */
@RestController
public class MovieController {
    private static final Logger LOGGER = LoggerFactory.getLogger(MovieController.class);
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private LoadBalancerClient loadBalancerClient;
    @Autowired
    private UserFeignClient userFeignClient;
    @Autowired
    private DiscoveryClient discoveryClient;

    /* @GetMapping("/user/{id}")
    public User findById(@PathVariable Long id) {
        return this.restTemplate.getForObject("http://localhost:8000/" + id, User.class);
    }*/

    /*@GetMapping("/user/{id}")
    public User findById(@PathVariable Long id) {
        return this.userFeignClient.findById(id);
    }*/

    /**
     *  将请求地址改为http://movieticketing-provider-user/，movieticketing-provider-user是用户微服务的虚拟主机名（virtual host name），
     *  当Ribbon和Eureka配合使用，会自动将虚拟主机名映射成微服务的网络地址。
     *  @HystrixCommand 的 fallbackMethod属性，指定回退方法是findByIdFallback(Long id)
     */
    @HystrixCommand(fallbackMethod = "findByIdFallback")
    @GetMapping("/user/{id}")
    public User findById(@PathVariable Long id) {
        return this.restTemplate.getForObject("http://movieticketing-provider-user/" + id, User.class);
    }

    /**
     * findById() 的容错默认回退方法
     * @param id
     * @return
     */
    public User findByIdFallback(Long id) {
        User user = new User();
        user.setId(-1L);
        user.setName("默认用户");
        return user;
    }

    /**
     * 在新增的logUserInstance()方法中可使用LoadBalancerClient的API更加直观地
     * 获取当前选择的用户微服务节点
     */
    @GetMapping("/log-user-instance")
    public void logUserInstance() {
        ServiceInstance serviceInstance = this.loadBalancerClient.choose("movieticketing-provider-user");
        // 打印当前选择的是哪个节点
        MovieController.LOGGER.info("{}:{}:{}", serviceInstance.getServiceId(), serviceInstance.getHost(), serviceInstance.getPort());
    }

    /**
     * 查询movieticketing-provider-user服务的信息并返回
     * @return movieticketing-provider-user服务的信息
     */
    @GetMapping("/user-instance")
    public List<ServiceInstance> showInfo() {
        return this.discoveryClient.getInstances("movieticketing-provider-user");
    }

    /**
     * 测试URL：http://localhost:8010/user/get0?id=1&username=张三
     * 该请求不会成功。
     * @param user user
     * @return 用户信息
     */
    @GetMapping("/user/get0")
    public User get0(User user) {
        return this.userFeignClient.get0(user);
    }

    /**
     * 测试URL：http://localhost:8010/user/get1?id=1&username=张三
     * @param user user
     * @return 用户信息
     */
    @GetMapping("/user/get1")
    public User get1(User user) {
        return this.userFeignClient.get1(user.getId(), user.getUsername());
    }

    /**
     * 测试URL：http://localhost:8010/user/get2?id=1&username=张三
     * @param user user
     * @return 用户信息
     */
    @GetMapping("/user/get2")
    public User get2(User user) {
        Map<String, Object> map = Maps.newHashMap();
        map.put("id", user.getId());
        map.put("username", user.getUsername());
        return this.userFeignClient.get2(map);
    }

    /**
     * 测试URL:http://localhost:8010/user/post?id=1&username=张三
     * @param user user
     * @return 用户信息
     */
    @GetMapping("/user/post")
    public User post(User user) {
        return this.userFeignClient.post(user);
    }
}
