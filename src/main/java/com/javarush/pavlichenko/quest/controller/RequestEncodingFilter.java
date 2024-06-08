package com.javarush.pavlichenko.quest.controller;

import javax.servlet.*;
import java.io.IOException;

public class RequestEncodingFilter implements Filter {

    private static final String ENCODING = "UTF-8";

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        request.setCharacterEncoding(ENCODING);
        chain.doFilter(request, response);
    }
}
