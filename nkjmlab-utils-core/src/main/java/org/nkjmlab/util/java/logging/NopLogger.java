package org.nkjmlab.util.java.logging;

public class NopLogger implements Logger {

  public static NopLogger INSTANCE = new NopLogger();

  @Override
  public void trace(String format, Object... params) {
    // TODO Auto-generated method stub

  }

  @Override
  public void debug(String format, Object... params) {
    // TODO Auto-generated method stub

  }

  @Override
  public void info(String format, Object... params) {
    // TODO Auto-generated method stub

  }

  @Override
  public void warn(String format, Object... params) {
    // TODO Auto-generated method stub

  }

  @Override
  public void error(String format, Object... params) {
    // TODO Auto-generated method stub

  }

  @Override
  public void error(Throwable message, Throwable throwable) {
    // TODO Auto-generated method stub

  }


}
