package com.profesionaljava.platziboot.bean;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class MyBeanWithDependencyImplement implements MyBeanWithDependency{

  Log LOGGER = LogFactory.getLog(MyBeanWithDependencyImplement.class);

  //con esto inyectamos nuestra dependencia MyOperation dentro de la implementacion de la dependencia MyBeanWithDependency
  private MyOperation myOperation;
  // necesito el constructor para completar la inyeccion de la dependencia (myOperation)
  public MyBeanWithDependencyImplement(MyOperation myOperation) {
    this.myOperation = myOperation;
  }

  @Override
  public void printWithDependency() {
    LOGGER.info("Hemos ingresado al metodo printWithDependency");
    int numero = 1;
    LOGGER.debug("El numero enviado como parametro a la dependencia operacion es: "+ numero);
    myOperation.sum(numero);
    System.out.println(myOperation.sum(numero));
    System.out.println("Hola desde la implementacion de un bean con dependencia");
  }
}
