package org.nkjmlab.util.java.web;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FileLastModifiedsMapTest {

  private Path testDirectory;

  @BeforeEach
  void setUp() throws IOException {
    testDirectory = Files.createTempDirectory("testDirectory");
    // Create test files in root directory
    Files.createFile(testDirectory.resolve("test1.txt"));
    Files.createFile(testDirectory.resolve("test2.log"));
    Files.createFile(testDirectory.resolve("test3.txt"));

    // Create subdirectories and test files in them
    Path subDir1 = Files.createDirectory(testDirectory.resolve("subDir1"));
    Path subDir2 = Files.createDirectory(subDir1.resolve("subDir2"));
    Files.createFile(subDir1.resolve("test4.txt"));
    Files.createFile(subDir2.resolve("test5.log"));
    Files.createFile(subDir2.resolve("test6.txt"));
  }

  @AfterEach
  void tearDown() throws IOException {
    testDirectory.toFile().deleteOnExit();
  }

  @Test
  void testCreate() throws IOException {
    FileLastModifiedsMap map = FileLastModifiedsMap.create(testDirectory, 2, ".txt");
    Map<String, Long> relativePaths = map.toRelativePath();

    // Check that only .txt files up to depth 2 are included
    assertThat(relativePaths)
        .hasSize(3)
        .containsKeys("test1_txt", "test3_txt", "subDir1_test4_txt");
  }

  @Test
  void testListFileModifiedDates() throws IOException {
    Map<Path, Long> fileModifiedDates =
        FileLastModifiedsMap.listFileModifiedDates(testDirectory, 2, ".txt", ".log");

    // Check that all files up to depth 2 are included
    assertThat(fileModifiedDates)
        .hasSize(4)
        .containsKeys(
            testDirectory.resolve("test1.txt"),
            testDirectory.resolve("test2.log"),
            testDirectory.resolve("test3.txt"),
            testDirectory.resolve("subDir1/test4.txt"));
  }

  @Test
  void testToRelativePath() throws IOException {
    FileLastModifiedsMap map = FileLastModifiedsMap.create(testDirectory, 3, ".txt");
    Map<String, Long> relativePaths = map.toRelativePath();

    // Check that the relative paths are correctly converted, including subdirectories
    assertThat(relativePaths)
        .containsKeys("test1_txt", "test3_txt", "subDir1_test4_txt", "subDir1_subDir2_test6_txt")
        .doesNotContainKeys("test2_log", "subDir1_subDir2_test5_log");
  }

  @Test
  void testCreateWithDepth3() throws IOException {
    FileLastModifiedsMap map = FileLastModifiedsMap.create(testDirectory, 3, ".txt", ".log");
    Map<String, Long> relativePaths = map.toRelativePath();

    // Check that all .txt and .log files up to depth 3 are included
    assertThat(relativePaths)
        .hasSize(6)
        .containsKeys(
            "test1_txt",
            "test2_log",
            "test3_txt",
            "subDir1_test4_txt",
            "subDir1_subDir2_test6_txt",
            "subDir1_subDir2_test5_log");
  }
}
