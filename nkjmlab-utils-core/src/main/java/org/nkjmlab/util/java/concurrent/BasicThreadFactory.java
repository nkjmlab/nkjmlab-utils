package org.nkjmlab.util.java.concurrent;

import java.lang.Thread.UncaughtExceptionHandler;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

public class BasicThreadFactory implements ThreadFactory {

  private final String threadNamePrefix;
  private final Boolean daemon;
  private final Integer priority;
  private final UncaughtExceptionHandler uncaughtExceptionHandler;
  private final AtomicInteger threadNumber = new AtomicInteger(1);

  private BasicThreadFactory(
      String threadNamePrefix,
      Boolean daemon,
      Integer priority,
      UncaughtExceptionHandler uncaughtExceptionHandler) {
    this.threadNamePrefix = threadNamePrefix;
    this.daemon = daemon;
    this.priority = priority;
    this.uncaughtExceptionHandler = uncaughtExceptionHandler;
  }

  public static Builder builder() {
    return new Builder();
  }

  public static Builder builder(String threadNamePrefix, boolean daemon) {
    return new Builder().setThreadNamePrefix(threadNamePrefix).setDaemon(daemon);
  }

  public static Builder builder(String threadNamePrefix) {
    return new Builder().setThreadNamePrefix(threadNamePrefix);
  }

  @Override
  public Thread newThread(Runnable runnable) {
    Thread thread = Executors.defaultThreadFactory().newThread(runnable);

    if (threadNamePrefix != null) {
      thread.setName(threadNamePrefix + "-" + threadNumber.getAndIncrement());
    }
    if (daemon != null) {
      thread.setDaemon(daemon);
    }
    if (priority != null) {
      thread.setPriority(priority);
    }
    if (uncaughtExceptionHandler != null) {
      thread.setUncaughtExceptionHandler(uncaughtExceptionHandler);
    }

    return thread;
  }

  public static class Builder {
    private String threadNamePrefix;
    private Boolean daemon;
    private Integer priority;
    private UncaughtExceptionHandler uncaughtExceptionHandler =
        (t, e) -> org.nkjmlab.util.java.logging.LogManager.createLogger(t.getName()).error(e, e);

    public Builder setThreadNamePrefix(String threadNamePrefix) {
      this.threadNamePrefix = threadNamePrefix;
      return this;
    }

    public Builder setDaemon(boolean daemon) {
      this.daemon = daemon;
      return this;
    }

    public Builder setPriority(int priority) {
      this.priority = priority;
      return this;
    }

    public BasicThreadFactory build() {
      return new BasicThreadFactory(threadNamePrefix, daemon, priority, uncaughtExceptionHandler);
    }

    public Builder setUncaughtExceptionHandler(UncaughtExceptionHandler handler) {
      this.uncaughtExceptionHandler = handler;
      return this;
    }
  }
}
