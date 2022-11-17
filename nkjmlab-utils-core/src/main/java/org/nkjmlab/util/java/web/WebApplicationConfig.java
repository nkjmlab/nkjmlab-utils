package org.nkjmlab.util.java.web;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import org.nkjmlab.util.java.io.SystemFileUtils;
import org.nkjmlab.util.java.lang.ResourceUtils;

public class WebApplicationConfig {
  private final File appRootDirectory;
  private final File webRootDirectory;
  private final File userHomeDirectory;
  private final File backupDirectory;
  /**
   * Key is jars name and value is version.
   */
  private final Map<String, String> webJars;

  public WebApplicationConfig(File appRootDirectory, File webRootDirectory, File userHomeDirectory,
      File backupDirectory, Map<String, String> webJars) {
    this.appRootDirectory = appRootDirectory;
    this.webRootDirectory = webRootDirectory;
    this.userHomeDirectory = userHomeDirectory;
    this.backupDirectory = backupDirectory;
    this.webJars = webJars;
  }

  public File getAppRootDirectory() {
    return appRootDirectory;
  }

  public File getWebRootDirectory() {
    return webRootDirectory;
  }

  public File getUserHomeDirectory() {
    return userHomeDirectory;
  }

  public File getBackupDirectory() {
    return backupDirectory;
  }

  public Map<String, String> getWebJars() {
    return webJars;
  }

  public static WebApplicationConfig.Builder builder() {
    return new Builder();
  }

  public static class Builder {
    private File appRootDirectory = ResourceUtils.getResourceAsFile("/");
    private String webRootDirectoryName = "webroot";
    private File userHomeDirectory = SystemFileUtils.getUserHomeDirectory();
    private String backupDirectoryName = "webapp-bkup";
    private List<String> webJars = new ArrayList<>();

    public WebApplicationConfig.Builder addWebJar(String... webJars) {
      this.webJars.addAll(Arrays.asList(webJars));
      return this;
    }

    public WebApplicationConfig build() {
      File webRootDirectory = new File(appRootDirectory, webRootDirectoryName);
      File backUpDirectory = new File(userHomeDirectory, backupDirectoryName);
      Map<String, String> webJars =
          WebJarsUtils.findWebJarVersionsFromClassPath(this.webJars.toArray(String[]::new));
      return new WebApplicationConfig(appRootDirectory, webRootDirectory, userHomeDirectory,
          backUpDirectory, webJars);
    }
  }
}
