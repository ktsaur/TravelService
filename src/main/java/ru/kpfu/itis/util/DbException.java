package ru.kpfu.itis.util;

public class DbException extends Exception {
  public DbException() {
    super();
  }

  public DbException(String message) {
    super(message);
  }

  public DbException(String message, Throwable cause) {
    super(message, cause);
  }

  public DbException(Throwable cause) {
    super(cause);
  }
}
