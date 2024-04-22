package org.nkjmlab.util.java.web;

import java.nio.file.Path;

import org.nkjmlab.util.java.lang.ClasspathResource;

public record WebApplicationFileLocation(Path appRootDirectory, Path webRootDirectory) {

  public static WebApplicationFileLocation.Builder builder() {
    return new Builder();
  }

  public static class Builder {
    private Path appRootDirectory =
        ClasspathResource.of(WebApplicationFileLocation.class, "/").getResourceAsFile().toPath();
    private String webRootDirectoryName = "webroot";

    private Builder() {}

    public Builder setWebRootDirectoryName(String dirName) {
      this.webRootDirectoryName = dirName;
      return this;
    }

    public WebApplicationFileLocation build() {
      return new WebApplicationFileLocation(
          appRootDirectory, appRootDirectory.resolve(webRootDirectoryName));
    }
  }
}
