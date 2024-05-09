package org.nkjmlab.util.java.lang;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class JavaCommandUtils {
  private JavaCommandUtils() {}

  public static String[] getClasspathElements() {
    return System.getProperty("java.class.path").split(File.pathSeparator);
  }

  public static String findClasspathElement(String regex) {
    List<String> elements = findClasspathElements(regex);
    if (elements.size() == 1) {
      return elements.get(0);
    } else {
      Object[] params = {regex, elements, getClasspathElements()};
      throw new IllegalArgumentException(
          ParameterizedStringFormatter.DEFAULT.format(
              "{} should be one in classpath. found {}, in {}", params));
    }
  }

  public static List<String> findClasspathElements(String regex) {
    String[] classPathElements = getClasspathElements();
    List<String> elements =
        Arrays.stream(classPathElements)
            .filter(elem -> new File(elem).getName().matches(regex))
            .collect(Collectors.toList());
    return elements;
  }

  public static String findJavaCommand() {
    String javaHome = System.getProperty("java.home");
    return new File(new File(javaHome, "bin"), "java").getAbsolutePath();
  }
}
