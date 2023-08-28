package com.kivi.framework.web.controller;

import java.net.MalformedURLException;
import java.net.URL;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 全局的控制器
 *
 */
@RestController
@RequestMapping( "/global" )
public class UrlMappingController {

    @GetMapping( path = "/baseUrl" )
    public String baseUrl( HttpServletRequest request ) {
        StringBuffer baseUrl = new StringBuffer();
        try {
            URL url = new URL(request.getRequestURL().toString());
            baseUrl.append("http://").append(url.getHost()).append(":").append(url.getPort());
        }
        catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return baseUrl.toString();
    }
}
