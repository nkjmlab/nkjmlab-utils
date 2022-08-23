package org.nkjmlab.util.h2;

import java.util.stream.Stream;

public class H2Options {
  /**
   *
   * @see http://www.h2database.com/html/performance.html?highlight=performance&search=performance#fast_import
   *      Fast Database Import To speed up large imports, consider using the following options
   *      temporarily: http://www.h2database.com/html/features.html#cache_settings
   */
  public static final String[] WITHOUT_LOG_OPTIONS = {"LOG=0", "UNDO_LOG=0"};

  private static final String QUAD_CACHE_SIZE = "CACHE_SIZE=" + 16384 * 4;

  public static final String[] FAST_LARGE_IMPORT_OPTIONS = Stream
      .concat(Stream.of(WITHOUT_LOG_OPTIONS), Stream.of(QUAD_CACHE_SIZE)).toArray(String[]::new);

  public static final String[] FAST_READONLY_OPTIONS = Stream
      .concat(Stream.of(WITHOUT_LOG_OPTIONS), Stream.of(QUAD_CACHE_SIZE, "ACCESS_MODE_DATA=r"))
      .toArray(String[]::new);

}
