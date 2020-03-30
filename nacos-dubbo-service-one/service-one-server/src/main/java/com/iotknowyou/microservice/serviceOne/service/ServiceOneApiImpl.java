package com.iotknowyou.microservice.serviceOne.service;

import com.iotknowyou.microservice.serviceOne.api.ServiceOneApi;
import com.iotknowyou.microservice.serviceTwo.ServiceTwoApi;

/**
 * @author LiuRongHua
 * @updateUser
 * @description
 * @updateRemark
 * @createDate 2020/3/27 18:31
 * @updateDate 2020/3/27 18:31
 **/

@org.apache.dubbo.config.annotation.Service
public class ServiceOneApiImpl implements ServiceOneApi{

    @org.apache.dubbo.config.annotation.Reference
    ServiceTwoApi serviceTwoApi;


    public String dubboServiceOne() {
        //远程调用service2
        String s = serviceTwoApi.dubboServiceTwoApi();
        return "dubbo-Service-One|"+s;
    }

}
