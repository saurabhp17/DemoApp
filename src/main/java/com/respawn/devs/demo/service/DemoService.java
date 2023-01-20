package com.respawn.devs.demo.service;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class DemoService {

    @Async
    public void runAsyncMethod(int i, String traceId) throws InterruptedException {

        MDC.put("traceId", traceId);
        Thread.sleep(1000);
        log.info("Current iteration {} ", i);
    }

}
