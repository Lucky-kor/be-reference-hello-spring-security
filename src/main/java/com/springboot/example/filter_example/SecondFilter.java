package com.springboot.example.filter_example;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.FilterConfig;
import java.io.IOException;

@Slf4j
public class SecondFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
        log.info("# SecondFilter init()");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        log.info("# SecondFilter start");
        chain.doFilter(request, response);
        log.info("# SecondFilter end");
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
        log.info("# SecondFilter destroyed");
    }
}
