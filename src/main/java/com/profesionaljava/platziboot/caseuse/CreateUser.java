package com.profesionaljava.platziboot.caseuse;

import com.profesionaljava.platziboot.entity.User;
import com.profesionaljava.platziboot.service.UserService;
import org.springframework.stereotype.Component;

@Component
public class CreateUser {
  private UserService userService;

  public CreateUser(UserService userService) {
    this.userService = userService;
  }

  public User save(User newUser) {
    return userService.save(newUser);
  }
}
