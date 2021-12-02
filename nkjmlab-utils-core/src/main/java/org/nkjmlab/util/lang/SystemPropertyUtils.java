package org.nkjmlab.util.lang;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

public class SystemPropertyUtils {
  private static org.apache.logging.log4j.Logger log =
      org.apache.logging.log4j.LogManager.getLogger();

  /**
   * java.net.useSystemProxies (デフォルト: false) 最近の Windows システムや Gnome 2.x システムでは、このプロパティーを true
   * に設定し、システムのプロキシ設定を使用するように java.net stack に指示できます (これらのどちらのシステムでも、ユーザーインタフェースからプロキシをグローバルに設定可能)。
   * このプロパティーは起動時に 1 回だけチェックされることに注意してください。
   */
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

  /**
   * http.nonProxyHosts (デフォルト: localhost|127.*|[::1])
   * プロキシを通過しないでアクセスすべきホストを指定します。通常これは内部ホストを定義します。このプロパティーの値は、'|' 文字で区切られたホストのリストです。
   * さらに、パターンマッチングのためにワイルドカード文字 '*' も使用できます。たとえば -Dhttp.nonProxyHosts=”*.foo.com|localhost”
   * は、foo.com ドメイン内のすべてのホストで、プロキシサーバーが指定されている場合でも localhost は直接アクセスする必要があることを示します。
   * デフォルト値には、ループバックアドレスのあらゆる一般的なバリエーションが含まれません。
   * 
   * @param hosts
   *
   */
  public static void nonProxyHosts(String... hosts) {
    System.setProperty("http.nonProxyHosts", String.join("|", hosts));

  }

  public static String[] getClassPaths() {
    return System.getProperty("java.class.path").split(File.pathSeparator);
  }

  public static String getClassPathOf(String regex) {

    for (String cp : SystemPropertyUtils.getClassPaths()) {
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
