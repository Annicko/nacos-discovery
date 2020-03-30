package com.iotknowyou.microservice.serviceTwo.service;

import com.iotknowyou.microservice.serviceTwo.ServiceTwoApi;

/**
 * @author LiuRongHua
 * @updateUser
 * @description
 * @updateRemark
 * @createDate 2020/3/25 1:05
 * @updateDate 2020/3/25 1:05
 **/

@org.apache.dubbo.config.annotation.Service
public class ServiceTwoApiImpl implements ServiceTwoApi {

    public String dubboServiceTwoApi() {
        return "dubbo-Service-TwoApi Impl";
    }
}
