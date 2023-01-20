package com.respawn.devs.demo.controller;

import com.respawn.devs.demo.DemoApplication;
import com.respawn.devs.demo.config.ESConfig;
import com.respawn.devs.demo.entity.MetadataIndex;
import com.respawn.devs.demo.entity.User;
//import com.respawn.devs.demo.repository.MetadataRepository;
import com.respawn.devs.demo.service.DemoService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("/demo/api")
@Slf4j
public class DemoController {

    Logger logger = LoggerFactory.getLogger(DemoApplication.class);

    @Autowired
    ESConfig esConfig;

    @Autowired
    private DemoService demoService;

//    @Autowired
//    MetadataRepository metadataRepository;

    @GetMapping(value = "/run-multiple-threads")
    public String runMultiThreadedMethod() throws InterruptedException {
        log.info("run-multiple-threads API called");

        for(int i=0; i<10; i++) {
            demoService.runAsyncMethod(i);
        }
        return "Success in calling multithreaded API";
    }

    @GetMapping(value = "/saveUser")
    public String saveUser() {
        log.info("saveUser to elastic API called");
        log.info("ESConfig: "+esConfig.config());

        MetadataIndex metadataIndex = new MetadataIndex();
        metadataIndex.setCreatedDate(LocalDate.now());
        metadataIndex.setUuid(UUID.randomUUID().toString());
        metadataIndex.setCreatedTimeStamp(LocalDateTime.now().toString());

//        metadataRepository.save(metadataIndex);

        return "Success";
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
