package org.nkjmlab.util.java.web;

import java.io.File;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.nkjmlab.util.java.io.FileUtils;

public class ViewModel implements Map<String, Object> {

  private static final String MODIFIED_DATES = "MODIFIED_DATES";
  private static final String LOCALE = "LOCALE";
  private static final String FILE_PATH = "FILE_PATH";
  private final Map<String, Object> map;

  private ViewModel(Map<String, Object> map) {
    this.map = Collections.unmodifiableMap(map);
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

  public static ViewModel.Builder builder() {
    return new ViewModel.Builder();
  }

  @Override
  public int size() {
    return map.size();
  }

  @Override
  public boolean isEmpty() {
    return map.isEmpty();
  }

  @Override
  public boolean containsKey(Object key) {
    return map.containsKey(key);
  }

  @Override
  public boolean containsValue(Object value) {
    return map.containsValue(value);
  }

  @Override
  public Object get(Object key) {
    return map.get(key);
  }

  @Override
  public Object put(String key, Object value) {
    return map.put(key, value);
  }

  @Override
  public Object remove(Object key) {
    return map.remove(key);
  }

  @Override
  public void putAll(Map<? extends String, ? extends Object> m) {
    map.putAll(m);
  }

  @Override
  public void clear() {
    map.clear();
  }

  @Override
  public Set<String> keySet() {
    return map.keySet();
  }

  @Override
  public Collection<Object> values() {
    return map.values();
  }

  @Override
  public Set<Entry<String, Object>> entrySet() {
    return map.entrySet();
  }

  /**
   * Creates {@link Builder} with initial parameters.
   *
   * @param map
   * @return
   */
  public static ViewModel.Builder builder(Map<String, Object> map) {
    return new Builder().putAll(map);
  }

  public static class Builder {

    private Locale locale = Locale.getDefault();

    private final Map<String, Object> map = new LinkedHashMap<>();

    public ViewModel.Builder setFileModifiedDate(
        Path directory, int maxDepth, String... extentions) {
      List<Path> files =
          FileUtils.listFiles(
              directory,
              maxDepth,
              p ->
                  Arrays.stream(extentions)
                      .filter(ext -> p.toString().endsWith(ext))
                      .findAny()
                      .isPresent());
      Map<String, Long> fileModifiedDate =
          files.stream()
              .collect(
                  Collectors.toMap(
                      f ->
                          f.toAbsolutePath()
                              .toString()
                              .replace(directory.toAbsolutePath().toString(), "")
                              .replace(".", "_")
                              .replace("-", "_")
                              .replace(File.separator, "_")
                              .replaceFirst("_", ""),
                      f -> f.toFile().lastModified()));
      map.put(MODIFIED_DATES, fileModifiedDate);
      return this;
    }

    public ViewModel.Builder setLocale(Locale locale) {
      this.locale = locale;
      return this;
    }

    public ViewModel.Builder setFilePath(String filePath) {
      map.put(FILE_PATH, filePath);
      return this;
    }

    public ViewModel build() {
      map.put(LOCALE, locale);
      ViewModel model = new ViewModel(map);
      return model;
    }

    public ViewModel.Builder put(String key, Object value) {
      map.put(key, value);
      return this;
    }

    public Builder putAll(Map<String, Object> keysAndvalues) {
      map.putAll(keysAndvalues);
      return this;
    }
  }
}
