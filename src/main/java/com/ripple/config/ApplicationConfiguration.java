package com.ripple.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan({"com.ripple.controller", "com.ripple.repository", "com.ripple.service"})
public class ApplicationConfiguration {
}
