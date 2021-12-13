package org.nkjmlab.util.java.json;

import java.io.File;

/**
 * A container for file database setting JSON.
 *
 * @author nkjm
 *
 */
public class FileDatabaseConfigJson {

  public final File databaseDirectory;
  public final String databaseName;
  public final String username;
  public final String password;

  public FileDatabaseConfigJson(String databaseDirectory, String databaseName, String username,
      String password) {
    this.databaseDirectory = new File(databaseDirectory);
    this.databaseName = databaseName;
    this.username = username;
    this.password = password;
  }

  public static class Builder {
    public String username;
    public String password;
    public String databaseDirectory;
    public String databaseName;

    public FileDatabaseConfigJson build() {
      return new FileDatabaseConfigJson(databaseDirectory, databaseName, username, password);
    }
  }

  @Override
  public String toString() {
    return "FileDatabaseConfigJson [databaseDirectory=" + databaseDirectory + ", databaseName="
        + databaseName + ", username=" + username + ", password=****]";
  }



}
