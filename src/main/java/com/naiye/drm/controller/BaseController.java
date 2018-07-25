package com.naiye.drm.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 *
 * @Author: csf
 * @Descriptions: 基础的Controller
 * @Date: create in  16:30 2018/6/25
 *
 */


@RestController
@RequestMapping
public class BaseController {

    @RequestMapping("/")
    public String baseIndex() {
        return "这是基础Controller";
    }



}

