package com.profesionaljava.platziboot.service;

import com.profesionaljava.platziboot.entity.User;
import com.profesionaljava.platziboot.repository.UserRepository;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService {
  private final Log LOG = LogFactory.getLog(UserService.class);
  private UserRepository userRepository;

  public UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  // Rollback con la anotación transactional
  @Transactional
  public void saveTransactional(List<User> users){
    users.stream()
        .peek(user -> LOG.info("Usuario insertado: " + user))
        .forEach(userRepository::save);
//        .forEach(user -> userRepository.save(user));
  }

  public List<User> getAllUsers(){
    return userRepository.findAll();
  }

  public User save(User newUser){
    return userRepository.save(newUser);
  }

  public void delete(Long id) {
    userRepository.delete(new User(id));
  }

  public User update(User newUser, Long id) {
    return userRepository.findById(id)
        .map(
          user -> {
            user.setEmail(newUser.getEmail());
            user.setBirthDate(newUser.getBirthDate());
            user.setName(newUser.getName());
            return userRepository.save(user);
          }
      ).orElseThrow(() -> new RuntimeException("No se encontro usuario a modificar"));

  }


}
