package org.nkjmlab.util.java.lang;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.nkjmlab.util.java.lang.JavaSystemProperties.JavaSystemProperty;

/** Utility class to manipulate and access Java command and classpath information. */
public class JavaCommandUtils {
  private JavaCommandUtils() {}

  /**
   * Retrieves the elements of the system classpath.
   *
   * @return an array of strings, each representing an element of the classpath
   */
  public static String[] getClasspathElements() {
    return System.getProperty("java.class.path").split(File.pathSeparator);
  }

  /**
   * Finds a single classpath element that matches the given regular expression. If exactly one
   * match is found, it returns that element; otherwise, throws an exception.
   *
   * @param regex the regular expression to match against the classpath elements
   * @return the classpath element that matches the specified regex
   * @throws IllegalArgumentException if zero or more than one element matches the regex
   */
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

  /**
   * Finds all classpath elements that match the specified regular expression.
   *
   * @param regex the regular expression to match against the classpath elements
   * @return a list of classpath elements that match the specified regex
   */
  public static List<String> findClasspathElements(String regex) {
    String[] classPathElements = getClasspathElements();
    List<String> elements =
        Arrays.stream(classPathElements)
            .filter(elem -> new File(elem).getName().matches(regex))
            .collect(Collectors.toList());
    return elements;
  }

  /**
   * Determines the absolute path to the Java executable within the current JAVA_HOME directory.
   *
   * @return the absolute path to the Java command
   */
  public static String findJavaCommand() {
    String javaHome = JavaSystemProperties.getProperty(JavaSystemProperty.JAVA_HOME);
    return new File(new File(javaHome, "bin"), "java").getAbsolutePath();
  }
}
