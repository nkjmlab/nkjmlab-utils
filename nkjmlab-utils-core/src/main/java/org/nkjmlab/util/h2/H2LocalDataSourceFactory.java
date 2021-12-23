package org.nkjmlab.util.h2;

import java.io.File;
import java.util.Set;
import java.util.stream.Stream;
import org.h2.jdbcx.JdbcConnectionPool;
import org.nkjmlab.util.java.io.SystemFileUtils;
import org.nkjmlab.util.java.json.FileDatabaseConfigJson;

/**
 * A factory of local data source with h2 database.
 *
 * <ul>
 * <li><a href="http://www.h2database.com/html/cheatSheet.html">H2 Database Engine</a></li>
 * <li><a href="http://h2database.com/html/features.html#database_url">Database URL
 * Overview</a></li>
 * </ul>
 *
 * @author nkjm
 *
 */
public class H2LocalDataSourceFactory {

  private final File databaseDirectory;
  private final String databaseName;
  private final String username;
  private final String password;
  private final String databasePath;
  private final String inMemoryModeJdbcUrl;
  private final String serverModeJdbcUrl;
  private final String embeddedModeJdbcUrl;
  private final String mixedModeJdbcUrl;


  private H2LocalDataSourceFactory(File databaseDirectory, String databaseName, String username,
      String password) {
    this.username = username;
    this.password = password;
    this.databaseName = databaseName;
    this.databaseDirectory = databaseDirectory;
    this.databasePath = createDatabasePath(databaseDirectory, databaseName);
    this.inMemoryModeJdbcUrl = "jdbc:h2:mem:" + databaseName + ";DB_CLOSE_DELAY=-1";
    this.serverModeJdbcUrl = "jdbc:h2:tcp://localhost/" + databasePath;
    this.embeddedModeJdbcUrl = "jdbc:h2:file:" + databasePath;
    this.mixedModeJdbcUrl = "jdbc:h2:" + databasePath + ";AUTO_SERVER=TRUE";
  }

  private String createDatabasePath(File databaseDirectory, String databaseName) {
    String absolutePath = databaseDirectory.getAbsolutePath().replace("\\", "/");
    File dir = new File(absolutePath + (absolutePath.endsWith("/") ? "" : "/"));
    return new File(dir, databaseName).getAbsolutePath().replace("\\", "/");
  }

  private static String toUrlOption(String[] options) {
    if (options.length == 0) {
      return "";
    }
    return ";" + String.join(";", options);
  }

  public String getInMemoryModeJdbcUrl() {
    return getInMemoryModeJdbcUrl(new String[0]);
  }

  public String getServerModeJdbcUrl() {
    return getServerModeJdbcUrl(new String[0]);
  }

  public String getEmbeddedModeJdbcUrl() {
    return getEmbeddedModeJdbcUrl(new String[0]);
  }

  public String getMixedModeJdbcUrl() {
    return getMixedModeJdbcUrl(new String[0]);
  }


  public String getInMemoryModeJdbcUrl(String... options) {
    return inMemoryModeJdbcUrl + toUrlOption(options);
  }

  public String getServerModeJdbcUrl(String... options) {
    return serverModeJdbcUrl + toUrlOption(options);
  }

  public String getEmbeddedModeJdbcUrl(String... options) {
    return embeddedModeJdbcUrl + toUrlOption(options);
  }

  public String getMixedModeJdbcUrl(String... options) {
    return mixedModeJdbcUrl + toUrlOption(options);
  }

  /**
   * Creates a new sever mode connection pool for H2 databases
   *
   * @return
   */
  public JdbcConnectionPool createInMemoryModeDataSource() {
    return JdbcConnectionPool.create(getInMemoryModeJdbcUrl(), getUsername(), getPassword());
  }

  /**
   * Creates a new sever mode connection pool for H2 databases
   *
   * @return
   */
  public JdbcConnectionPool createServerModeDataSource() {
    return JdbcConnectionPool.create(getServerModeJdbcUrl(), getUsername(), getPassword());
  }

  /**
   * Creates a new embedded mode connection pool for H2 databases
   *
   * @return
   */
  public JdbcConnectionPool createEmbeddedModeDataSource() {
    return JdbcConnectionPool.create(getEmbeddedModeJdbcUrl(), getUsername(), getPassword());
  }

  /**
   * Creates a new mixed mode connection pool for H2 databases
   *
   * @return
   */
  public JdbcConnectionPool createMixedModeDataSource() {
    return JdbcConnectionPool.create(getMixedModeJdbcUrl(), getUsername(), getPassword());
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

  public static Builder builder(FileDatabaseConfigJson conf) {
    return builder(conf.databaseDirectory, conf.databaseName, conf.username, conf.password);
  }

  /**
   * Initializes a newly created {@link H2LocalDataSourceFactory.Builder} object; you can get
   * {{@code LocalDataSourceFactory} object via {@link #build()} method. "~/" or "~\" in the
   * database directory path will be expanded.
   *
   * @param databaseDirectory the directory including the database file.
   * @param databaseName the name of database.
   * @param username
   * @param password
   */

  public static Builder builder(File databaseDirectory, String databaseName, String username,
      String password) {
    return new Builder(databaseDirectory, databaseName, username, password);
  }



  @Override
  public String toString() {
    return "LocalDataSourceFactory [databaseDirectory=" + databaseDirectory + ", databaseName="
        + databaseName + ", username=" + username + ", password=****" + ", databasePath="
        + databasePath + ", inMemoryModeJdbcUrl=" + inMemoryModeJdbcUrl + ", serverModeJdbcUrl="
        + serverModeJdbcUrl + ", embeddedModeJdbcUrl=" + embeddedModeJdbcUrl + ", mixedModeJdbcUrl="
        + mixedModeJdbcUrl + "]";
  }



  public static class Builder {
    private File databaseDirectory = new File(System.getProperty("java.io.tmpdir"));
    private String databaseName = "annondb";
    private String username = "";
    private String password = "";

    public Builder() {}


    private Builder(File databaseDirectory, String dbName, String username, String password) {
      this.databaseName = dbName;
      this.username = username;
      this.password = password;
      setDatabaseDirectory(databaseDirectory);
    }

    public Builder setUsername(String username) {
      this.username = username;
      return this;
    }

    public Builder setPassword(String password) {
      this.password = password;
      return this;
    }

    private static final Set<String> allowPrefixes = Set.of("~/", "~\\", "./", ".\\");

    /**
     * Sets database directory. "~/" or "~\" in the database directory path will be expanded.
     *
     * @param databaseDirectoryPath
     * @return
     */
    public Builder setDatabaseDirectory(File databaseDirectoryPath) {
      String prefix = databaseDirectoryPath.getPath().substring(0, 2);

      if (!allowPrefixes.contains(prefix) && !databaseDirectoryPath.isAbsolute()) {
        throw new IllegalArgumentException(
            "the databaseDirectory path should be startWith " + allowPrefixes
                + " or absolute path. The given is [" + databaseDirectoryPath.getPath() + "]");
      }
      this.databaseDirectory =
          new File(SystemFileUtils.getTildeExpandAbsolutePath(databaseDirectoryPath));
      return this;
    }

    public Builder setDatabaseName(String dbName) {
      this.databaseName = dbName;
      return this;
    }

    /**
     * Builds a {@link H2LocalDataSourceFactory} instance.
     *
     * @return
     */
    public H2LocalDataSourceFactory build() {
      return new H2LocalDataSourceFactory(databaseDirectory, databaseName, username, password);
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