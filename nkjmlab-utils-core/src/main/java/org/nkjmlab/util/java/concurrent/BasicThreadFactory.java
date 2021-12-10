package org.nkjmlab.util.java.concurrent;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

public class BasicThreadFactory implements ThreadFactory {

  private static final AtomicInteger groupNumber = new AtomicInteger(1);

  private final ThreadGroup threadGroup;
  private final String threadNamePrefix;
  private final boolean daemon;
  private final int priority;
  private final AtomicInteger threadNumber = new AtomicInteger(1);

  public BasicThreadFactory(String newGroupName) {
    this(newGroupName, false);
  }

  public BasicThreadFactory(String newGroupName, boolean daemon) {
    this(newGroupName, daemon, Thread.NORM_PRIORITY);
  }


  public BasicThreadFactory(String newGroupName, boolean daemon, int priority) {
    this(new ThreadGroup(Thread.currentThread().getThreadGroup(),
        newGroupName + "-group-" + groupNumber.getAndIncrement()), daemon, priority);
  }

  public BasicThreadFactory(ThreadGroup threadGroup, boolean daemon, int priority) {
    this.threadGroup = threadGroup;
    this.threadNamePrefix =
        threadGroup.getName() + "-in-" + BasicThreadFactory.class.getSimpleName() + "-";
    this.daemon = daemon;
    this.priority = priority;
  }



  @Override
  public Thread newThread(Runnable task) {
    Thread th = new Thread(threadGroup, threadNamePrefix + threadNumber.getAndIncrement());
    th.setDaemon(daemon);
    th.setPriority(priority);

    th.setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
      @Override
      public void uncaughtException(Thread t, Throwable e) {
        org.nkjmlab.util.java.logging.LogManager.getLogger(t.getName()).error(e, e);
      }
    });

    return th;
  }

}
