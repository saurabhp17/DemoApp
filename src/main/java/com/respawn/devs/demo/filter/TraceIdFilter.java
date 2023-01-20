package com.respawn.devs.demo.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.MDC;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.UUID;

@Component
@Order(0)
public class TraceIdFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
        String traceId = httpServletRequest.getHeader("X-TRACE-ID");
        if(traceId == null){
            traceId = UUID.randomUUID().toString();
        }
        MDC.put("traceId", traceId);

        try{
            httpServletResponse.setHeader("X-TRACE-ID", traceId);
            filterChain.doFilter(servletRequest, servletResponse);
        }finally {
            MDC.clear();
        }
    }
}
