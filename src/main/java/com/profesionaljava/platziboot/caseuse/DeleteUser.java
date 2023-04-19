package com.profesionaljava.platziboot.caseuse;

import com.profesionaljava.platziboot.service.UserService;
import org.springframework.stereotype.Component;

@Component
public class DeleteUser {
  private UserService userService;

  public DeleteUser(UserService userService) {
    this.userService = userService;
  }

  public void remove(Long id) {
    userService.delete(id);
  }
}
