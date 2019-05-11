package com.ripple.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by intel on 5/9/2019.
 */

@RestController
public class HealthController {

    @RequestMapping("/health")
    public String checkHealth(){
        return "UP";
    }
}
