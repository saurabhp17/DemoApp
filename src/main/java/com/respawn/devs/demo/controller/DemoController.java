package com.respawn.devs.demo.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/demo/api")
@Slf4j
public class DemoController {

    @PostMapping(value = "/getUsers")
    public String fetchAllUsers(){
        log.info("getUsers API called");
        return "Success";
    }

}
