package org.nkjmlab.util.java.lang;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.nkjmlab.util.java.function.Try;

public class JavaSystemProperties {
  private final Map<JavaSystemProperty, String> properties;

  private JavaSystemProperties(Map<JavaSystemProperty, String> properties) {
    this.properties = properties;
  }

  public String get(JavaSystemProperty key) {
    return properties.get(key);
  }

  @Override
  public String toString() {
    return properties.toString();
  }

  /**
   * @see
   *     https://docs.oracle.com/javase/jp/17/docs/api/java.base/java/lang/System.html#getProperties()
   */
  public enum JavaSystemProperty {
    JAVA_VERSION,
    JAVA_VERSION_DATE,
    JAVA_VENDOR,
    JAVA_VENDOR_URL,
    JAVA_VENDOR_VERSION,
    JAVA_HOME,
    JAVA_VM_SPECIFICATION_VERSION,
    JAVA_VM_SPECIFICATION_VENDOR,
    JAVA_VM_SPECIFICATION_NAME,
    JAVA_VM_VERSION,
    JAVA_VM_VENDOR,
    JAVA_VM_NAME,
    JAVA_SPECIFICATION_VERSION,
    JAVA_SPECIFICATION_VENDOR,
    JAVA_SPECIFICATION_NAME,
    JAVA_CLASS_VERSION,
    JAVA_CLASS_PATH,
    JAVA_LIBRARY_PATH,
    JAVA_IO_TMPDIR,
    JAVA_COMPILER,
    OS_NAME,
    OS_ARCH,
    OS_VERSION,
    FILE_SEPARATOR,
    PATH_SEPARATOR,
    LINE_SEPARATOR,
    USER_NAME,
    USER_HOME,
    USER_DIR,
    NATIVE_ENCODING,
    JDK_MODULE_PATH,
    JDK_MODULE_UPGRADE_PATH,
    JDK_MODULE_MAIN,
    JDK_MODULE_MAIN_CLASS;

    private final String propertyName;

    JavaSystemProperty() {
      this.propertyName = name().toLowerCase().replace("_", ".");
    }

    public String getPropertyName() {
      return propertyName;
    }
  }

  public static JavaSystemProperties create() {
    return new JavaSystemProperties(
        Stream.of(JavaSystemProperty.values())
            .collect(
                Collectors.toMap(
                    p -> p,
                    p -> Try.getOrElse(() -> System.getProperty(p.getPropertyName(), ""), ""))));
  }
}
