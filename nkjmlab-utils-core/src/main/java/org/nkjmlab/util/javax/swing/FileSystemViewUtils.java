package org.nkjmlab.util.javax.swing;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import javax.swing.filechooser.FileSystemView;

public class FileSystemViewUtils {

  private FileSystemViewUtils() {}

  public static List<Patition> getPartitionNames() {
    FileSystemView fsv = FileSystemView.getFileSystemView();
    return Arrays.stream(File.listRoots())
        .map(
            rootFile ->
                new Patition(
                    rootFile,
                    rootFile.getPath().substring(0, 2),
                    fsv.getSystemDisplayName(rootFile)))
        .toList();
  }

  public record Patition(File file, String driveLetter, String systemDisplayName) {}
}
