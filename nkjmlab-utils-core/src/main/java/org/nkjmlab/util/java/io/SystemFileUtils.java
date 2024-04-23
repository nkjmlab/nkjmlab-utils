package org.nkjmlab.util.java.io;

import java.io.File;

public class SystemFileUtils {
  /**
   * Getting the temporal directory which is referenced by {@code
   * System.getProperty("java.io.tmpdir")}.
   *
   * @return
   */
  public static File getTempDirectory() {
    return new File(System.getProperty("java.io.tmpdir"));
  }

  /**
   * Getting the user's home directory which is referenced by {@code
   * System.getProperty("user.home")}.
   *
   * @return
   */
  public static File getUserHomeDirectory() {
    return new File(System.getProperty("user.home"));
  }

  /**
   * Getting the user directory which is referenced by {@code System.getProperty("user.dir")}.
   *
   * @return
   */
  public static File getUserDirectory() {
    return new File(System.getProperty("user.dir"));
  }

  public static String getTildeExpandAbsolutePath(File path) {
    return (path.getName().equals("~")
            || path.getPath().startsWith("~/")
            || path.getPath().startsWith("~\\"))
        ? path.getPath().replace("~", getUserHomeDirectory().getAbsolutePath())
        : path.getAbsolutePath();
  }
}
