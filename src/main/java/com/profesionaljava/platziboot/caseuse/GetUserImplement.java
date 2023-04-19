package com.profesionaljava.platziboot.caseuse;

import com.profesionaljava.platziboot.entity.User;
import com.profesionaljava.platziboot.service.UserService;

import java.util.List;

public class GetUserImplement implements GetUser{

  private UserService userService;

  public GetUserImplement(UserService userService) {
    this.userService = userService;
  }

  @Override
  public List<User> getAll() {
    return userService.getAllUsers();
  }
}
