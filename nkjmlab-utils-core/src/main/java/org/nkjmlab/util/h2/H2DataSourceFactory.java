package org.nkjmlab.util.h2;

import java.io.File;
import java.util.Set;
import java.util.stream.Stream;
import org.h2.jdbcx.JdbcConnectionPool;

/**
 * <a href="http://www.h2database.com/html/cheatSheet.html">H2 Database Engine</a>
 * <a href="http://h2database.com/html/features.html#database_url">Database URL Overview</a>
 *
 * @author nkjm
 *
 */
public class H2DataSourceFactory {

  private final File databaseDirectory;
  private final String databaseName;
  private final String username;
  private final String password;
  private final String databasePath;


  public H2DataSourceFactory(File databaseDirectory, String databaseName, String username,
      String password) {
    this.username = username;
    this.password = password;
    this.databaseDirectory = databaseDirectory;
    this.databaseName = databaseName;
    String p = databaseDirectory.getAbsolutePath().replace("\\", "/");
    this.databasePath = p + (p.endsWith("/") ? "" : "/") + databaseName;
  }

  private static String toUrlOption(String[] options) {
    if (options.length == 0) {
      return "";
    }
    return ";" + String.join(";", options);
  }

  public String getInMemoryModeJdbcUrl(String... options) {
    return "jdbc:h2:mem:" + databaseName + ";DB_CLOSE_DELAY=-1" + toUrlOption(options);
  }

  public String getServerModeJdbcUrl(String... options) {
    return "jdbc:h2:tcp://localhost" + databasePath + toUrlOption(options);
  }

  public String getEmbeddedModeJdbcUrl(String... options) {
    return "jdbc:h2:file:" + databasePath;
  }

  public String getMixedModeJdbcUrl(String... options) {
    return "jdbc:h2:" + databasePath + ";AUTO_SERVER=TRUE" + toUrlOption(options);
  }

  /**
   * Creates a new sever mode connection pool for H2 databases
   *
   * @return
   */
  public JdbcConnectionPool createInMemoryModeDataSource(String... options) {
    return JdbcConnectionPool.create(getInMemoryModeJdbcUrl(options), getUsername(), getPassword());
  }

  /**
   * Creates a new sever mode connection pool for H2 databases
   *
   * @return
   */
  public JdbcConnectionPool createServerModeDataSource(String... options) {
    return JdbcConnectionPool.create(getServerModeJdbcUrl(options), getUsername(), getPassword());
  }

  /**
   * Creates a new embedded mode connection pool for H2 databases
   *
   * @return
   */
  public JdbcConnectionPool createEmbeddedModeDataSource(String... options) {
    return JdbcConnectionPool.create(getEmbeddedModeJdbcUrl(options), getUsername(), getPassword());
  }

  /**
   * Creates a new mixed mode connection pool for H2 databases
   *
   * @return
   */
  public JdbcConnectionPool createMixedModeDataSource(String... options) {
    return JdbcConnectionPool.create(getMixedModeJdbcUrl(options), getUsername(), getPassword());
  }

  public String getUsername() {
    return username;
  }

  public String getPassword() {
    return password;
  }

  public File getDatabaseDirectory() {
    return databaseDirectory;
  }

  public String getDatabaseName() {
    return databaseName;
  }

  public String getDatabasePath() {
    return databasePath;
  }

  /**
   * Creates the directory for the database.
   */
  public void mkdirsForDatabase() {
    databaseDirectory.mkdirs();
  }

  public static Builder builder() {
    return new Builder();
  }

  public static Builder builder(File dbDir, String dbName, String username, String password) {
    return new Builder(dbDir, dbName, username, password);
  }


  public static class Builder {
    private File databaseDirectory = new File(System.getProperty("java.io.tmpdir"));
    private String databaseName = "annondb";
    private String username = "";
    private String password = "";

    public Builder() {}


    /**
     * Initializes a newly created {@code H2DataSourceFactory.Builder} object; you can get
     * {{@code H2DataSourceFactory} object via {@link #build()} method.
     *
     * @param databaseDirectory the directory including the database file.
     * @param databaseName the name of database.
     * @param username
     * @param password
     */
    public Builder(File dbDir, String dbName, String username, String password) {
      this.databaseName = dbName;
      this.username = username;
      this.password = password;
      setDatabaseDirectory(dbDir);
    }

    public Builder setUsername(String username) {
      this.username = username;
      return this;
    }

    public Builder setPassword(String password) {
      this.password = password;
      return this;
    }

    private static final Set<String> allows = Set.of("~/", "~\\", "./", ".\\");

    public Builder setDatabaseDirectory(File dbDir) {
      String prefix = dbDir.getPath().substring(0, 2);

      if (!allows.contains(prefix) && !dbDir.isAbsolute()) {
        throw new IllegalArgumentException("the databaseDirectory path should be startWith "
            + allows + " or absolute path. The given is [" + dbDir.getPath() + "]");
      }
      this.databaseDirectory = dbDir;
      return this;
    }

    public Builder setDatabaseName(String dbName) {
      this.databaseName = dbName;
      return this;
    }

    public H2DataSourceFactory build() {
      return new H2DataSourceFactory(databaseDirectory, databaseName, username, password);
    }
  }

  public static class Options {
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



}
