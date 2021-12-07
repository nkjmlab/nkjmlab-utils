package org.nkjmlab.util.java.io;

import java.io.File;
import java.nio.file.Path;

public class SystemFileUtils {
  /**
   * Getting a temp file object of the temporal directory which is referenced by
   * {@code System.getProperty("java.io.tmpdir")}.
   *
   * @return
   */
  public static File getTempDirectory() {
    return new File(getTempDirectoryPathString());

  }

  /**
   * Getting a path object of the temporal directory which is referenced by
   * {@code System.getProperty("java.io.tmpdir")}.
   *
   * @return
   */
  public static Path getTempDirectoryPath() {
    return getTempDirectory().toPath();

  }

  /**
   * Getting a string object of the temporal directory which is referenced by
   * {@code System.getProperty("java.io.tmpdir")}.
   *
   * @return
   */
  public static String getTempDirectoryPathString() {
    return System.getProperty("java.io.tmpdir");
  }

  /**
   * Getting a file object of the temporal directory which is referenced by
   * {@code System.getProperty("user.home")}.
   *
   * @return
   */
  public static File getUserDirectory() {
    return new File(getUserHomeDirectoryPathString());

  }

  /**
   * Getting a path object of the directory which is referenced by
   * {@code System.getProperty("user.home")}.
   *
   * @return
   */
  public static Path getUserDirectoryPath() {
    return getUserDirectory().toPath();

  }

  /**
   * Getting a string object of the directory which is referenced by
   * {@code System.getProperty("user.home")}.
   *
   * @return
   */
  public static String getUserHomeDirectoryPathString() {
    return System.getProperty("user.home");
  }

  public static String getCurrentDirectoryPathString() {
    return System.getProperty("user.dir");
  }

  public static File getCurrentDirectory() {
    return new File(getCurrentDirectoryPathString());
  }

  public static File getFileInCurrentDirectory(String relativePath) {
    return new File(getCurrentDirectoryPathString(), relativePath);
  }

  /**
   * Getting a temp file object in the directory in temporal directory which is referenced by
   * {@code System.getProperty("java.io.tmpdir")}.
   *
   * @param parent
   * @param fileName
   *
   * @return
   */
  public static File getTempFile(File parent, String fileName) {
    return new File(new File(getTempDirectory(), parent.getPath()), fileName);
  }

  /**
   * Getting a temp file object in the temporal directory which is referenced by
   * {@code System.getProperty("java.io.tmpdir")}.
   *
   * @param fileName
   * @return
   */
  public static File getTempFile(String fileName) {
    return new File(getTempDirectory(), fileName);
  }

  /**
   * Getting a file object in the user directory which is referenced by
   * {@code System.getProperty("user.home")}.
   *
   * @param fileName
   * @return
   */
  public static File getFileInUserDirectory(String fileName) {
    return new File(getUserDirectory(), fileName);
  }

  /**
   * Getting a file object in the user directory which is referenced by
   * {@code System.getProperty("user.home")}.
   *
   * @param parent
   * @param fileName
   *
   * @return
   */
  public static File getFileInUserDirectory(File parent, String fileName) {
    return new File(new File(getUserDirectory(), parent.getPath()), fileName);
  }
}
