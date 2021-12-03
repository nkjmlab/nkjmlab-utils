package org.nkjmlab.util.java.concurrent;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

public class NamedThreadFactory implements ThreadFactory {

  private static final AtomicInteger poolNumber = new AtomicInteger(1);
  private final AtomicInteger threadNumber = new AtomicInteger(1);
  private final String threadName;
  private final boolean daemon;

  public NamedThreadFactory(String threadName) {
    this(threadName, false);
  }

  public NamedThreadFactory(String threadName, boolean daemon) {
    this.threadName = threadName + "-pl-" + poolNumber.getAndIncrement() + "-th-";
    this.daemon = daemon;
  }

  @Override
  public Thread newThread(Runnable r) {
    Thread t = new Thread(r, threadName + threadNumber.getAndIncrement());
    if (daemon) {
      t.setDaemon(true);
    } else {
      t.setDaemon(false);
    }
    return t;
  }

}
