package com.respawn.devs.demo.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class DemoService {

    @Async
    public void runAsyncMethod(int i) throws InterruptedException {
        Thread.sleep(10);
        log.info("Current iteration {} ", i);
    }

}
