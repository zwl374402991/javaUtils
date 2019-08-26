package com.untils.controller;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/csv")
@Api("csv 解析")
public class csvUtilsController {


    @GetMapping("/say")
    public String test () {
        return "hello world";
    }
}
