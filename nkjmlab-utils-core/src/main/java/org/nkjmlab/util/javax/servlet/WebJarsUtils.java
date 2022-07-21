package org.nkjmlab.util.javax.servlet;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.nkjmlab.util.java.lang.ParameterizedStringUtils;
import org.nkjmlab.util.java.lang.SystemPropertyUtils;

public class WebJarsUtils {

  static String findWebJarVersionFromClassPath(String libName) {
    String regex = "^" + libName + "-(.*).jar";

    List<String> versions =
        SystemPropertyUtils.findClassPathElements(regex).stream().map(classPath -> {
          Pattern pattern = Pattern.compile(regex);
          Matcher m = pattern.matcher(new File(classPath).getName());
          if (m.find()) {
            return m.group(1);
          } else {
            throw new IllegalStateException(
                ParameterizedStringUtils.newString("{} should be one in classpath ({})", regex,
                    SystemPropertyUtils.getClassPathElements()));
          }
        }).collect(Collectors.toList());

    if (versions.size() == 0) {
      throw new IllegalStateException(ParameterizedStringUtils.newString(
          "{} should be one in classpath ({})", regex, SystemPropertyUtils.getClassPathElements()));
    } else if (versions.size() == 1) {
      return versions.get(0);
    } else {
      return versions.stream().filter(
          v -> v.toLowerCase().matches("([0-9]|\\.|-|beta|alpha|stable|release|stable|snapshot)*"))
          .findAny()
          .orElseThrow(() -> new IllegalStateException(
              ParameterizedStringUtils.newString("{} should be one in classpath ({})", regex,
                  SystemPropertyUtils.getClassPathElements())));
    }
  }

  public static Map<String, String> findWebJarVersionsFromClassPath(String... libNames) {
    return Stream.of(libNames)
        .collect(Collectors.toMap(lib -> lib.replace(".", "_").replace("-", "_"),
            lib -> findWebJarVersionFromClassPath(lib)));
  }

}
