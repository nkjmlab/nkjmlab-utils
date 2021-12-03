package org.nkjmlab.util.java.lang;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

public class SystemUtils {
  private static final org.nkjmlab.util.java.logging.Logger log =
      org.nkjmlab.util.java.logging.LogManager.getLogger();

  public static void useSystemProxies() {
    System.setProperty("java.net.useSystemProxies", "true");
    log.info("java.net.useSystemProxies=true");
  }

  public static void httpProxy(String host, int port) {
    System.setProperty("http.proxyHost", host);
    System.setProperty("http.proxyPort", String.valueOf(port));
    log.info("http.proxyHost={},http.proxyPort={}", host, port);
  }

  public static void httpsProxy(String host, int port) {
    System.setProperty("https.proxyHost", host);
    System.setProperty("https.proxyPort", String.valueOf(port));
    log.info("https.proxyHost={},https.proxyPort={}", host, port);
  }

  public static void nonProxyHosts(String... hosts) {
    System.setProperty("http.nonProxyHosts", String.join("|", hosts));

  }

  public static String[] getClassPaths() {
    return System.getProperty("java.class.path").split(File.pathSeparator);
  }

  public static String getClassPathOf(String regex) {
    for (String cp : SystemUtils.getClassPaths()) {
      if (new File(cp).getName().matches(regex)) {
        return cp;
      }
    }
    throw new RuntimeException(regex + " not found");
  }

  public static List<String> getClasspathsOf(List<String> regexs) {
    return regexs.stream().map(s -> "^" + s + "-[0-9\\.]*[-SNAPSHOT\\.]*\\.jar$")
        .collect(Collectors.toList()).stream().map(regex -> getClassPathOf(regex))
        .collect(Collectors.toList());
  }

}
