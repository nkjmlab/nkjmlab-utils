package org.nkjmlab.util.java.web;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.nkjmlab.util.java.lang.JavaCommandUtils;
import org.nkjmlab.util.java.lang.ParameterizedStringFormatter;

public class WebJarsUtils {

  static String findWebJarVersionFromClassPath(String libName) {
    String regex = "^" + libName + "-(.*).jar";

    List<String> versions =
        JavaCommandUtils.findClasspathElements(regex).stream()
            .map(
                classPath -> {
                  Pattern pattern = Pattern.compile(regex);
                  Matcher m = pattern.matcher(new File(classPath).getName());
                  if (m.find()) {
                    return m.group(1);
                  } else {
                    Object[] params = {regex, JavaCommandUtils.getClasspathElements()};
                    throw new IllegalStateException(
                        ParameterizedStringFormatter.DEFAULT.format(
                            "{} should be one in classpath ({})", params));
                  }
                })
            .collect(Collectors.toList());

    if (versions.size() == 0) {
      Object[] params = {regex, JavaCommandUtils.getClasspathElements()};
      throw new IllegalStateException(
          ParameterizedStringFormatter.DEFAULT.format(
              "{} should be one in classpath ({})", params));
    } else if (versions.size() == 1) {
      return versions.get(0);
    } else {
      Object[] params = {regex, JavaCommandUtils.getClasspathElements()};
      return versions.stream()
          .filter(
              v ->
                  v.toLowerCase()
                      .matches("([0-9]|\\.|-|beta|alpha|stable|release|stable|snapshot)*"))
          .findAny()
          .orElseThrow(
              () ->
                  new IllegalStateException(
                      ParameterizedStringFormatter.DEFAULT.format(
                          "{} should be one in classpath ({})", params)));
    }
  }

  public static Map<String, String> findWebJarVersionsFromClassPath(String... libNames) {
    return Stream.of(libNames)
        .collect(
            Collectors.toMap(
                lib -> lib.replace(".", "_").replace("-", "_"),
                lib -> findWebJarVersionFromClassPath(lib)));
  }
}
