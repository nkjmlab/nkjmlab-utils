package org.nkjmlab.util.javax.servlet;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;
import org.nkjmlab.util.java.io.FileUtils;

public class ViewModel {

  private static final String MODIFIED_DATES = "MODIFIED_DATES";
  private static final String LOCALE = "LOCALE";
  private static final String FILE_PATH = "FILE_PATH";
  private final Map<String, Object> map;

  public ViewModel(Map<String, Object> map) {
    this.map = Collections.unmodifiableMap(map);
  }

  public Map<String, Object> getMap() {
    return map;
  }

  @Override
  public String toString() {
    return map.toString();
  }


  public Map<String, Long> getModifiedDates() {
    @SuppressWarnings("unchecked")
    Map<String, Long> ret = (Map<String, Long>) map.get(MODIFIED_DATES);
    return ret;
  }

  public Locale getLocale() {
    return (Locale) map.get(LOCALE);
  }

  public String getFilePath() {
    return (String) map.get(FILE_PATH);
  }

  public static Builder builder() {
    return new Builder();
  }

  public static class Builder {

    private Map<String, Long> fileModifiedDate = Collections.emptyMap();
    private Locale locale = Locale.getDefault();

    private final Map<String, Object> map = new TreeMap<>();

    private Builder() {}

    public Builder setFileModifiedDate(File directory, int maxDepth, String... extentions) {
      List<File> files = FileUtils.listFiles(directory, maxDepth, p -> Arrays.stream(extentions)
          .filter(ext -> p.toString().endsWith(ext)).findAny().isPresent());
      this.fileModifiedDate = files.stream()
          .collect(Collectors.toMap(
              f -> f.getAbsolutePath().replace(directory.getAbsolutePath(), "").replace(".", "_")
                  .replace("-", "_").replace(File.separator, "_").replaceFirst("_", ""),
              f -> f.lastModified()));
      return this;
    }

    public Builder setLocale(Locale locale) {
      this.locale = locale;
      return this;
    }

    public Builder setFilePath(String filePath) {
      map.put(FILE_PATH, filePath);
      return this;
    }

    public ViewModel build() {
      map.putAll(fileModifiedDate);
      map.put(LOCALE, locale);
      ViewModel model = new ViewModel(map);
      return model;
    }

    public void put(String key, Object value) {
      map.put(key, value);
    }

  }



}
