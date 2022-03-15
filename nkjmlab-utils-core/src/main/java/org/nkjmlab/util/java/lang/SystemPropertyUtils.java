package org.nkjmlab.util.java.lang;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SystemPropertyUtils {

  public static void setUseSystemProxies() {
    System.setProperty("java.net.useSystemProxies", "true");
  }

  public static void setHttpProxy(String host, int port) {
    System.setProperty("http.proxyHost", host);
    System.setProperty("http.proxyPort", String.valueOf(port));
  }

  public static void setHttpsProxy(String host, int port) {
    System.setProperty("https.proxyHost", host);
    System.setProperty("https.proxyPort", String.valueOf(port));
  }

  public static void setNonProxyHosts(String... hosts) {
    System.setProperty("http.nonProxyHosts", String.join("|", hosts));

  }

  public static String[] getClassPathElements() {
    return System.getProperty("java.class.path").split(File.pathSeparator);
  }

  public static String findOneClassPathElement(String regex) {
    String[] classPathElements = getClassPathElements();
    List<String> elements = Arrays.stream(classPathElements)
        .filter(elem -> new File(elem).getName().matches(regex)).collect(Collectors.toList());
    if (elements.size() == 1) {
      return elements.get(0);
    } else {
      throw new IllegalStateException(ParameterizedStringUtils
          .newString("Jar should be one in class path ({})", Arrays.toString(classPathElements)));
    }
  }

  public static String findJavaCommand() {
    String javaHome = System.getProperty("java.home");
    return new File(new File(javaHome, "bin"), "java").getAbsolutePath();
  }

  public static Map<String, String> getJavaProperties() {
    return Stream
        .of("os.name", "os.version", "java.class.version", "java.specification.version",
            "java.vm.name", "java.vm.version", "java.vm.vendor", "java.home", "java.class.path",
            "user.name", "user.home", "user.dir")
        .collect(Collectors.toMap(p -> p,
            p -> ParameterizedStringUtils.newString("{}={}", p, System.getProperty(p))));
  }


}
