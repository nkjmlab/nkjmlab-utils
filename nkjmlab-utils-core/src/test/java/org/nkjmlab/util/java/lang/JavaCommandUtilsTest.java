package org.nkjmlab.util.java.lang;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.io.File;
import java.util.List;

import org.junit.jupiter.api.Test;

class JavaCommandUtilsTest {

  @Test
  public void testGetClasspathElements() {
    String[] classpath = JavaCommandUtils.getClasspathElements();
    assertThat(classpath).as("Classpath should not be null or empty").isNotNull().isNotEmpty();
    assertThat(classpath[0])
        .as("Classpath elements should not contain the path separator")
        .doesNotContain(File.pathSeparator);
  }

  @Test
  public void testFindClasspathElement() {
    String regex = ".*";
    assertThatThrownBy(() -> JavaCommandUtils.findClasspathElement(regex))
        .as("Should throw IllegalArgumentException if multiple or no matches found")
        .isInstanceOf(IllegalArgumentException.class);
  }

  @Test
  public void testFindClasspathElements() {
    List<String> elements = JavaCommandUtils.findClasspathElements(".*junit.*");
    assertThat(elements)
        .as("Classpath elements list should not be null or empty")
        .isNotNull()
        .isNotEmpty()
        .allMatch(elem -> elem.contains("junit"), "All classpath elements should contain 'java'");
  }

  @Test
  public void testFindJavaCommand() {
    String javaCommand = JavaCommandUtils.findJavaCommand();
    assertThat(javaCommand).as("Java command path should not be null").isNotNull();
    assertThat(
            new File(javaCommand + (JavaSystemProperties.isOsNameContainsWindows() ? ".exe" : ""))
                .canExecute())
        .isTrue();
  }
}
