package com.profesionaljava.platziboot;

import com.profesionaljava.platziboot.bean.MyBean;
import com.profesionaljava.platziboot.bean.MyBeanWithDependency;
import com.profesionaljava.platziboot.bean.MyBeanWithProperties;
import com.profesionaljava.platziboot.component.ComponentDependency;
import com.profesionaljava.platziboot.dto.UserDto;
import com.profesionaljava.platziboot.entity.User;
import com.profesionaljava.platziboot.pojo.UserPojo;
import com.profesionaljava.platziboot.repository.UserRepository;
import com.profesionaljava.platziboot.service.UserService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.aop.scope.ScopedProxyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.domain.Sort;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@SpringBootApplication
public class PlatzibootApplication implements CommandLineRunner {

  Log LOGGER = LogFactory.getLog(PlatzibootApplication.class);

  private ComponentDependency componentDependency;
  private MyBean myBean;
  private MyBeanWithDependency myBeanWithDependency;
  private MyBeanWithProperties myBeanWithProperties;
  private UserPojo userPojo;
  private UserRepository userRepository;
  private UserService userService;

  //@Autowired --> ya no es obligatorio
  public PlatzibootApplication(@Qualifier("componentTwoImplement") ComponentDependency componentDependency, MyBean myBean, MyBeanWithDependency myBeanWithDependency, MyBeanWithProperties myBeanWithProperties, UserPojo userPojo, UserRepository userRepository, UserService userService) {
    this.componentDependency = componentDependency;
    this.myBean = myBean;
    this.myBeanWithDependency = myBeanWithDependency;
    this.myBeanWithProperties = myBeanWithProperties;
    this.userPojo = userPojo;
    this.userRepository = userRepository;
    this.userService = userService;
  }

  public static void main(String[] args) {
    SpringApplication.run(PlatzibootApplication.class, args);
  }

  @Override
  public void run(String... args) throws Exception {
//    ejemplosAnteriores();
    saveUsersInDataBase();
    getInformationJpqlFromUser();
    saveWithErrorTransactional();
  }

  private void saveWithErrorTransactional(){
    User test1 = new User("Test1Transactional1", "Test1Transactional1@domain.com", LocalDate.of(2020,1,1));
    User test2 = new User("Test2Transactional1", "Test2Transactional1@domain.com", LocalDate.of(2020,1,1));
    User test3 = new User("Test3Transactional1", "Test1Transactional1@domain.com", LocalDate.of(2020,1,1));
    User test4 = new User("Test4Transactional1", "Test4Transactional1@domain.com", LocalDate.of(2020,1,1));

    List<User> users = Arrays.asList(test1, test2, test3, test4);
    //Rollback con la anotación transactional
    try {
      userService.saveTransactional(users);
    }catch (Exception e){
      LOGGER.error("Esta es una excepcion dentro del metodo transaccional " + e);
    }
    userService.getAllUsers().stream()
        .forEach(user ->
            LOGGER.info("Este es el usuario dentro del metodo transaccional: " + user));
  }

  private void getInformationJpqlFromUser(){
    LOGGER.info("Usuario con el metodo findUserByEmail "+
        userRepository.findByUserEmail("user7@gmail.com")
            .orElseThrow(() -> new RuntimeException("No se encuentro el usuario")));

    userRepository.findAndSort("user", Sort.by("id").ascending())
        .stream()
        .forEach(user -> LOGGER.info("Usuario con metodo sort "+user));

    userRepository.findByName("user1")
        .stream()
        .forEach(user -> LOGGER.info("Usuario con query method "+user));

    LOGGER.info("Usuario con query method findByEmailAndName " + userRepository.findByEmailAndName("user2@gmail.com", "user2")
        .orElseThrow(()-> new RuntimeException("Usuario no encontrado por Email ni por Nombre")));

    //OR - LIKE
    userRepository.findByNameLike("%er1%")
        .stream()
        .forEach(user -> LOGGER.info("Usuario findByNameLike: "+user));
    userRepository.findByNameOrEmail(null, "user11@gmail.com")
        .stream()
        .forEach(user -> LOGGER.info("Usuario findByNameOrEmail: "+user));
    userRepository.findByNameOrEmail("user12", null)
        .stream()
        .forEach(user -> LOGGER.info("Usuario findByNameOrEmail x: "+user));
    // BETWEEN
    userRepository.findByBirthDateBetween(LocalDate.of(2020,3,1), LocalDate.of(2020,7,1))
        .stream()
        .forEach(user -> LOGGER.info("Usuario con intervalo de fechas: "+user));
    // ORDER BY
    userRepository.findByNameLikeOrderByIdDesc("%user1%")
        .stream()
        .forEach(user -> LOGGER.info("Usuario encontrado con like y ordenado descendente " + user));
    // PODEROSO CONTAINING!!!!
    userRepository.findByNameContainingOrderByIdAsc("user1")
        .stream()
        .forEach(user -> LOGGER.info("Usuario encontrado con CONTAINING y ordenado ascendente "+ user));

    //  NAMED PARAMETERS dentro de los Query methods
    LOGGER.info("El usuario a partir del named @Param es: " + userRepository.getAllByBirthDateAndEmail(LocalDate.of(2020,7,7),
            "user7@gmail.com")
        .orElseThrow(()->
            new RuntimeException("No se encontro el usuario a partir del named @Param")));

  }

  private void saveUsersInDataBase(){
    User user1 = new User("user1", "user1@gmail.com", LocalDate.of(2020,1,1));
    User user2 = new User("user2", "user2@gmail.com", LocalDate.of(2020,2,2));
    User user3 = new User("user3", "user3@gmail.com", LocalDate.of(2020,3,3));
    User user4 = new User("user4", "user4@gmail.com", LocalDate.of(2020,4,4));
    User user5 = new User("user5", "user5@gmail.com", LocalDate.of(2020,5,5));
    User user6 = new User("user6", "user6@gmail.com", LocalDate.of(2020,6,6));
    User user7 = new User("user7", "user7@gmail.com", LocalDate.of(2020,7,7));
    User user8 = new User("user8", "user8@gmail.com", LocalDate.of(2020,8,8));
    User user9 = new User("user9", "user9@gmail.com", LocalDate.of(2020,9,9));
    User user10 = new User("user10", "user10@gmail.com", LocalDate.of(2020,10,10));
    User user11 = new User("user11", "user11@gmail.com", LocalDate.of(2020,11,11));
    User user12 = new User("user12", "user12@gmail.com", LocalDate.of(2020,12,12));
    List<User> list = Arrays.asList(user1, user2, user3, user4, user5, user6, user7, user8, user9, user10, user11, user12);
    userRepository.saveAll(list);
//    list.stream().forEach(userRepository::save);
  }

  private void ejemplosAnteriores(){
    componentDependency.saludar();
    myBean.print();
    myBeanWithDependency.printWithDependency();
    System.out.println(myBeanWithProperties.function());
    System.out.println(userPojo.getEmail()+ " " + userPojo.getPassword()+ " Edad : "+userPojo.getEdad());
    try{
      //error de NumberException
      int number = 1/0;
      LOGGER.info("Mi valor : "+number);
    }catch (Exception e){
      LOGGER.error("Esto es un error al dividir por cero " + e.getMessage());
    }
  }

}
