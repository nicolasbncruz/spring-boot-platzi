package com.profesionaljava.platziboot.configuration;

import com.profesionaljava.platziboot.caseuse.GetUser;
import com.profesionaljava.platziboot.caseuse.GetUserImplement;
import com.profesionaljava.platziboot.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CaseUseConfiguration {

  @Bean
  GetUser getUser(UserService userService){
    return new GetUserImplement(userService);
  }

}
