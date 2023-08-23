package org.nkjmlab.util.java.web;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.nkjmlab.util.java.io.SystemFileUtils;
import org.nkjmlab.util.java.lang.ResourceUtils;

public class WebApplicationConfig {
  private final Path appRootDirectory;
  private final Path webRootDirectory;
  private final Path userHomeDirectory;
  private final Path backupDirectory;
  /** Key is jar's name and, value is the version. */
  private final Map<String, String> webJars;

  public WebApplicationConfig(
      Path appRootDirectory,
      Path webRootDirectory,
      Path userHomeDirectory,
      Path backupDirectory,
      Map<String, String> webJars) {
    this.appRootDirectory = appRootDirectory;
    this.webRootDirectory = webRootDirectory;
    this.userHomeDirectory = userHomeDirectory;
    this.backupDirectory = backupDirectory;
    this.webJars = webJars;
  }

  public Path getAppRootDirectory() {
    return appRootDirectory;
  }

  public Path getWebRootDirectory() {
    return webRootDirectory;
  }

  public Path getUserHomeDirectory() {
    return userHomeDirectory;
  }

  public Path getBackupDirectory() {
    return backupDirectory;
  }

  public Map<String, String> getWebJars() {
    return webJars;
  }

  public static WebApplicationConfig.Builder builder() {
    return new Builder();
  }

  public static class Builder {
    private Path appRootDirectory = ResourceUtils.getResourceAsFile("/").toPath();
    private String webRootDirectoryName = "webroot";
    private Path userHomeDirectory = SystemFileUtils.getUserHomeDirectory().toPath();
    private String backupDirectoryName = "webapp-bkup";
    private List<String> webJars = new ArrayList<>();

    private Builder() {}

    public WebApplicationConfig.Builder addWebJar(String... webJars) {
      this.webJars.addAll(Arrays.asList(webJars));
      return this;
    }

    public WebApplicationConfig build() {
      Path webRootDirectory = appRootDirectory.resolve(webRootDirectoryName);
      Path backUpDirectory = userHomeDirectory.resolve(backupDirectoryName);
      Map<String, String> webJars =
          WebJarsUtils.findWebJarVersionsFromClassPath(this.webJars.toArray(String[]::new));
      return new WebApplicationConfig(
          appRootDirectory, webRootDirectory, userHomeDirectory, backUpDirectory, webJars);
    }
  }
}
