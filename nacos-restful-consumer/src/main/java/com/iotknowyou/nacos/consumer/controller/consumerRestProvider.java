package com.iotknowyou.nacos.consumer.controller;

import com.iotknowyou.microservice.serviceOne.api.ServiceOneApi;
import com.iotknowyou.microservice.serviceTwo.ServiceTwoApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

/**
 * @author LiuRongHua
 * @updateUser
 * @description
 * @updateRemark
 * @createDate 2020/3/21 3:17
 * @updateDate 2020/3/21 3:17
 **/

@RestController
public class consumerRestProvider {

    // 读取配置文件中的 提供服务的地址
    @Value("${provider.address}")
    private String provider;


    @GetMapping("/consumer")
    public String consumer(){
        // 通过 RestTemplate 可以调用远程Rest连接
        RestTemplate restTemplate = new RestTemplate();

        String  result = restTemplate.getForObject("http://" + provider + "/service" , String.class);

        System.out.println(" 远程访问成功 ！");

        return "consumer invode | " + result;
    }

    //指定服务名
    String serviceId = "nacos-restful-provider";

    //通过负载均衡发现地址，流程是从服务发现中心拿nacos-restful-provider服务的列表，通过负载均衡算法获取一个地址
    @Autowired
    LoadBalancerClient loadBalancerClient;

    @GetMapping(value = "/consumerByNacos")
    public String service1(){
        //远程调用
        RestTemplate restTemplate = new RestTemplate();

        //发现一个地址  --> 作用： 通过负载均衡算法获取一个地址
        ServiceInstance serviceInstance = loadBalancerClient.choose(serviceId);
        //获取一个http://开头的地址，包括ip和端口
        URI uri = serviceInstance.getUri();
        System.out.println("获取的IP地址："+uri);
        String result = restTemplate.getForObject(uri + "/service", String.class);
        return "consumer invode | "+result;
    }



    //通过Dubbo引入相应的服务API

    @org.apache.dubbo.config.annotation.Reference
    ServiceTwoApi serviceTwoApi;

    @org.apache.dubbo.config.annotation.Reference
    ServiceOneApi serviceOneApi;



    @GetMapping(value = "/serviceOne")
    public String serviceOne(){
        //远程调用serviceOne微服务,该微服务中的serviceOneApi获取调用 serviceTwo
        String providerResult = serviceOneApi.dubboServiceOne();
        return "consumer dubbo invoke |"+providerResult;
    }
    @GetMapping(value = "/serviceThree")
    public String serviceTwo(){
        //远程调用serviceTwo微服务
        String providerResult = serviceTwoApi.dubboServiceTwoApi();
        return "consumer dubbo invoke |"+providerResult;
    }


    /*
    *   Nacos配置中心中的配置获取
    *       1、通过spring的 @Value 注解直接获取
    *
    *
    */

    @Value("${common.name}")
    private String common_name;


    @Autowired
    ConfigurableApplicationContext applicationContext;

    @GetMapping("/getNacosConfigInfo")
    public Map getNacosConfigInfo(){
        Map result= new HashMap(3);
        //  通过 ConfigurableApplicationContext 我们可以动态获取 Nacos配置中心分布的配置信息。
        String name =  applicationContext.getEnvironment().getProperty("common.name");
        String address =  applicationContext.getEnvironment().getProperty("common.addr");
        result.put("common_name Get By @Value",common_name);
        result.put("common_name Get By ConfigurableApplicationContext",name);
        result.put("common_addr Get By ConfigurableApplicationContext",address);
        return result;
    }
}
