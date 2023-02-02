package com.macaplix.squareGames;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.annotation.Order;

import java.io.IOException;

@WebFilter("/*")
public class AddResponseHeaderFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // ...
    }

    @Override
    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
        String origin = ((HttpServletRequest)servletRequest).getHeader("origin");
        origin = (origin == null || origin.equals("")) ? "null" : origin;
        httpServletResponse.addHeader("Access-Control-Allow-Origin", origin);
        httpServletResponse.addHeader("Access-Control-Allow-Methods", "POST, GET, PUT, UPDATE, DELETE, OPTIONS");
        httpServletResponse.addHeader("Access-Control-Allow-Credentials", "true");
        httpServletResponse.addHeader("Access-Control-Allow-Headers",
                "Authorization, origin, content-type, accept, x-requested-with");

       //httpServletResponse.addHeader("Access-Control-Allow-Origin", "null");

        //httpServletResponse.addHeader("Access-Control-Allow-Credentials", "true");
         filterChain.doFilter(servletRequest, servletResponse);

    }

    @Override
    public void destroy() {
        // ...
    }
}

