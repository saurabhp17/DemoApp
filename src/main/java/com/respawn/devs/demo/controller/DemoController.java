package com.respawn.devs.demo.controller;

import com.respawn.devs.demo.DemoApplication;
import com.respawn.devs.demo.entity.User;
import com.respawn.devs.demo.service.DemoService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("/demo/api")
@Slf4j
public class DemoController {

    Logger logger = LoggerFactory.getLogger(DemoApplication.class);

    @Autowired
    private DemoService demoService;

    @GetMapping(value = "/run-multiple-threads")
    public String runMultiThreadedMethod() throws InterruptedException {
        log.info("run-multiple-threads API called");

        for(int i=0; i<10; i++) {
            demoService.runAsyncMethod(i);
        }
        return "Success in calling multithreaded API";
    }

    @GetMapping("/getUser/{id}")
    public User getUserById(@PathVariable int id){
        List<User> users = getUsers();
        User user = users.stream().filter(u->u.getId()==id).findAny().orElse(null);

        if(user != null){
            log.info("User found with id {}", id);
            log.info("User : {}", user);
            return user;
        }else{
            try {
                throw new Exception();
            }catch (Exception e){
                e.printStackTrace();
                log.error("User not found with id {}", id);
            }
            return new User();
        }
    }

    private List<User> getUsers(){
        return Stream.of(new User(1, "aaa"),
                new User(2, "bbb"),
                new User(3, "ccc")).collect(Collectors.toList());
    }


}
