package com.profesionaljava.platziboot.controller;

import com.profesionaljava.platziboot.caseuse.CreateUser;
import com.profesionaljava.platziboot.caseuse.DeleteUser;
import com.profesionaljava.platziboot.caseuse.GetUser;
import com.profesionaljava.platziboot.caseuse.UpdateUser;
import com.profesionaljava.platziboot.entity.User;
import com.profesionaljava.platziboot.repository.UserRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserRestController {
  // create, get, delete, updte
  private GetUser getUser;
  private CreateUser createUser;
  private DeleteUser deleteUser;
  private UpdateUser updateUser;
  private UserRepository userRepository;

  public UserRestController(GetUser getUser, CreateUser createUser, DeleteUser deleteUser, UpdateUser updateUser, UserRepository userRepository) {
    this.getUser = getUser;
    this.createUser = createUser;
    this.deleteUser = deleteUser;
    this.updateUser = updateUser;
    this.userRepository = userRepository;
  }

  @GetMapping("/")
  List<User> get(){
    return getUser.getAll();
  }

  @PostMapping("/")
  ResponseEntity<User> newUser(@RequestBody User newUser){
    return new ResponseEntity<>(createUser.save(newUser), HttpStatus.CREATED);
  }

  @DeleteMapping("/{id}")
  ResponseEntity deleteUsser(@PathVariable Long id){
    deleteUser.remove(id);
    return new ResponseEntity(HttpStatus.NO_CONTENT);
  }

  @PutMapping("/{id}")
  ResponseEntity<User> replaceUser(@RequestBody User newUser, @PathVariable Long id){
    return new ResponseEntity<>(updateUser.update(newUser, id), HttpStatus.OK);
  }

  @GetMapping("/pageable")
  List<User> getUserPageable(@RequestParam int page, @RequestParam int size){
    return userRepository.findAll(PageRequest.of(page, size)).getContent();
  }

}
