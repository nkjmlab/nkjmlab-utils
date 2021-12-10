package org.nkjmlab.util.java.concurrent;

import java.util.concurrent.atomic.AtomicBoolean;

public class CallOnceTask implements Runnable {
  private final AtomicBoolean done = new AtomicBoolean();
  private final Runnable task;

  public CallOnceTask(Runnable task) {
    this.task = task;
  }

  @Override
  public void run() {
    if (done.compareAndSet(false, true)) {
      task.run();
    }
  }
}
