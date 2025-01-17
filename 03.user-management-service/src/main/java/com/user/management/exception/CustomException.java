package com.user.management.exception;

public class CustomException {

  public static class UserAlreadyExists extends RuntimeException {
    public UserAlreadyExists(String message) {
      super(message);
    }
  }

  public static class UserNotFound extends RuntimeException {
    public UserNotFound(String message) {
      super(message);
    }
  }

}
