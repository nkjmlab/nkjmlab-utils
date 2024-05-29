package org.nkjmlab.util.junit;

import java.util.Arrays;

public class JUnitCallerUtils {

  private JUnitCallerUtils() {}

  public static void skipIfCalledByJUnit(Runnable func) {
    try {
      if (isCalledByJUnit() || isCalledBySurefire()) {
        return;
      }
    } catch (Throwable e) {
      throw new RuntimeException(e);
    }
    func.run();
  }

  /**
   * Checks if the current execution is initiated by Maven Surefire plugin. This method is useful to
   * determine if the execution context is a test environment, specifically when tests are run using
   * the Maven Surefire plugin.
   *
   * <p>It works by checking the {@code isTestEnvironment} system property, which should be set to
   * {@code true} within the Surefire plugin configuration. This approach allows the application to
   * identify if it is running in a test environment, enabling test-specific configurations or
   * behaviors.
   *
   * <p>To configure the test environment correctly, include the following in your Maven Surefire
   * plugin settings:
   *
   * <pre>{@code
   * <plugin>
   *   <groupId>org.apache.maven.plugins</groupId>
   *   <artifactId>maven-surefire-plugin</artifactId>
   *   <version>latest version</version>
   *   <configuration>
   *     <systemPropertyVariables>
   *       <isTestEnvironment>true</isTestEnvironment>
   *     </systemPropertyVariables>
   *   </configuration>
   * </plugin>
   * }</pre>
   *
   * @return true if the current execution is initiated by Maven Surefire, indicating a test
   *     environment.
   */
  private static boolean isCalledBySurefire() {
    return "true".equalsIgnoreCase(System.getProperty("isTestEnvironment"));
  }

  private static boolean isCalledByJUnit() {
    return Arrays.stream(Thread.currentThread().getStackTrace())
        .anyMatch(e -> e.getClassName().startsWith("org.junit."));
  }
}
