package org.nkjmlab.util.openai;

public class OpenAi4jException extends RuntimeException {

  private static final long serialVersionUID = -3645952699944789665L;

  public OpenAi4jException(String message) {
    super(message);
  }

  public OpenAi4jException(String message, Throwable cause) {
    super(message, cause);
  }
}
