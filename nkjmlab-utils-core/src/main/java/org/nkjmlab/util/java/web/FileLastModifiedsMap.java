package org.nkjmlab.util.java.web;

import java.io.File;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.nkjmlab.util.java.io.FileUtils;

public class FileLastModifiedsMap {
  private final Map<Path, Long> fileModifiedDates;
  private final Path baseDirectory;

  private FileLastModifiedsMap(Path baseDirectory, Map<Path, Long> fileModifiedDates) {
    this.baseDirectory = baseDirectory;
    this.fileModifiedDates = fileModifiedDates;
  }

  public static FileLastModifiedsMap create(
      Path baseDirectory, int maxDepth, String... extentions) {
    return new FileLastModifiedsMap(
        baseDirectory, listFileModifiedDates(baseDirectory, maxDepth, extentions));
  }

  public static Map<Path, Long> listFileModifiedDates(
      Path baseDirectory, int maxDepth, String... extentions) {
    List<Path> files =
        FileUtils.listFiles(
            baseDirectory,
            maxDepth,
            p ->
                Arrays.stream(extentions)
                    .filter(ext -> p.toString().endsWith(ext))
                    .findAny()
                    .isPresent());

    Map<Path, Long> fileModifiedDates =
        files.stream().collect(Collectors.toMap(f -> f, f -> f.toFile().lastModified()));
    return fileModifiedDates;
  }

  public Map<String, Long> toRelativePath() {
    return fileModifiedDates.entrySet().stream()
        .collect(
            Collectors.toMap(
                en ->
                    en.getKey()
                        .toAbsolutePath()
                        .toString()
                        .replace(baseDirectory.toAbsolutePath().toString(), "")
                        .replace(".", "_")
                        .replace("-", "_")
                        .replace(File.separator, "_")
                        .replaceFirst("_", ""),
                en -> en.getValue()));
  }
}
