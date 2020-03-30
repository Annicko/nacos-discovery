package com.iotknowyou.nacos.provider.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author LiuRongHua
 * @updateUser
 * @description
 * @updateRemark
 * @createDate 2020/3/21 3:03
 * @updateDate 2020/3/21 3:03
 **/

@RestController
public class RestProviderController {

    // 暴露RestFul 接口
    @GetMapping("/service")
    public String service(){
        System.out.println("provider invoke!");
        return "provider invoke!";
    }
}
