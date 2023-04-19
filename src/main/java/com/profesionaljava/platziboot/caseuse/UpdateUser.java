package com.profesionaljava.platziboot.caseuse;

import com.profesionaljava.platziboot.entity.User;
import com.profesionaljava.platziboot.service.UserService;
import org.springframework.stereotype.Component;

@Component
public class UpdateUser {
  private UserService userService;

  public UpdateUser(UserService userService) {
    this.userService = userService;
  }

  public User update(User newUser, Long id) {
    return userService.update(newUser, id);
  }
}
