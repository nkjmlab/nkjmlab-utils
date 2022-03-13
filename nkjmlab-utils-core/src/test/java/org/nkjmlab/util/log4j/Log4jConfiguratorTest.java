package org.nkjmlab.util.log4j;

import org.apache.logging.log4j.Level;
import org.junit.jupiter.api.Test;

class Log4jConfiguratorTest {
  private static final org.nkjmlab.util.java.logging.SimpleLogger log =
      org.nkjmlab.util.java.logging.LogManager.createLogger();

  @Test
  void testLog() {
    log.debug("debug");
    log.trace("trace");
    Log4jConfigurator.overrideByBundledXmlConfiguration(Level.TRACE, true);
    log.debug("debug");
    log.trace("trace");
  }


}
