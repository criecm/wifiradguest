package ecm.arrubaguest.server;

import java.io.Serializable;

public class ServiceException  extends Exception implements Serializable{

   private String timeout;

  public ServiceException() {
  }

  public ServiceException(String timeout) {
    this.timeout = timeout;
  }

  public String getSymbol() {
    return this.timeout;
  }
}
