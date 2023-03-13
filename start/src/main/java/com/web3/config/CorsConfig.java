package com.web3.config;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @Author fuxian
 * @Date 2023/3/13
 */
@Order(1)
@Component
@WebFilter(urlPatterns = "/*", filterName = "corsConfig")
public class CorsConfig implements Filter {

    private static final String ORIGIN = "Origin";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        String origin = httpRequest.getHeader(ORIGIN);

        httpResponse.setCharacterEncoding("UTF-8");
        httpResponse.setContentType("application/json; charset=utf-8");
        httpResponse.setHeader("Access-Control-Allow-Origin", origin);
        httpResponse.setHeader("Access-Control-Allow-Credentials", "true");
        httpResponse.setHeader("Access-Control-Allow-Methods", "*");
        // 如果允许所有header,就用*
        httpResponse.setHeader("Access-Control-Allow-Headers", "Content-Type,Authorization,token,userId,sysCode,requestSourceType");
        httpResponse.setHeader("Access-Control-Expose-Headers", "*");
        filterChain.doFilter(httpRequest, httpResponse);
    }

    @Override
    public void destroy() {
    }

}
