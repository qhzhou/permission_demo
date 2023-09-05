package com.example.permission_demo.config;

import com.example.permission_demo.service.UserService;
import com.example.permission_demo.service.impl.MockUserService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {

  @Bean
  @ConditionalOnMissingBean(UserService.class)
  public UserService userService() {
    return new MockUserService();
  }

}
