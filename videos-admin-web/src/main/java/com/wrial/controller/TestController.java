package com.wrial.controller;
/*
 * @Author  Wrial
 * @Date Created in 10:59 2019/8/16
 * @Description
 */

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TestController {

    @GetMapping("/hello")
    public String hello() {
        return "hello";
    }
    @GetMapping("/center")
    public String center() {
        return "center";
    }
}
