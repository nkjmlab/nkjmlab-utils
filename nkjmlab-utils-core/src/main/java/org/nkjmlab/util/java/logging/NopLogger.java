package org.nkjmlab.util.java.logging;

public class NopLogger implements Logger {

  public static final NopLogger INSTANCE = new NopLogger();

  @Override
  public void debug(String format, Object... params) {}

  @Override
  public void error(String format, Object... params) {}

  @Override
  public void error(Throwable message, Throwable throwable) {}

  @Override
  public void info(String format, Object... params) {}

  @Override
  public void trace(String format, Object... params) {}

  @Override
  public void warn(String format, Object... params) {}

  @Override
  public void warn(Throwable message, Throwable throwable) {}


}
